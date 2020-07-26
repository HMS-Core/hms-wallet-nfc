/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

/**
 * 1.3 Personalize data response
 *
 * @since 2020-01-17
 */
public class PersonalizeResponse {
    /**
     * 200：Success
     * 401：Request error（token error）
     * 402：Signature check error
     */
    private String httpStatus;

    private PassDataResponse response;

    private String signature;

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public PassDataResponse getResponse() {
        return response;
    }

    public void setResponse(PassDataResponse response) {
        this.response = response;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
