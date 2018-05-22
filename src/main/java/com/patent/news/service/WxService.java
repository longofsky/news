/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patent.news.dto.TokenDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 10:32 PM
 */

@Service
public class WxService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${configs.com.patent.news.wx.appid}")
    private String appid;

    @Value("${configs.com.patent.news.wx.secret}")
    private String secret;

    public String user() throws IOException {
        TokenDto tokenDto = token();
        String uri = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + tokenDto.getAccessToken() + "&next_openid=";
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        return exchange.getBody();
    }

    public TokenDto token() throws IOException {
        String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        String body = exchange.getBody();
        return objectMapper.readValue(body, TokenDto.class);
    }
}
