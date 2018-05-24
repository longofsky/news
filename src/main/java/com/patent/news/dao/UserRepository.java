package com.patent.news.dao;

import com.patent.news.entity.User;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 8:55 PM
 */
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByOpenid(String openId);
}
