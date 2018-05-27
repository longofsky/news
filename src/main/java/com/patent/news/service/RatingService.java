/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.Patent;
import com.patent.news.entity.User;
import com.patent.news.util.Constant;
import com.patent.news.util.FileIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:54 PM
 */
@Service
public class RatingService extends BaseService {

    @Autowired
    private UserService userService;

    @Autowired
    private PatentService patentService;

    public void rating(String openid, String patentId) {
        User user = userService.findAndSaveByOpenId(openid);
        Patent patent = patentService.findAndSaveByPatentId(patentId);
        FileIO.FileWrite(Constant.path, user.getUserId() + "::" + patent.getItemId() + "::1::978300760\n", true);
    }

}
