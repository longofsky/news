/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.User;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Author: Tory
 * Date: 5/27/18
 * Time: 6:25 PM
 */
@Service
public class UserService extends BaseService {

    public User findAndSaveByOpenId(String openid) {
        User user = userRepository.findByOpenid(openid);
        if (Objects.isNull(user)) {
            Iterable<User> userIterable = userRepository.findAll(new Sort(Sort.Direction.DESC, "userId"));
            User next;
            if (userIterable.iterator().hasNext()) {
                next = userIterable.iterator().next();
            } else {
                next = new User();
                next.setUserId(0);
            }
            user = new User();
            user.setUserId(next.getUserId() + 1);
            user.setOpenid(openid);
            userRepository.save(user);
        }
        return user;
    }
}
