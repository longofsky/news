/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.dto.RatingPostDto;
import com.patent.news.service.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:59 PM
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<?> rating(@RequestBody RatingPostDto ratingPostDto) {
        ratingService.rating(ratingPostDto.getOpenid(), ratingPostDto.getPatentId());
        return ResponseEntity.ok().build();
    }
}
