/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.dao.NewsRepository;
import com.patent.news.entity.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 8:43 PM
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public void save(News news) {
        newsRepository.save(news);
    }
}
