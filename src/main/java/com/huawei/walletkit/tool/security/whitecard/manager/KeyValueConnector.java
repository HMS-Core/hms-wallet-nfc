/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.whitecard.manager;

/**
 * Combine the key-value string in format "key=value&key=value...&key=value"
 *
 * @since 2020-02-20
 */
public class KeyValueConnector {
    private StringBuilder builder;

    /**
     * append function
     *
     * @param key key
     * @param value value
     * @return keyvalueConnector
     */
    public KeyValueConnector append(String key, String value) {
        if (key == null || key.trim().isEmpty()) {
            return this;
        }
        if (builder == null) {
            builder = new StringBuilder();
        } else {
            builder.append("&");
        }
        builder.append(key).append("=");
        if (value != null) {
            builder.append(value);
        }
        return this;
    }

    /**
     * toString 方法
     *
     * @return string
     */
    public String toString() {
        if (builder == null) {
            return "";
        } else {
            return builder.toString();
        }
    }
}
