/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

/**
 * Status of pass
 *
 * @since 2019/06/25
 */
public class PassStatusInfo {
    private String passId;

    /*
     * 0-inactive，1-expired，2-active, 3-pass not exist
     */
    private String status;

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
