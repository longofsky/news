package com.patent.news.dao;

import com.patent.news.entity.Collection;

import java.util.List;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:55 PM
 */
public interface CollectionRepository extends BaseRespository<Collection, String> {

    List<Collection> findByUserOpenidAndPatentPatentId(String openid, String patentId);
    List<Collection> findByUserOpenid(String openid);

}
