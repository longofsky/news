/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.Patent;
import com.patent.news.entity.User;
import com.patent.news.util.FileIO;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:54 PM
 */
@Service
public class RatingService extends BaseService {

    public void rating(String openid, String patentId) {
        User user = userRepository.findByOpenid(openid);
        if (Objects.isNull(user)) {
            user = new User();
            user.setOpenid(openid);
            userRepository.save(user);
        }

        Patent patent = patentRepository.findByPatentId(patentId);
        if (Objects.isNull(patent)) {
            patent = new Patent();
            patent.setPatentId(patentId);
            patentRepository.save(patent);
        }
        String path = System.getProperty("user.dir") + "/data/rating.log";
        System.out.println(path);
        FileIO.FileWrite(path, user.getUserId() + "::" + patent.getItemId() + "::1::" + new Date().getTime()+"\n", true);
    }
}
