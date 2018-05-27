/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.patent.news.dto.PatentSearchDetailDto;
import com.patent.news.dto.PatentSearchDto;
import com.patent.news.dto.TokenPatentDto;
import com.patent.news.entity.Patent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Tory
 * Date: 5/23/18
 * Time: 12:08 AM
 */
@Service
public class PatentService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatentService.class);

    private static final TypeReference<List<PatentSearchDetailDto>> TYPE_REFERENCE = new TypeReference<List<PatentSearchDetailDto>>() {
    };

    @Value("${configs.com.patent.news.client.id}")
    private String clientId;

    @Value("${configs.com.patent.news.client.secret}")
    private String clientSecret;

    public void initPatent() {
        HashSet<String> patentIdSet = new HashSet<>();
        String[] titles = {"phone", "游戏机", "PS4", "5g", "自动化", "电扇", "杯子", "医药", "电脑", "虚拟现实",
                "区块链", "人工智能", "物联网", "大数据", "云计算", "汽车", "高铁", "自动驾驶", "边缘计算", "人脸识别",
                "快递", "签字笔"};

        String[] companies = {"索尼", "小米", "华为", "三星", "苹果", "娃哈哈", "中石油", "中国移动", "中国电信", "京东方",
                "思科", "讯飞", "西门子", "海尔", "格力", "百度", "腾讯", "戴尔", "万达", "尚德药缘"};

        Arrays.stream(titles).forEach(item -> {
            try {
                PatentSearchDto patentSearchDto = simpleSearch(item);
                patentIdSet.addAll(patentSearchDto.getPatent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Arrays.stream(companies).forEach(item -> {
            try {
                PatentSearchDto patentSearchDto = simpleSearchByAns(item);
                patentIdSet.addAll(patentSearchDto.getPatent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        int itemId = 0;

        ArrayList<Patent> patents = new ArrayList<>();
        for (String patentId : patentIdSet) {
            Patent patent = patentRepository.findByPatentId(patentId);
            if (Objects.isNull(patent)) {
                itemId++;
                patent = new Patent();
                patent.setItemId(itemId);
                patent.setPatentId(patentId);
                patentRepository.save(patent);
                patents.add(patent);
            }
        }

        LOGGER.info("Added patent amount:" + patents.size());


    }

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

    public PatentSearchDto simpleSearch(String ttl) throws IOException {
        String uri = "https://api.zhihuiya.com/patent/simple/search?limit=10&ttl=" + ttl;
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), String.class);
        return objectMapper.readValue(exchange.getBody(), PatentSearchDto.class);
    }

    public PatentSearchDto simpleSearchByAns(String ans) throws IOException {
        String uri = "https://api.zhihuiya.com/patent/simple/search?limit=10&ans=" + ans;
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), String.class);
        return objectMapper.readValue(exchange.getBody(), PatentSearchDto.class);
    }

    public String search(String ttl) throws IOException {
        PatentSearchDto search = simpleSearch(ttl);
        String patentId = search.getPatent().stream().collect(Collectors.joining(","));
        String uri = "https://api.zhihuiya.com/patent?patent_id=" + patentId;
        return getResult(uri);
    }

    public String searchTitle(String ttl) throws IOException {
        String uri = keywordUrl + "/news/cut/" + ttl;
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), String.class);

        List keyWordList = objectMapper.readValue(exchange.getBody(), List.class);
        String search = search((String) keyWordList.get(0));


        List<PatentSearchDetailDto> list = objectMapper.readValue(search, TYPE_REFERENCE);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 2 && i < list.size(); i++) {
            str.append("专利号：").append(list.get(i).getPatentNumber()).append("\n");
            List<Map<String, String>> title = list.get(i).getTitle();
            String titleStr = null;
            for (Map<String, String> map : title) {
                String cn = map.get("lang");
                if (StringUtils.equalsIgnoreCase(cn, "CN")) {
                    titleStr = map.get("text");
                    break;
                }
            }
            if (StringUtils.isBlank(titleStr)) {
                for (Map<String, String> map : title) {
                    String en = map.get("lang");
                    if (StringUtils.equalsIgnoreCase(en, "EN")) {
                        titleStr = map.get("text");
                        break;
                    }
                }
            }

            if (StringUtils.isBlank(titleStr)) {
                for (Map<String, String> map : title) {
                    String lang = map.get("lang");
                    if (StringUtils.equalsIgnoreCase(lang, "JP")) {
                        titleStr = map.get("text");
                        break;
                    }
                }
            }
            String detailUrl = frontendUrl + "/detail/" + list.get(i).getPatentId();
            str.append("标题：").append("<a href='").append(detailUrl).append("'>").append(titleStr).append("</a>").append("\n");
            str.append("-------------\n");
        }

        // todo 修要修改链接为 frontendUrl/{ttl} 格式
        str.append("<a href='").append(frontendUrl).append("'>查看更多</a>");
        return str.toString();

    }

    public String patentDetail(String patentId) throws IOException {
        String uri = "https://api.zhihuiya.com/patent?patent_id=" + patentId;
        return getResult(uri);
    }

    public String patentCitationCount(String patentId, String citationType) throws IOException {
        String uri = "https://api.zhihuiya.com/patent/citation/count?patent_id=" + patentId + "&citation_type=" + citationType;
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
