/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.Patent;
import com.patent.news.entity.User;
import com.patent.news.util.Constant;
import com.patent.news.util.FileIO;

import org.springframework.data.domain.Sort;
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
            Iterable<User> userIterable = userRepository.findAll(new Sort(Sort.Direction.DESC, "userId"));
            User next = userIterable.iterator().next();
            user = new User();
            user.setUserId(next.getUserId() + 1);
            user.setOpenid(openid);
            userRepository.save(user);
        }

        Patent patent = patentRepository.findByPatentId(patentId);
        if (Objects.isNull(patent)) {
            Iterable<Patent> patentIterable = patentRepository.findAll(new Sort(Sort.Direction.DESC, "itemId"));
            Patent next = patentIterable.iterator().next();
            patent = new Patent();
            patent.setItemId(next.getItemId() + 1);
            patent.setPatentId(patentId);
            patentRepository.save(patent);
        }
        FileIO.FileWrite(Constant.path, user.getUserId() + "::" + patent.getItemId() + "::1::" + new Date().getTime() + "\n", true);
    }
}
