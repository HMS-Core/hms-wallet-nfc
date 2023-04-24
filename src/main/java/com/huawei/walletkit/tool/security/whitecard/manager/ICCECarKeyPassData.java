package com.huawei.walletkit.tool.security.whitecard.manager;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.model.Certificate;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ICCECarKeyPassData extends PassData{
    private static final String TAG = "ICCECarKeyPassData";
    private static final String AES_TEMP_KEY_PREV_PART = "34810233";

    /**
     * Get personalize data, the data must be encrypted with applet personalize public key
     *
     * @param appletPublicKey applet personalize public key
     * @return Personalize data in json
     */
    public static String getPersonalizeData(String appletPublicKey) {
        JSONObject rootObj = new JSONObject();
        String aesKey = AesUtils.getAesKey(16);
        String aesIv = AesUtils.getAesIV();
        LogUtil.info(TAG, "getPersonalizeData, aesKey=" + LogUtil.parseSensitiveinfo(aesKey));
        LogUtil.info(TAG, "getPersonalizeData, aesIv=" + LogUtil.parseSensitiveinfo(aesIv));

        // Encrypt AES key with applet identity public key by RSA,
        // you must add a fix prev hex string to the key before you encrypt it.
        LogUtil.info(TAG, "getPersonalizeData, aesKey2=" + LogUtil.parseSensitiveinfo(AES_TEMP_KEY_PREV_PART + aesKey));
        String tempKey = DataConvertUtil.encryptToHexByPublicKey(
                DataConvertUtil.hexStringToByteArray(AES_TEMP_KEY_PREV_PART + aesKey),
                appletPublicKey, DataConvertUtil.PERSONALIZE_DATA_ENCRYPT_MODE);
        // Encrypt AES IV with applet identity public key by RSA
        String tempIv = DataConvertUtil.encryptToHexByPublicKey(DataConvertUtil.hexStringToByteArray(aesIv),
                appletPublicKey, DataConvertUtil.PERSONALIZE_DATA_ENCRYPT_MODE);

        String cardId = getCardId();
        byte[] filledCardId = fillData(cardId);
        String cardIdEncrypt = AesUtils.encryptToHex(filledCardId, aesKey, aesIv, AesUtils.AES_CBC_NOPADDING);

        // Encrypt card key with applet identity public key by RSA,
        // you must add a fix prev hex string to the key before you encrypt it.
        String srcCardId = getCardKey();
        LogUtil.info(TAG, "getPersonalizeData, srcCardId=" + LogUtil.parseSensitiveinfo(srcCardId));
        String cardKey = DataConvertUtil.encryptToHexByPublicKey(DataConvertUtil.hexStringToByteArray(srcCardId),
                appletPublicKey, DataConvertUtil.PERSONALIZE_DATA_ENCRYPT_MODE);

        // Fill card info data
        byte[] filledCardInfoInfo = fillData(getCardInfo() + getCardAuthParameter());
        // Encrypt filled card info data by AES
        String cardInfoEncrypt = AesUtils.encryptToHex(filledCardInfoInfo, aesKey, aesIv, AesUtils.AES_CBC_NOPADDING);

        // Fill private info data
        byte[] filledPrivateInfo = fillData(getPrivateInfo());
        // Encrypt filled privet info data by AES
        String privateInfoEncrypt = AesUtils.encryptToHex(filledPrivateInfo, aesKey, aesIv, AesUtils.AES_CBC_NOPADDING);

        rootObj.put("temp_key", tempKey);
        rootObj.put("temp_iv", tempIv);
        rootObj.put("card_id", cardIdEncrypt);
        rootObj.put("card_key", cardKey);
        rootObj.put("card_info", cardInfoEncrypt);
        rootObj.put("card_privateInfo", privateInfoEncrypt);
        rootObj.put("card_key_iv", getCardKeyIv());
        LogUtil.info(TAG, "Personalize:" + rootObj.toString(), true);
        return rootObj.toString();
    }

    /**
     * Fill personal data before encrypt it.
     *
     * @param srcData personal data
     * @return filled byte array
     */
    private static byte[] fillData(String srcData) {
        byte[] srcBytes = DataConvertUtil.hexStringToByteArray(srcData);
        if (srcBytes.length == 0) {
            LogUtil.info("fillData, srcBytes length error, length=0");
            return null;
        }
        byte[] lengthBytes = DataConvertUtil.dataLengthToBytes(srcBytes.length);
        if (lengthBytes.length == 0) {
            LogUtil.info("fillData, data length error, length=0");
            return null;
        }
        int totalLength = (lengthBytes.length + srcBytes.length + 15) / 16 * 16;
        byte[] dstBytes = new byte[totalLength];
        Arrays.fill(dstBytes, (byte) 0);
        System.arraycopy(lengthBytes, 0, dstBytes, 0, lengthBytes.length);
        System.arraycopy(srcBytes, 0, dstBytes, lengthBytes.length, srcBytes.length);
        if (lengthBytes.length + srcBytes.length < totalLength) {
            // First filled byte must be 0x80, others be 0x00.
            dstBytes[lengthBytes.length + srcBytes.length] = (byte) 0x80;
        }
        return dstBytes;
    }

    /**
     * Private card data，this is a test data, developer must fix the return data
     */
    private static String getPrivateInfo() {
        // todo 需要替换为真实值
        return "00000000000000000000000000000000";
    }

    private static String getCardInfo() {
        // todo cardInfo需要替换为真实值
        String cardInfo = "xxxxxxxxx";
        return getTLV("9F05", cardInfo);
    }

    private static String getCardAuthParameter() {
        // todo cardAuthParameter需要替换为真实值
        String cardAuthParameter = "xxxxxxxxx";
        return getTLV("9F31", cardAuthParameter);
    }

    private static String getTLV(String tag, String value) {
        StringBuilder buffer = new StringBuilder();
        String lc = "";
        if (value.length() > 254 && value.length() < 300) {
            lc = "81";
        }
        lc = lc + Integer.toHexString(value.length() / 2);
        return buffer.append(tag).append(lc).append(value).toString();
    }

    /**
     * The unique ID of card，this is a test data, developer must fix the return data
     */
    private static String getCardId() {
        //todo 替换成你自己的卡号，即serialNumber。16字节长度
        return "9F3B" + "10" + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    }

    /**
     * The private key of card，this is a test data, developer must fix the return data.
     */
    private static String getCardKey() {
        //todo 替换成你自己的密钥；对应联盟文档中的Dkey；用于SessionKey的计算
        return "34010133" + "2533f4afaa126ad44909b61291e82a7f";
    }

    private static String getCardKeyIv(){
        return "1000000000000000000000000000000000";
    }
}
