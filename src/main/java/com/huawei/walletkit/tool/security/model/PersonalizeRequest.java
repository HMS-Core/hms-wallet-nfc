/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

import com.huawei.walletkit.tool.security.whitecard.manager.CommonUtils;
import com.huawei.walletkit.tool.security.whitecard.manager.KeyValueConnector;

/**
 * 1.3 Personalize request
 *
 * @since 2020-01-17
 */
public class PersonalizeRequest {
    private RequestBody requestBody;

    private String signature;

    private Certificate certificate;

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

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     * Assemble the content of the requestbody in ascending order of Key values
     * according to k=v&k=v format
     *
     * @param token Personalize token, used to check the request
     * @return formatted string
     */
    public String toJsonString(String token) {
        KeyValueConnector connector = new KeyValueConnector();
        if (CommonUtils.isICCECarKey(requestBody.getPassTypeIdentifier())) {
            connector.append("cardSEId", requestBody.getCardSEId());
        }

        return connector.append("passTypeIdentifier", requestBody.getPassTypeIdentifier())
                .append("passVersion", requestBody.getPassVersion())
                .append("personalizeCert", requestBody.getPersonalizeCert())
                .append("personalizeCertType", requestBody.getPersonalizeCertType())
                .append("personalizePublicKey", requestBody.getPersonalizePublicKey())
                .append("serialNumber", requestBody.getSerialNumber())
                .append("token", token)
                .append("transId", requestBody.getTransId())
                .append("transPublicKey", requestBody.getTransPublicKey())
                .append("userDeviceId", requestBody.getUserDeviceId())
                .toString();
    }
}
