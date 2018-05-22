/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */
package com.patent.news.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Author: Tory
 * Date: 4/23/17
 * Time: 6:27 PM
 */
@NoRepositoryBean
public interface BaseRespository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}
