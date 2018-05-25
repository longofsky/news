/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import lombok.Data;

/**
 * Author: Tory
 * Date: 5/25/18
 * Time: 11:27 AM
 */
@Data
public class WxResponseDto {
    private int total;
    private int count;
    private String nextOpenid;
    private WxOpenIdDto data;
}
