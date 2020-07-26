/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

/**
 * Barcode class
 *
 * @since 2019-10-23
 */
public class BarCodeInfo {
    private String text;

    /**
     * Type of barcode, maybe the value "codabar", "qrCode", "textOnly" etc.
     */
    private String type;

    private String value;

    private String encoding;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
