/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import lombok.Data;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:21 PM
 */
@Data
public class RatingDto {

    private long userId;
    private long itemId;
    private float rating;
    private long timestamp;
}
