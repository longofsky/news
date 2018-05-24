/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.service.RecommendLatentService;
import com.patent.news.service.RecommendService;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 8:43 PM
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private RecommendLatentService recommendLatentService;

    @GetMapping("/userbase")
    public ResponseEntity<?> recommendUserBase(@RequestParam String openid) throws IOException, TasteException {
        return ResponseEntity.ok().body(recommendService.recommendUserBase(openid));
    }

    @GetMapping("/itembase")
    public ResponseEntity<?> recommendItemBase(@RequestParam String openid, @RequestParam String patentId) throws IOException, TasteException {
        return ResponseEntity.ok().body(recommendService.recommendItemBase(openid, patentId));
    }

    @GetMapping("/training")
    public ResponseEntity<?> training() {
        recommendLatentService.training();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/latent")
    public ResponseEntity<?> recommendLatent(@RequestParam String openid) {
        return ResponseEntity.ok().body(recommendLatentService.recommendLatent(openid));
    }
}
