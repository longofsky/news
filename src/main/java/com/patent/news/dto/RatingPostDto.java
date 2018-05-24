/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import lombok.Data;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:21 PM
 */
@Data
public class RatingPostDto {

    String openid;
    String patentId;
}
