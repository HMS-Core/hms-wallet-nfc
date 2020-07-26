/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.whitecard.manager;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.model.Certificate;
import com.huawei.walletkit.tool.security.model.RegistrationsRequest;
import com.huawei.walletkit.tool.security.model.RequestBody;

import java.nio.charset.Charset;

/**
 * Parameters checker
 *
 * @since 2020-01-19
 */
public class ParamChecker {
    private static final String TAG = "ParamChecker";

    /**
     * Check if the certificate is valid
     *
     * @param certificate certificate to be checked
     * @return true if valid, else false
     */
    public static boolean isValidCertificate(Certificate certificate) {
        if (certificate == null || CommonUtils.isStringEmpty(certificate.getSignature())
                || CommonUtils.isStringEmpty(certificate.getPublicKey())) {
            return false;
        }
        return true;
    }

    /**
     * Check the register request
     *
     * @param request register request
     * @return true if valid, else false
     */
    public static boolean isValidRegistrationsRequest(RegistrationsRequest request) {
        if (request == null) {
            return false;
        }
        RequestBody requestBody = request.getRequestBody();
        if (requestBody == null || CommonUtils.isStringEmpty(requestBody.getUserDeviceId())
                || CommonUtils.isStringEmpty(requestBody.getSerialNumber())
                || CommonUtils.isStringEmpty(requestBody.getPassTypeIdentifier())) {
            return false;
        }
        if (CommonUtils.isStringEmpty(request.getSignature())) {
            return false;
        }
        if (!isValidCertificate(request.getCertificate())) {
            return false;
        }
        return true;
    }

    /**
     * Signature verification of the hash value of the target string
     *
     * @param content   target string
     * @param signature signature value
     * @param publicKey public key
     * @param mode      sign mode
     * @return true if verify success, else false
     */
    public static boolean hashSignatureCheck(String content, String signature, String publicKey, String mode) {
        String hashValue = DataConvertUtil.encodeSHA256(content);
        LogUtil.info(TAG, "hashSignatureCheck, content=" + content + ", hashValue=" + hashValue, true);
        boolean result = DataConvertUtil.checkSign(hashValue, signature, publicKey, mode);
        LogUtil.info(TAG, "hashSignatureCheck, result=" + result);
        return result;
    }

    /**
     * Check sp certificate
     *
     * @param certificate sp certificate
     * @return true if valid, else false
     */
    public static boolean checkSpServerCertificate(Certificate certificate) {
        if (!isValidCertificate(certificate)) {
            return false;
        }
        boolean result = DataConvertUtil.checkSign(certificate.getPublicKey(),
                certificate.getSignature(), Constants.SERVER_PUBLIC_KEY, DataConvertUtil.SIGN_MODE_SHA256_RSA);
        LogUtil.info("checkSpServerCertificate, result=" + result);
        return result;
    }

    /**
     * Check if the string equals to the byte array
     *
     * @param bytes     byte array
     * @param hashValue string
     * @return true if equal, else false
     */
    public static boolean checkHashValueEqual(byte[] bytes, String hashValue) {
        if (bytes == null || hashValue == null) {
            return false;
        }
        String resultStr = new String(bytes, Charset.defaultCharset());
        return resultStr.trim().equals(hashValue);
    }
}
