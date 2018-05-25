package com.patent.news.dto;

import lombok.Data;

@Data
public class WxMessageDto {
    String open_id;
    String url;
    String title;
    String content;
}
