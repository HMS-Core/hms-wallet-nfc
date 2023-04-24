/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.whitecard.manager;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.model.PassDataResponse;
import com.huawei.walletkit.tool.security.pass.PassDataField;
import com.huawei.walletkit.tool.security.pass.PassDataInfo;
import com.huawei.walletkit.tool.security.pass.PassPackageCreator;

/**
 * Basic data of pass
 *
 * @since 2020-01-19
 */
public class DevicePassUnit {
    private static final String TAG = "DevicePassUnit";

    private String passVersion = "2.0";

    private String organizationPassId = "34221688191";

    private String serialNumber = "34221688191";

    private String passTypeIdentifier = "huawei.test.device.pass.0001";

    private String organizationName = "Test Orginization";

    protected String encryptSessionKey;

    protected String encryptUserDeviceFields;

    protected String encryptAppletPersonalizeFields;

    /**
     * Get encrypted pass info and personalize data
     *
     * @param transId                    TA transId
     * @param taPublicKey                TA temp public key
     * @param appletPersonalizePublicKey Personalize public key from applet
     * @return Pass data
     */
    public PassDataResponse toJson(String transId, String taPublicKey, String appletPersonalizePublicKey) {
        generatePassData(transId, taPublicKey, appletPersonalizePublicKey);
        PassDataResponse dataInfo = new PassDataResponse();
        dataInfo.setEncryptAppletPersonalizeFields(encryptAppletPersonalizeFields);
        dataInfo.setEncryptDevicePass(encryptUserDeviceFields);
        dataInfo.setEncryptSessionKey(encryptSessionKey);
        return dataInfo;
    }

    protected void generatePassData(String transId, String taPublicKey, String appletPublicKey) {
        LogUtil.info(TAG, "generatePassData, transId=" + LogUtil.parseSensitiveinfo(transId));
        LogUtil.info(TAG, "generatePassData, taPublicKey=" + LogUtil.parseSensitiveinfo(taPublicKey));
        LogUtil.info(TAG, "generatePassData, appletPublicKey=" + LogUtil.parseSensitiveinfo(appletPublicKey));
        String aesKey = AesUtils.getAesKey(16);
        String aesIv = AesUtils.getAesIV();
        String originKey = transId + aesKey + aesIv;
        // Encrypt originKey with taPublicKey
        encryptSessionKey = DataConvertUtil.encryptToBase64ByPublicKey(originKey, taPublicKey,
                DataConvertUtil.TA_PUBLIC_KEY_ENCRYPT_MODE);
        LogUtil.info(TAG, "generatePassData, encryptSessionKey=" + LogUtil.parseSensitiveinfo(encryptSessionKey));
        // Get pass package data
        String passPackageData = generatePassPackageData();
        LogUtil.info(TAG, "generatePassData, passPackageData=" + passPackageData, true);
        // Encrypt pass data with key-iv
        encryptUserDeviceFields =
                AesUtils.encryptToBase64(passPackageData, aesKey, aesIv, AesUtils.AES_CBC_PKCS_5_PADDING);
        LogUtil.info(TAG, "generatePassData, encryptUserDeviceFields=" + encryptUserDeviceFields, true);
        // get personalize data
        String personalizeData = PassData.getPersonalizeData(appletPublicKey);
        LogUtil.info(TAG, "generatePassData, personalizeData=" + LogUtil.parseSensitiveinfo(personalizeData));
        // Encrypt personalize data with key-iv
        encryptAppletPersonalizeFields =
                AesUtils.encryptToBase64(personalizeData, aesKey, aesIv, AesUtils.AES_CBC_PKCS_5_PADDING);
        LogUtil.info(TAG, "generatePassData, encryptAppletPersonalizeFields=" + encryptAppletPersonalizeFields, true);
    }

    private String generatePassPackageData() {
        PassDataInfo passDataInfo = new PassDataInfo();
        passDataInfo.setPassVersion(passVersion);
        passDataInfo.setOrganizationPassId(organizationPassId);
        passDataInfo.setOrganizationName(organizationName);
        passDataInfo.setPassTypeIdentifier(passTypeIdentifier);
        passDataInfo.setSerialNumber(serialNumber);
        passDataInfo.setPassStyleIdentifier("DevicePassStyle");
        PassDataField fields = new PassDataField();
        passDataInfo.setFields(fields);
        String dataInfo = PassPackageCreator.createPassPackage(passDataInfo);
        LogUtil.info(TAG, "dataInfo:" + dataInfo, true);
        return dataInfo;
    }

    public String getPassVersion() {
        return passVersion;
    }

    public void setPassVersion(String passVersion) {
        this.passVersion = passVersion;
    }

    public String getOrganizationPassId() {
        return organizationPassId;
    }

    public void setOrganizationPassId(String organizationPassId) {
        this.organizationPassId = organizationPassId;
    }

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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
