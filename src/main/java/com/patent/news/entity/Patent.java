/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:37 PM
 */
@Data
@Entity
@Table(name = "patent")
public class Patent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    private String patentId;

}
