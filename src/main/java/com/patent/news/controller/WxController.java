/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import com.patent.news.service.WxService;

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
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private WxService wxService;

    @GetMapping("/token")
    public ResponseEntity<?> token() throws IOException {
        return ResponseEntity.ok().body(wxService.token());
    }

    @GetMapping("/user")
    public ResponseEntity<?> user() throws IOException {
        return ResponseEntity.ok().body(wxService.user());
    }

}
