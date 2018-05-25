package com.patent.news.dao;

import com.patent.news.entity.User;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:55 PM
 */
public interface UserRepository extends BaseRespository<User, String> {
    User findByOpenid(String openId);

}
