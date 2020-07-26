/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

/**
 * Request body data
 *
 * @since 2020-01-17
 */
public class RequestBody {
    private String passTypeIdentifier;

    private String serialNumber;

    private String passVersion;

    private String userDeviceId;

    private String personalizePublicKey;

    private String personalizeCert;

    private String personalizeCertType;

    private String transPublicKey;

    private String transId;

    public String getPassTypeIdentifier() {
        return passTypeIdentifier;
    }

    public void setPassTypeIdentifier(String passTypeIdentifier) {
        this.passTypeIdentifier = passTypeIdentifier;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPassVersion() {
        return passVersion;
    }

    public void setPassVersion(String passVersion) {
        this.passVersion = passVersion;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getPersonalizePublicKey() {
        return personalizePublicKey;
    }

    public void setPersonalizePublicKey(String personalizePublicKey) {
        this.personalizePublicKey = personalizePublicKey;
    }

    public String getPersonalizeCert() {
        return personalizeCert;
    }

    public void setPersonalizeCert(String personalizeCert) {
        this.personalizeCert = personalizeCert;
    }

    public String getTransPublicKey() {
        return transPublicKey;
    }

    public void setTransPublicKey(String transPublicKey) {
        this.transPublicKey = transPublicKey;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getPersonalizeCertType() {
        return personalizeCertType;
    }

    public void setPersonalizeCertType(String personalizeCertType) {
        this.personalizeCertType = personalizeCertType;
    }
}
