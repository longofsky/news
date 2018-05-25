package com.patent.news.dao;

import com.patent.news.entity.Patent;

import java.util.List;
import java.util.Set;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:55 PM
 */
public interface PatentRepository extends BaseRespository<Patent, String> {

    Patent findByPatentId(String patentId);

    List<Patent> findByItemIdIn(Set<Long> itemIdSet);
}
