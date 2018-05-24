/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.entity.News;
import com.patent.news.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 8:43 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public void save(@RequestBody @Valid News news) {
        newsService.save(news);
    }

    @GetMapping("/free/count")
    public ResponseEntity<?> free() {
        return ResponseEntity.ok().body(5);
    }

}
