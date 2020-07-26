/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

/**
 * Class of pass data
 *
 * @since 2018/08/11
 */
public class PassDataInfo {
    /**
     * Pass structure version
     */
    private String formatVersion;

    /**
     * Pass type
     */
    private String passTypeIdentifier;

    /**
     * Pass Style
     */
    private String passStyleIdentifier;

    /**
     * Issuer name
     */
    private String organizationName;

    /**
     * Id or issuer
     */
    private String organizationPassId;

    /**
     * URL to the sp server
     */
    private String webServiceURL;

    private String authorizationToken;

    private String serialNumber;

    private String createPassTime;

    private PassDataField fields;

    private String passVersion;

    public PassDataField getFields() {
        return fields;
    }

    public void setFields(PassDataField fields) {
        this.fields = fields;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public String getPassTypeIdentifier() {
        return passTypeIdentifier;
    }

    public void setPassTypeIdentifier(String passTypeIdentifier) {
        this.passTypeIdentifier = passTypeIdentifier;
    }

    public String getPassStyleIdentifier() {
        return passStyleIdentifier;
    }

    public void setPassStyleIdentifier(String passStyleIdentifier) {
        this.passStyleIdentifier = passStyleIdentifier;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationPassId() {
        return organizationPassId;
    }

    public void setOrganizationPassId(String organizationPassId) {
        this.organizationPassId = organizationPassId;
    }

    public String getWebServiceURL() {
        return webServiceURL;
    }

    public void setWebServiceURL(String webServiceURL) {
        this.webServiceURL = webServiceURL;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCreatePassTime() {
        return createPassTime;
    }

    public void setCreatePassTime(String createPassTime) {
        this.createPassTime = createPassTime;
    }

    public String getPassVersion() {
        return passVersion;
    }

    public void setPassVersion(String passVersion) {
        this.passVersion = passVersion;
    }

}
