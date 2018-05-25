/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.service.PatentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/patent")
public class PatentController extends BaseController {

    @Autowired
    private PatentService patentService;

    @GetMapping("/token")
    public ResponseEntity<?> token() throws IOException {
        return ResponseEntity.ok().body(patentService.token());
    }


    @GetMapping("/simple/search")
    public ResponseEntity<?> search(@RequestParam String ttl) throws IOException {
        return ResponseEntity.ok().body(patentService.simpleSearch(ttl));
    }

    @GetMapping("/search")
    public ResponseEntity<?> patent(@RequestParam String ttl) throws IOException {
        return ResponseEntity.ok().body(patentService.search(ttl));
    }

    @GetMapping("")
    public ResponseEntity<?> detail(@RequestParam String patentId) throws IOException {
        return ResponseEntity.ok().body(patentService.patentDetail(patentId));
    }

    @GetMapping("/citation/count")
    public ResponseEntity<?> patentCitationCount(@RequestParam String patentId, @RequestParam String citationType) throws IOException {
        return ResponseEntity.ok().body(patentService.patentCitationCount(patentId, citationType));
    }

    @GetMapping("/valuation")
    public ResponseEntity<?> patentValuation(@RequestParam String patentId) throws IOException {
        return ResponseEntity.ok().body(patentService.patentValuation(patentId));
    }

    @GetMapping("/classification")
    public ResponseEntity<?> classification(@RequestParam String type, @RequestParam String code) throws IOException {
        return ResponseEntity.ok().body(patentService.classification(type, code));
    }

    @PostMapping("/init")
    public ResponseEntity<?> init() throws IOException {
        patentService.initPatent();
        return ResponseEntity.ok().build();
    }
}
