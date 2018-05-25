/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patent.news.dao.PatentRepository;
import com.patent.news.dao.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 9:05 PM
 */
public class BaseService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PatentRepository patentRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;
}
