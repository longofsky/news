/*
 * Copyright (c) 2016 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patent.news.util;

import java.util.UUID;

/**
 * A class that contains methods to generate unique strings
 */
public class UniqueString {

    /**
     * Return a unique string generated from a UUID.
     *
     * @return The UUID based unique string.
     */
    public static String uuidUniqueString() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
