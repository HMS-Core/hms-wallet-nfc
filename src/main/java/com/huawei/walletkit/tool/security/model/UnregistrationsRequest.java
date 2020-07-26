/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

import com.huawei.walletkit.tool.security.whitecard.manager.KeyValueConnector;

/**
 * Unregister request
 *
 * @since 2020-01-17
 */
public class UnregistrationsRequest {
    private RequestBody requestBody;

    private String signature;

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Assemble the content of the requestbody in ascending order of Key values
     * according to k=v&k=v format
     *
     * @return formatted string
     */
    public String toJsonString() {
        return new KeyValueConnector().append("passTypeIdentifier", requestBody.getPassTypeIdentifier())
                .append("serialNumber", requestBody.getSerialNumber())
                .append("transId", requestBody.getTransId())
                .append("userDeviceId", requestBody.getUserDeviceId())
                .toString();
    }
}
