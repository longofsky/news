/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 8:27 PM
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String get() {
        return "get";
    }
}
