/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import lombok.Data;

import java.util.List;

/**
 * Author: Tory
 * Date: 5/25/18
 * Time: 11:27 AM
 */
@Data
public class WxOpenIdDto {
    private List<String> openid;
}
