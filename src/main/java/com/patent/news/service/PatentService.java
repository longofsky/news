/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patent.news.dto.TokenPatentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

/**
 * Author: Tory
 * Date: 5/23/18
 * Time: 12:08 AM
 */
@Service
public class PatentService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${configs.com.patent.news.client.id}")
    private String clientId;

    @Value("${configs.com.patent.news.client.secret}")
    private String clientSecret;

    public TokenPatentDto token() throws IOException {
        String uri = "https://con.zhihuiya.com/connector/oauth/token";
        Base64.Encoder encoder = Base64.getEncoder();
        String basicStr = "Basic " + encoder.encodeToString((clientId + ":" + clientSecret).getBytes("UTF-8"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", basicStr);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        String body = exchange.getBody();
        return objectMapper.readValue(body, TokenPatentDto.class);
    }

    public String classification() throws IOException {
        String uri = "https://api.zhihuiya.com/patent/classification?type=ipc&code=A01,A02";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-PatSnap-Version", "1.0.0");
        httpHeaders.set("Authorization", "Bearer " + token().getAccessToken());
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        return exchange.getBody();
    }

    public void search() {
        String uri = "https://api.zhihuiya.com/patent";
    }

}
