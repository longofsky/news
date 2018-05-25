/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patent.news.dto.FieldDtoFactory;
import com.patent.news.dto.TemplateMsgDataDto;
import com.patent.news.dto.TemplateMsgDto;
import com.patent.news.dto.TokenWxDto;
import com.patent.news.dto.WxResponseDto;
import com.patent.news.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.List;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 10:32 PM
 */

@Service
public class WxService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    TokenWxDto tokenDto;

    @Value("${configs.com.patent.news.wx.appid}")
    private String appid;

    @Value("${configs.com.patent.news.wx.secret}")
    private String secret;

    @Value("${configs.com.patent.news.wx.template.id}")
    private String templateId;

    public String user() throws IOException {
        String uri = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + tokenDto.getAccessToken() + "&next_openid=";
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        return exchange.getBody();
    }

    @Autowired
    @Scheduled(fixedDelay = 3600 * 1000L)
    public void refreshToken() throws IOException {
        String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        String body = exchange.getBody();
        LOGGER.info("[accessToken is {}]", body);
        tokenDto = objectMapper.readValue(body, TokenWxDto.class);
    }

    public void sendMessage(String userId, String url, String title, String content) throws IOException {
        LOGGER.info("[sendMessage userid {},url {},title {},content {}]", userId, url, title, content);
        String uri = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + tokenDto.getAccessToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type","application/json; charset=utf-8");
        TemplateMsgDto msgDto = new TemplateMsgDto();
        msgDto.setTemplateId(templateId);
        msgDto.setTouser(userId);
        msgDto.setUrl(url + "?open_id=" + userId);


        String color = "#173177";
        TemplateMsgDataDto templateMsgDataDto = new TemplateMsgDataDto();
        templateMsgDataDto.setFirst(FieldDtoFactory.getFieldDto(title, color));
        templateMsgDataDto.setKeyword1(FieldDtoFactory.getFieldDto(content, color));
        templateMsgDataDto.setRemark(FieldDtoFactory.getFieldDto("点击查看详情", color));

        msgDto.setData(templateMsgDataDto);

        String data = objectMapper.writer().writeValueAsString(msgDto);
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(data, httpHeaders), String.class);

        LOGGER.info("[sendMessage is {}]", exchange.getBody());
    }


    public void initUser() throws IOException {
        String body = user();
        WxResponseDto wxResponseDto = objectMapper.readValue(body, WxResponseDto.class);
        List<String> openidList = wxResponseDto.getData().getOpenid();
        int userId = 0;
        for (String openid : openidList) {
            userId++;
            User user = new User();
            user.setUserId(userId);
            user.setOpenid(openid);
            userRepository.save(user);
        }
    }

    public void sendQueryWord(String userId, String keyword) {
        String uri = keywordUrl + "/news/open_id/" + userId + "/query/" + keyword;
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

        LOGGER.info("[sendQueryWord is {}]", exchange.getBody());
    }
}
