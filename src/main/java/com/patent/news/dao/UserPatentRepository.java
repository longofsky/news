package com.patent.news.dao;

import com.patent.news.entity.User;
import com.patent.news.entity.UserPatent;

import java.util.List;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:55 PM
 */
public interface UserPatentRepository extends BaseRespository<UserPatent, String> {

    List<UserPatent> findByUserOpenid(String openid);

}
