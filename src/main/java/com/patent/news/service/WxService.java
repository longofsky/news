/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String user() throws IOException {
        String uri = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + tokenDto.getAccessToken() + "&next_openid=";
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        return exchange.getBody();
    }

    @Scheduled(fixedDelay = 3600 * 1000L)
    public void refreshToken() throws IOException {
        String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        String body = exchange.getBody();
        LOGGER.info("[accessToke is {}]", body);
        tokenDto = objectMapper.readValue(body, TokenWxDto.class);
    }

    public void sendMessage() throws IOException {
        String uri = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + tokenDto.getAccessToken();
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
}
