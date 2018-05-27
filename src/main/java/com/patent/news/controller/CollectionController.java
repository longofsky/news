/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.service.CollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:59 PM
 */
@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public ResponseEntity<?> collect(@RequestParam String openid, @RequestParam String patentId) {
        collectionService.collect(openid, patentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String openid, @RequestParam String patentId) {
        collectionService.delete(openid, patentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/collected")
    public ResponseEntity<?> collected(@RequestParam String openid, @RequestParam String patentId) {
        return ResponseEntity.ok().body(collectionService.isCollected(openid, patentId));
    }

    @GetMapping("/{openid}")
    public ResponseEntity<?> collection(@PathVariable String openid) {
        return ResponseEntity.ok().body(collectionService.collection(openid));
    }


}
