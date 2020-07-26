/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

import java.io.Serializable;

/**
 * Data field of pass
 *
 * @since 2018/08/11
 */
public class PassDataFieldInfo implements Serializable {
    private static final long serialVersionUID = -6148888288911963340L;

    /**
     * Key of the field
     */
    private String key;

    /**
     * Labek of the field
     */
    private String label;

    /**
     * Value of the field
     */
    private String value;

    /**
     * Redirect url
     */
    private String redirectUrl;

    private String localizedLabel;

    private String localizedValue;

    public PassDataFieldInfo() {
    }

    public PassDataFieldInfo(String key, String label, String value) {
        this.key = key;
        this.label = label;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocalizedLabel() {
        return localizedLabel;
    }

    public void setLocalizedLabel(String localizedLabel) {
        this.localizedLabel = localizedLabel;
    }

    public String getLocalizedValue() {
        return localizedValue;
    }

    public void setLocalizedValue(String localizedValue) {
        this.localizedValue = localizedValue;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
