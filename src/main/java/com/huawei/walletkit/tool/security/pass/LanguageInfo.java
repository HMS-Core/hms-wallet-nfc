/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

/**
 * Multi-language info
 *
 * @since 2018/09/2
 */
public class LanguageInfo {
    /**
     * Key value of the message
     */
    private String key;

    /**
     * Language type
     */
    private String language;

    /**
     * Value in the language
     */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
