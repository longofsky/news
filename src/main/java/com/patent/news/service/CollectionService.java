/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.Collection;
import com.patent.news.entity.Patent;
import com.patent.news.entity.User;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:54 PM
 */
@Service
public class CollectionService extends BaseService {

    @Autowired
    private UserService userService;

    @Autowired
    private PatentService patentService;

    public void collect(String openid, String patentId) {
        User user = userService.findAndSaveByOpenId(openid);
        Patent patent = patentService.findAndSaveByPatentId(patentId);
        Collection collection = new Collection();
        collection.setUser(user);
        collection.setPatent(patent);
        collectionRepository.save(collection);
    }

    public boolean isCollected(String openid, String patentId) {
        List<Collection> collectionList = collectionRepository.findByUserOpenidAndPatentPatentId(openid, patentId);
        return CollectionUtils.isNotEmpty(collectionList);
    }

    public void delete(String openid, String patentId) {
        List<Collection> collectionList = collectionRepository.findByUserOpenidAndPatentPatentId(openid, patentId);
        collectionList.forEach(item -> collectionRepository.delete(item));
    }

    public String  collection(String openid){
        List<Collection> collectionList = collectionRepository.findByUserOpenid(openid);
        return collectionList.stream().map(item -> item.getPatent().getPatentId()).collect(Collectors.joining(","));
    }

}
