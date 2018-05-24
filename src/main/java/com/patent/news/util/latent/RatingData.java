package com.patent.news.util.latent;

/**
 * 用户记录数据
 */
public class RatingData {

    public int userID;
    public int movieID;
    public int rate;
    public int data;


    public RatingData(String daString) {
        String[] strings = daString.split("::");
        if (strings.length != 4)
            System.err.println("The Data's Not Comfort");
        userID = Integer.parseInt(strings[0]);
        movieID = Integer.parseInt(strings[1]);
        rate = Integer.parseInt(strings[2]);
        data = Integer.parseInt(strings[3]);
    }


}
