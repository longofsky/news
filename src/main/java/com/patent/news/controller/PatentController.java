/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.service.PatentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 8:43 PM
 */
@RestController
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentService patentService;

    @GetMapping("/token")
    public ResponseEntity<?> token() throws IOException {
        return ResponseEntity.ok().body(patentService.token());
    }

    @GetMapping("/classification")
    public ResponseEntity<?> classification() throws IOException {
        return ResponseEntity.ok().body(patentService.classification());
    }

}
