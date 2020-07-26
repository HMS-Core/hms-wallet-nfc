/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

import java.util.List;
import java.util.Map;

/**
 * Data fields of pass
 *
 * @since 2018/08/11
 */
public class PassDataField {
    private List<PassDataFieldInfo> commonFields;

    private List<PassDataFieldInfo> appendFields;

    private List<PassDataFieldInfo> messageList;

    private List<PassDataFieldInfo> timeList;

    private List<PassDataFieldInfo> imageList;

    private List<PassDataFieldInfo> textList;

    private Map internationalize;

    private List<LanguageInfo> localized;

    private PassStatusInfo status;

    private BarCodeInfo barCode;

    private String srcPassTypeIdentifier;

    private String srcPassIdentifier;

    private String isUserDiy;

    private String countryCode;

    private String currencyCode;

    private List<PassDataFieldInfo> urlList;

    private List<LocationInfo> locationList;

    private List<PassDataFieldInfo> ticketInfoList;

    public List<PassDataFieldInfo> getCommonFields() {
        return commonFields;
    }

    public void setCommonFields(List<PassDataFieldInfo> commonFields) {
        this.commonFields = commonFields;
    }

    public List<PassDataFieldInfo> getAppendFields() {
        return appendFields;
    }

    public void setAppendFields(List<PassDataFieldInfo> appendFields) {
        this.appendFields = appendFields;
    }

    public Map getInternationalize() {
        return internationalize;
    }

    public void setInternationalize(Map internationalize) {
        this.internationalize = internationalize;
    }

    public PassStatusInfo getStatus() {
        return status;
    }

    public void setStatus(PassStatusInfo status) {
        this.status = status;
    }

    public BarCodeInfo getBarCode() {
        return barCode;
    }

    public void setBarCode(BarCodeInfo barCode) {
        this.barCode = barCode;
    }

    public List<LanguageInfo> getLocalized() {
        return localized;
    }

    public void setLocalized(List<LanguageInfo> localized) {
        this.localized = localized;
    }

    /**
     * getSrcClassId
     *
     * @return String
     */
    public String getSrcClassId() {
        return srcPassTypeIdentifier;
    }

    /**
     * setSrcClassId
     *
     * @param srcPassTypeIdentifier String
     */
    public void setSrcClassId(String srcPassTypeIdentifier) {
        this.srcPassTypeIdentifier = srcPassTypeIdentifier;
    }

    /**
     * getSrcObjectId
     *
     * @return String
     */
    public String getSrcObjectId() {
        return srcPassIdentifier;
    }

    /**
     * setSrcObjectId
     *
     * @param srcPassIdentifier String
     */
    public void setSrcObjectId(String srcPassIdentifier) {
        this.srcPassIdentifier = srcPassIdentifier;
    }

    public String getIsUserDiy() {
        return isUserDiy;
    }

    public void setIsUserDiy(String isUserDiy) {
        this.isUserDiy = isUserDiy;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<LocationInfo> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationInfo> locationList) {
        this.locationList = locationList;
    }

    public List<PassDataFieldInfo> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<PassDataFieldInfo> messageList) {
        this.messageList = messageList;
    }

    public List<PassDataFieldInfo> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<PassDataFieldInfo> timeList) {
        this.timeList = timeList;
    }

    public List<PassDataFieldInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<PassDataFieldInfo> imageList) {
        this.imageList = imageList;
    }

    public List<PassDataFieldInfo> getTextList() {
        return textList;
    }

    public void setTextList(List<PassDataFieldInfo> textList) {
        this.textList = textList;
    }

    public List<PassDataFieldInfo> getTicketInfoList() {
        return ticketInfoList;
    }

    public void setTicketInfoList(List<PassDataFieldInfo> ticketInfoList) {
        this.ticketInfoList = ticketInfoList;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<PassDataFieldInfo> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<PassDataFieldInfo> urlList) {
        this.urlList = urlList;
    }
}
