/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.whitecard.manager;

import static com.huawei.walletkit.tool.security.whitecard.manager.Constants.ICCE_CARKEY_PASSTYPE_PREFIX;

/**
 * Common utils
 *
 * @since 2020-01-19
 */
public class CommonUtils {
    /**
     * Check if string is empty
     *
     * @param str string to be checked
     * @return check result
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if passTypeIdentify belongs to ICCE
     * @param passTypeIdentifier passTypeIdentifier to be checked
     * @return check result
     */
    public static boolean isICCECarKey(String passTypeIdentifier) {
        return passTypeIdentifier != null && passTypeIdentifier.contains(ICCE_CARKEY_PASSTYPE_PREFIX);
    }
}
