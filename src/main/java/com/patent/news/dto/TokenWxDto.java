/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import lombok.Data;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 11:07 PM
 */
@Data
public class TokenWxDto {

    private String accessToken;
    private int expiresIn;

}
