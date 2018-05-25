package com.patent.news.dto;

import lombok.Data;

@Data
public class TemplateMsgDto {
    String touser;
    String templateId;
    String url;
    TemplateMsgDataDto data;
}
