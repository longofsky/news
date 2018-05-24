/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.Patent;
import com.patent.news.entity.User;
import com.patent.news.util.Constant;
import com.patent.news.util.FileIO;
import com.patent.news.util.latent.Evaluation;
import com.patent.news.util.latent.LFM;
import com.patent.news.util.latent.RatingData;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Tory
 * Date: 5/25/18
 * Time: 12:08 AM
 */
@Service
public class RecommendLatentService extends BaseService {

    public static Set<Integer> user = new HashSet<>();
    public static Set<Integer> item = new HashSet<>();
    public static List<Integer> itemList = new ArrayList<>();
    public static Map<Integer, Integer> map = new HashMap<>();
    public static Map<Integer, Integer> randMap = new HashMap<>();    //倾向选择热门且用户未评价的为负例
    /**
     * 用户项目训练数据 （这个属于是标准答案）
     */
    public static Map<Integer, Map<Integer, Float>> UserItemTrain = new HashMap<>();
    public static LFM lfm = new LFM();

    private static Map<Integer, Float> getFu(Map<Integer, Float> item) {
        Map<Integer, Float> map = new HashMap<>();
        while (map.size() < item.size() * 4 && item.size() + map.size() < RecommendLatentService.item.size() * 0.8) {
            /**同等对待方式*/
            int rand = (int) (Math.random() * RecommendLatentService.itemList.size());
            if (!item.containsKey(RecommendLatentService.itemList.get(rand))) {
                map.put(RecommendLatentService.itemList.get(rand), (float) 0);
            }
        }
        return map;
    }

    public void training() {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        FileIO fileIO = new FileIO();
        fileIO.SetfileName(Constant.path);
        fileIO.FileRead();
        List<String> list = fileIO.cloneList();
        int num = 0;
        for (String s : list) {
            RatingData data = new RatingData(s);
            if (UserItemTrain.containsKey(data.userID)) {
                UserItemTrain.get(data.userID).put(data.movieID, (float) 1);
            } else {
                Map<Integer, Float> tMap = new HashMap<>();
                tMap.put(data.movieID, (float) 1);
                UserItemTrain.put(data.userID, tMap);
            }
            //计算每个项目的热度
            if (map.containsKey(data.movieID)) {
                map.put(data.movieID, map.get(data.movieID) + 1);
            } else {
                map.put(data.movieID, 1);
            }
            randMap.put(num++, data.movieID);
            user.add(data.userID);
            item.add(data.movieID);
        }

        System.out.println("正在构造罗盘赌");
        itemList.addAll(RecommendLatentService.item);
        int Fu = 0;
        for (int user : UserItemTrain.keySet()) {
            UserItemTrain.get(user).putAll(getFu(UserItemTrain.get(user)));
            if (++Fu % 1000 == 0)
                System.out.println("已构造 " + Fu + " 个负样本用户数据");
        }
        System.out.println("负样本生成完毕");

        LFM lfm = new LFM(user, item);
        for (int trac = 0; trac <= 1; trac++) {
            LFM.LatentFactorModel(UserItemTrain);
        }
    }

    public List<String> recommendLatent(String openid) {
        User user = userRepository.findByOpenid(openid);
        long userId = user.getUserId();
        Map<Integer, Float> item1 = UserItemTrain.get((int)userId);
        Set<Integer> resysList = lfm.getResysList((int) userId, item1);
        List<Long> ids = new ArrayList<>();
        resysList.forEach(item -> ids.add(Long.valueOf(item)));
//        System.out.println(ids);
//        return null;
        Iterable<Patent> patentIterable = patentRepository.findAllById(ids);
        List<String> patentIdList = new ArrayList<>();
        patentIterable.forEach(item -> patentIdList.add(item.getPatentId()));
        return patentIdList;
    }
}
