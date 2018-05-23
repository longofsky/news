/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import lombok.Data;

import java.util.List;

/**
 * Author: Tory
 * Date: 5/23/18
 * Time: 8:52 PM
 */
@Data
public class PatentSearchDto {
    private List<String> patent;
    private int offset;
    private int limit;
    private int total;
}
