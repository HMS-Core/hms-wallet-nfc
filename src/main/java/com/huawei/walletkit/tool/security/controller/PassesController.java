/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.controller;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.model.PersonalizeRequest;
import com.huawei.walletkit.tool.security.model.PersonalizeResponse;
import com.huawei.walletkit.tool.security.model.RegistrationsRequest;
import com.huawei.walletkit.tool.security.model.RegistrationsResponse;
import com.huawei.walletkit.tool.security.model.RequestTokenRequest;
import com.huawei.walletkit.tool.security.model.RequestTokenResponse;
import com.huawei.walletkit.tool.security.model.UnregistrationsRequest;
import com.huawei.walletkit.tool.security.model.UnregistrationsResponse;
import com.huawei.walletkit.tool.security.whitecard.manager.Constants;
import com.huawei.walletkit.tool.security.whitecard.manager.DataConvertUtil;
import com.huawei.walletkit.tool.security.whitecard.manager.WhiteCardManager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

/**
 * PassesController
 *
 * @since 2020-01-16
 */
@RestController
public class PassesController {
    /**
     * register
     *
     * @param res HttpServletRequest
     * @param request RegistrationsRequest
     * @return ResponseEntity
     */
    @PostMapping(path = "/v1/passes/registrations")
    public ResponseEntity<RegistrationsResponse> register(HttpServletRequest res,
        @RequestBody RegistrationsRequest request) {
        return ResponseEntity.ok(WhiteCardManager.getInstance().dealWithRegisterRequest(extractToken(res), request));
    }

    /**
     * requestToken
     *
     * @param request RequestTokenRequest
     * @return ResponseEntity
     */
    @PostMapping(path = "/v1/passes/requestToken/personalize")
    public ResponseEntity<RequestTokenResponse> requestToken1(@RequestBody RequestTokenRequest request) {
        return ResponseEntity.ok(WhiteCardManager.getInstance().dealWithTokenRequest(request));
    }

    /**
     * requestToken
     *
     * @param request RequestTokenRequest
     * @return ResponseEntity
     */
    @PostMapping(path = "/v1/passes/requestPersonalizeToken")
    public ResponseEntity<RequestTokenResponse> requestToken(@RequestBody RequestTokenRequest request) {
        return ResponseEntity.ok(WhiteCardManager.getInstance().dealWithTokenRequest(request));
    }

    /**
     * getPersonalInfo
     *
     * @param res HttpServletRequest
     * @param request PersonalizeRequest
     * @return ResponseEntity
     */
    @PostMapping(path = "/v1/passes/requestPersonalize")
    public ResponseEntity<PersonalizeResponse> getPersonalInfo(HttpServletRequest res,
        @RequestBody PersonalizeRequest request) {
        return ResponseEntity
            .ok(WhiteCardManager.getInstance().dealWithPersonalizeDataRequest(extractToken(res), request));
    }

    /**
     * unregister
     *
     * @param request UnregistrationsRequest
     * @return ResponseEntity
     */
    @PostMapping(path = "/v1/passes/unregistrations")
    public ResponseEntity<UnregistrationsResponse> unregister(@RequestBody UnregistrationsRequest request) {
        return ResponseEntity.ok(WhiteCardManager.getInstance().dealWithUnregisterRequest(request));
    }

    /**
     * Get the token from the request, and extract and decode according to the format
     * to get the original value of the token
     *
     * @param res Request
     * @return token value or null
     */
    private String extractToken(HttpServletRequest res) {
        String clientToken = res.getHeader(Constants.TOKEN_KEY_VALUE);
        if (clientToken == null) {
            LogUtil.info("extractToken, error token is null");
            return null;
        }

        if (!clientToken.startsWith(Constants.TOKEN_PREV_PART)) {
            LogUtil.info("PassesController", "extractToken, error token=" + LogUtil.parseSensitiveinfo(clientToken));
            return null;
        }

        String tokenBase64 = clientToken.substring(Constants.TOKEN_PREV_PART.length());
        byte[] tokenBytes = DataConvertUtil.base64Decode(tokenBase64);
        if (tokenBytes == null) {
            LogUtil.info("extractToken, base64 decode fail.");
            return null;
        }
        try {
            String token = new String(tokenBytes, "UTF-8");
            return token;
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("extractToken, convert token fail.");
            return null;
        }
    }
}
