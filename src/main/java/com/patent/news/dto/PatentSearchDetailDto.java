/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Author: Tory
 * Date: 5/25/18
 * Time: 7:31 PM
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatentSearchDetailDto implements Serializable {

    private static final long serialVersionUID = -5085851236008651041L;

    private String ipc;
    private String patentId;
    private String patentNumber;
    private List<Map<String,String>> title;

//    @JsonProperty("abstract")
//    private Map<String,String> abs;

}
