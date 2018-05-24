/**
 * Copyright (C) 2017 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.service;

import com.patent.news.entity.Patent;
import com.patent.news.entity.User;
import com.patent.news.util.Constant;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Tory
 * Date: 5/24/18
 * Time: 11:00 PM
 */
@Service
public class RecommendService extends BaseService {

    private List<String> getPatentIds(List<RecommendedItem> recommendedItemList) {
        Set<Long> collect = recommendedItemList.stream().map(RecommendedItem::getItemID).collect(Collectors.toSet());
        Iterable<Patent> patentIterable = patentRepository.findAllById(collect);
        List<String> patentIdList = new ArrayList<>();
        patentIterable.forEach(item -> patentIdList.add(item.getPatentId()));
        return patentIdList;
    }
    private DataModel generateModel() throws IOException {
        File file = new File(Constant.path);
        return new GroupLensDataModel(file);
    }

    public List<String> recommendUserBase(String openid) throws IOException, TasteException {
        User user = userRepository.findByOpenid(openid);
        DataModel dataModel = generateModel();
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        List<RecommendedItem> recommendedItemList = recommender.recommend(user.getUserId(), 3);
        return getPatentIds(recommendedItemList);
    }

    public List<String> recommendItemBase(String openid, String patentId) throws IOException, TasteException {
        User user = userRepository.findByOpenid(openid);
        Patent patent = patentRepository.findByPatentId(patentId);
        DataModel dataModel = generateModel();
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(user.getUserId(), patent.getItemId(), 3);
        return getPatentIds(recommendedItemList);
    }

}
