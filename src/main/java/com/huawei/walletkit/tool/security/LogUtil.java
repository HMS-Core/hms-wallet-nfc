/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LogUtil
 *
 * @author lWX883636
 * @since 2020-07-16
 */
public class LogUtil {
    private static final Logger LOGGER = Logger.getLogger(LogUtil.class.getName());

    private static final String SYMBOL = "*";

    /**
     * log set level
     *
     * @param newLevel Level
     */
    public static void setLevel(Level newLevel) {
        LOGGER.setLevel(newLevel);
    }

    /**
     * get log level
     *
     * @return Level
     */
    public static Level getLevel() {
        return LOGGER.getLevel();
    }

    /**
     * info
     *
     * @param msg String
     */
    public static void info(String msg) {
        info("", msg);
    }

    /**
     * info
     *
     * @param tag String
     * @param msg String
     */
    public static void info(String tag, String msg) {
        info(tag, msg, false);
    }

    /**
     * info
     *
     * @param tag             String
     * @param msg             String
     * @param isSensitiveInfo boolean
     */
    public static void info(String tag, String msg, boolean isSensitiveInfo) {
        LOGGER.info(getMsg(tag, msg, isSensitiveInfo));
    }


    /**
     * warning
     *
     * @param msg String
     */
    public static void warning(String msg) {
        warning("", msg);
    }

    /**
     * warning
     *
     * @param tag String
     * @param msg String
     */
    public static void warning(String tag, String msg) {
        warning("", msg, false);
    }

    /**
     * warning
     *
     * @param tag             String
     * @param msg             String
     * @param isSensitiveInfo boolean
     */
    public static void warning(String tag, String msg, boolean isSensitiveInfo) {
        LOGGER.warning(getMsg(tag, msg, isSensitiveInfo));
    }


    private static String getMsg(String tag, String msg, boolean isSensitiveInfo) {
        String log = "";
        if (tag != null && !tag.isEmpty()) {
            log = tag + ":";
        }
        if (msg != null) {
            if (isSensitiveInfo) {
                log = parseSensitiveinfo(log + msg);
            } else {
                log = log + msg;
            }
        }
        return log;
    }

    /**
     * parseSensitiveinfo
     *
     * @param log String
     * @return String
     */
    public static String parseSensitiveinfo(String log) {
        if (null == log || log.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < log.length(); i++) {
            if (i % 2 == 0) {
                stringBuilder.append(log.charAt(i));
            } else {
                stringBuilder.append(SYMBOL);
            }
        }
        return stringBuilder.toString();
    }
}
