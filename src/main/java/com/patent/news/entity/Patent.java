/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "patent")
public class Patent extends BaseEntity{

    private long itemId;

    private String patentId;

}
