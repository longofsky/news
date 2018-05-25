/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.service.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:59 PM
 */
@RestController
@RequestMapping("/rating")
public class RatingController extends BaseController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<?> rating(@RequestParam String openid, @RequestParam String patentId) {
        ratingService.rating(openid, patentId);
        return ResponseEntity.ok().build();
    }
}
