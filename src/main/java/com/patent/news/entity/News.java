/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: Tory
 * Date: 5/22/18
 * Time: 8:42 PM
 */
@Entity
@Table(name = "news")
public class News extends BaseEntity {
    private String title;
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
