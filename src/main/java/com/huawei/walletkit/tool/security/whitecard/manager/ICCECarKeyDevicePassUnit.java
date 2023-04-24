package com.huawei.walletkit.tool.security.whitecard.manager;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.model.PassDataResponse;

public class ICCECarKeyDevicePassUnit extends DevicePassUnit{
    private static final String TAG = "ICCECarKeyDevicePassUnit";
    @Override
    public PassDataResponse toJson(String transId, String taPublicKey, String appletPersonalizePublicKey) {
        generatePassData(transId, taPublicKey, appletPersonalizePublicKey);
        PassDataResponse dataInfo = new PassDataResponse();
        dataInfo.setEncryptAppletPersonalizeFields(encryptAppletPersonalizeFields);
        dataInfo.setEncryptSessionKey(encryptSessionKey);
        return dataInfo;
    }

    @Override
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

        // get personalize data
        String personalizeData = ICCECarKeyPassData.getPersonalizeData(appletPublicKey);
        LogUtil.info(TAG, "generatePassData, personalizeData=" + LogUtil.parseSensitiveinfo(personalizeData));
        // Encrypt personalize data with key-iv
        encryptAppletPersonalizeFields =
                AesUtils.encryptToBase64(personalizeData, aesKey, aesIv, AesUtils.AES_CBC_PKCS_5_PADDING);
        LogUtil.info(TAG, "generatePassData, encryptAppletPersonalizeFields=" + encryptAppletPersonalizeFields, true);
    }
}
