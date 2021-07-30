/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.whitecard.manager;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.model.PersonalizeRequest;
import com.huawei.walletkit.tool.security.model.PersonalizeResponse;
import com.huawei.walletkit.tool.security.model.RegistrationsRequest;
import com.huawei.walletkit.tool.security.model.RegistrationsResponse;
import com.huawei.walletkit.tool.security.model.RequestTokenRequest;
import com.huawei.walletkit.tool.security.model.RequestTokenResponse;
import com.huawei.walletkit.tool.security.model.Certificate;
import com.huawei.walletkit.tool.security.model.PassDataResponse;
import com.huawei.walletkit.tool.security.model.RequestBody;
import com.huawei.walletkit.tool.security.model.Response;
import com.huawei.walletkit.tool.security.model.UnregistrationsRequest;
import com.huawei.walletkit.tool.security.model.UnregistrationsResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;

/**
 * Deal with client request.
 *
 * @since 2020-01-19
 */
public class WhiteCardManager {
    private static final String TAG = "WhiteCardManager";

    private static final WhiteCardManager INSTANCE = new WhiteCardManager();
    /**
     * The device that requested the certificate, save the corresponding certificate information<deviceId, spCert>
     */
    private Map<String, Certificate> deviceIdCertMap = new HashMap<>();
    /**
     * Cached personal data tokens
     */
    private Map<String, String> deviceIdTokenMap = new HashMap<>();
    /**
     * The public identity key of the applet passed in the wallet certificate, used to verify the wallet request data
     */
    private String appletAuthPublicKey;

    public static WhiteCardManager getInstance() {
        return INSTANCE;
    }

    private WhiteCardManager() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * Deal with the register request
     *
     * @param token   Token from the header of request, need to match with the token of Linkeddevicepass
     * @param request Register request
     * @return Response data for return
     */
    public RegistrationsResponse dealWithRegisterRequest(String token, RegistrationsRequest request) {
        LogUtil.info(TAG, "Register: token=" + token, true);
        RegistrationsResponse response = new RegistrationsResponse();
        if (token == null) {
            LogUtil.info("Register:Invalid token.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        if (!ParamChecker.isValidRegistrationsRequest(request)) {
            LogUtil.info("Register:Some key param is empty.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        Certificate walletCertificate = request.getCertificate();
        if (!verifyWalletCert(walletCertificate)) {
            LogUtil.info("Register:Verify wallet cert failed.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }

        if (!ParamChecker.hashSignatureCheck(request.toJsonString(), request.getSignature(),
                appletAuthPublicKey, DataConvertUtil.SIGN_MODE_SHA256_RSA_MGF1)) {
            LogUtil.info("dealWithRegisterRequest, check body sign fail.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }

        String userDeviceId = request.getRequestBody().getUserDeviceId();
        response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_OK));
        Response responseBody = new Response();
        Certificate serverCert = PassData.getServerCert(appletAuthPublicKey);
        responseBody.setCertificate(serverCert);
        response.setResponse(responseBody);
        LogUtil.info(TAG, "Register:{deviceId=" + userDeviceId + ", cert=" + serverCert + "}", true);
        deviceIdCertMap.put(userDeviceId, serverCert);
        return response;
    }

    /**
     * Check the wallet certificate and obtain the applet identity public key
     *
     * @param walletCert Wallet certificate
     * @return Check result
     */
    private boolean verifyWalletCert(Certificate walletCert) {
        String appletPublicKey = walletCert.getPublicKey();
        String signature = walletCert.getSignature();
        if (CommonUtils.isStringEmpty(appletPublicKey) || CommonUtils.isStringEmpty(signature)) {
            LogUtil.info("verifyWalletCert, public key or signature is empty.");
            return false;
        }
        if (!(DataConvertUtil.checkSign(appletPublicKey, signature, Constants.WALLET_PUBLIC_KEY,
            DataConvertUtil.SIGN_MODE_SHA256_RSA)
            || DataConvertUtil.checkSign(appletPublicKey, signature, Constants.WALLET_PUBLIC_KEY,
            DataConvertUtil.SIGN_MODE_SHA256_RSA_PSS))) {
            LogUtil.info("verifyWalletCert, checkSign fail.");
            return false;
        }
        LogUtil.info("verifyWalletCert, success.");
        appletAuthPublicKey = appletPublicKey;
        return true;
    }

    /**
     * Deal with the request for personalize token
     *
     * @param request Token request
     * @return Response data for return
     */
    public RequestTokenResponse dealWithTokenRequest(RequestTokenRequest request) {
        RequestTokenResponse response = new RequestTokenResponse();
        String userDeviceId = request.getRequestBody().getUserDeviceId();
        Certificate spCertFromRequest = request.getCertificate();
        if (CommonUtils.isStringEmpty(userDeviceId) || !ParamChecker.checkSpServerCertificate(spCertFromRequest)) {
            LogUtil.info("RequestToken:User device id or sp certificate is empty");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        Certificate cachedCert = deviceIdCertMap.get(userDeviceId);
        if (cachedCert == null) {
            LogUtil.info("RequestToken:Certificate not match");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }
        if (!ParamChecker.hashSignatureCheck(request.toJsonString(), request.getSignature(),
                appletAuthPublicKey, DataConvertUtil.SIGN_MODE_SHA256_RSA_MGF1)) {
            LogUtil.info("dealWithTokenRequest, check body sign fail.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }
        response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_OK));
        Response responseBody = new Response();
        String personalizeToken = PassData.getPersonalizeToken();
        responseBody.setToken(personalizeToken);
        deviceIdTokenMap.put(userDeviceId, personalizeToken);
        response.setResponse(responseBody);
        return response;
    }

    /**
     * Deal with the request for personalize data
     *
     * @param token   Token for personalize data
     * @param request Request for personalize data
     * @return Response data for return
     */
    public PersonalizeResponse dealWithPersonalizeDataRequest(String token, PersonalizeRequest request) {
        PersonalizeResponse response = new PersonalizeResponse();
        RequestBody requestBody = request.getRequestBody();
        String userDeviceId = requestBody.getUserDeviceId();
        Certificate spCertFromRequest = request.getCertificate();
        LogUtil.info(TAG, "Personalize: token=" + LogUtil.parseSensitiveinfo(token));
        String cachedToken = deviceIdTokenMap.remove(userDeviceId);
        if (cachedToken == null || !cachedToken.equals(token)) {
            LogUtil.info("Personalize:Invalid token.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        if (CommonUtils.isStringEmpty(userDeviceId) || !ParamChecker.checkSpServerCertificate(spCertFromRequest)) {
            LogUtil.info("Personalize:User device id or sp certificate is empty");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }

        Certificate cachedCert = deviceIdCertMap.get(userDeviceId);
        if (cachedCert == null) {
            LogUtil.info("Personalize:Certificate not match");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        if (!ParamChecker.hashSignatureCheck(request.toJsonString(token), request.getSignature(),
                appletAuthPublicKey, DataConvertUtil.SIGN_MODE_SHA256_RSA_MGF1)) {
            LogUtil.info("dealWithPersonalizeDataRequest, check body sign fail.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }
        String personalizePKSign = requestBody.getPersonalizeCert();
        byte[] srcBytes = DataConvertUtil.base64Decode(requestBody.getPersonalizePublicKey());
        if (!DataConvertUtil.checkSign(srcBytes, personalizePKSign,
                appletAuthPublicKey, DataConvertUtil.SIGN_MODE_SHA256_RSA_MGF1)) {
            LogUtil.info("dealWithPersonalizeDataRequest, check PK sign fail.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }
        response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_OK));
        PassDataResponse passData = getDevicePassData(requestBody);
        response.setResponse(passData);
        String passDataStr = passData.toJsonString();
        String passHashValue = DataConvertUtil.encodeSHA256(passDataStr);
        LogUtil.info(TAG, "Personalize, body=" + LogUtil.parseSensitiveinfo(passDataStr));
        LogUtil.info(TAG, "Personalize, passHashValue=" + LogUtil.parseSensitiveinfo(passHashValue));
        String signature = DataConvertUtil.signData(passHashValue, Constants.SERVER_SECRET_KEY);
        LogUtil.info(TAG, "Personalize, signature=" + LogUtil.parseSensitiveinfo(signature));
        response.setSignature(signature);
        return response;
    }

    private PassDataResponse getDevicePassData(RequestBody requestBody) {
        DevicePassUnit devicePassUnit = new DevicePassUnit();
        String serialNumber = requestBody.getSerialNumber();
        LogUtil.info(TAG, "getDevicePassData, serialNumber=" + serialNumber, true);
        devicePassUnit.setSerialNumber(serialNumber);
        devicePassUnit.setOrganizationPassId(serialNumber);
        devicePassUnit.setPassVersion(requestBody.getPassVersion());
        devicePassUnit.setPassTypeIdentifier(requestBody.getPassTypeIdentifier());
        PassDataResponse devicePassJson = devicePassUnit.toJson(requestBody.getTransId(),
                requestBody.getTransPublicKey(), requestBody.getPersonalizePublicKey());
        return devicePassJson;
    }

    /**
     * Deal with the unregister request
     *
     * @param request Request for unregister
     * @return Response data for return
     */
    public UnregistrationsResponse dealWithUnregisterRequest(UnregistrationsRequest request) {
        UnregistrationsResponse response = new UnregistrationsResponse();
        String userDeviceId = request.getRequestBody().getUserDeviceId();
        if (CommonUtils.isStringEmpty(userDeviceId)) {
            LogUtil.info("Personalize:User device id or sp certificate is empty");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        Certificate cachedCert = deviceIdCertMap.remove(userDeviceId);
        if (cachedCert == null) {
            LogUtil.info("Personalize:Certificate not exist.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        String signature = request.getSignature();
        String jsonString = request.toJsonString();
        LogUtil.info(TAG, "dealWithUnregisterRequest, jsonString=" + jsonString, true);
        if (!DataConvertUtil.checkSign(jsonString, signature, Constants.WALLET_PUBLIC_KEY,
                DataConvertUtil.SIGN_MODE_SHA256_RSA_PSS)) {
            LogUtil.info("dealWithUnregisterRequest, checkSign fail.");
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_SIGN_ERROR));
            return response;
        }
        LogUtil.info("dealWithUnregisterRequest, success.");
        response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_OK));
        return response;
    }
}
