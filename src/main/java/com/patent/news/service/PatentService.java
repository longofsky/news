/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patent.news.dto.PatentSearchDto;
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
import java.util.stream.Collectors;

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

    private HttpHeaders getHttpHeaders() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-PatSnap-Version", "1.0.0");
        httpHeaders.set("Authorization", "Bearer " + token().getAccessToken());
        return httpHeaders;
    }

    private String getResult(String uri) throws IOException {
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), String.class);
        return exchange.getBody();
    }

    public PatentSearchDto search(String ttl) throws IOException {
        String uri = "https://api.zhihuiya.com/patent/simple/search?ttl=" + ttl;
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), String.class);
        return objectMapper.readValue(exchange.getBody(), PatentSearchDto.class);
    }

    public String patent(String ttl) throws IOException {
        PatentSearchDto search = search(ttl);
        String patentId = search.getPatent().stream().collect(Collectors.joining(","));
        String uri = "https://api.zhihuiya.com/patent?patent_id=" + patentId;
        return getResult(uri);
    }

    public String patentDetail(String patentId) throws IOException {
        String uri = "https://api.zhihuiya.com/patent?patent_id=" + patentId;
        return getResult(uri);
    }

    public String patentCitationCount(String patentId, String citationType) throws IOException {
        String uri = "https://api.zhihuiya.com/patent?patent_id=" + patentId + "&citation_type=" + citationType;
        return getResult(uri);
    }

    public String patentValuation(String patentId) throws IOException {
        String uri = "https://api.zhihuiya.com/patent/valuation?patent_id=" + patentId;
        return getResult(uri);
    }

    public String classification(String type, String code) throws IOException {
        String uri = "https://api.zhihuiya.com/patent/classification?type=" + type + "&code=" + code;
        return getResult(uri);
    }

}
