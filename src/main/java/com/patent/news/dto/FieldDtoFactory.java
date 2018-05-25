package com.patent.news.dto;

public class FieldDtoFactory {
    public static TemplateMsgFieldDto getFieldDto(String value, String color) {
        TemplateMsgFieldDto templateMsgFieldDto = new TemplateMsgFieldDto();
        templateMsgFieldDto.setColor(color);
        templateMsgFieldDto.setValue(value);
        return templateMsgFieldDto;
    }
}
