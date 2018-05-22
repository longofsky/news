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
public class TokenPatentDto {

    private String accessToken;
    private int expiresIn;
    private String tokenType;
    private String scope;
    private String jti;

}
