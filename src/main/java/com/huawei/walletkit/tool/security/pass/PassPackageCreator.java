/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.pass;

import com.huawei.walletkit.tool.security.LogUtil;
import com.huawei.walletkit.tool.security.whitecard.manager.Constants;
import com.huawei.walletkit.tool.security.whitecard.manager.DataConvertUtil;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class to create pass package
 *
 * @author z00323379
 * @since 2020-02-26
 */
public class PassPackageCreator {
    /**
     * Create pass package and return the string encoded by base64
     *
     * @param passDataInfo Pass info
     * @return Pass package info encoded with base64
     */
    public static String createPassPackage(PassDataInfo passDataInfo) {
        if (passDataInfo == null) {
            LogUtil.info("createPassPackage, passDataInfo is null.");
            return null;
        }
        // 1. Get content of file hwpass.json
        JSONObject passObj = JSONObject.fromObject(passDataInfo);
        String passJson = passObj.toString();
        // 2. Get content of file mainfest.json
        String hashValue = DataConvertUtil.encodeSHA256(passJson);
        JSONObject manifestJson = new JSONObject();
        manifestJson.put("hwpass.json", hashValue);
        String manifestContent = manifestJson.toString();
        // 3. Signature to the content of mainifest.json file, return the content of file signature
        String signature = DataConvertUtil.signData(manifestContent, Constants.SERVER_SECRET_KEY);
        // 4. Write the above three contents to three zip files and generate the zip file
        String filePath = getTempPassPath();
        if (filePath == null) {
            LogUtil.info("createPassPackage, get pass path fail.");
            return null;
        }
        if (!generatePassZipFile(passJson, manifestContent, signature, filePath)) {
            LogUtil.info("createPassPackage, generate pass zip file fail.");
            return null;
        }
        String passData = readPassData(filePath);
        File file = new File(filePath);
        boolean deleteResult = file.delete();
        LogUtil.info("createPassPackage, delete file result=" + deleteResult);
        return passData;
    }

    private static String readPassData(String file) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(file);
            int ch;
            while ((ch = inputStream.read()) != -1) {
                byteStream.write(ch);
            }
            byte[] passByteData = byteStream.toByteArray();
            return DataConvertUtil.base64EncodeToString(passByteData);
        } catch (IOException e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtil.info("readPassData, close inputStream error.");
                }
            }
            if (byteStream != null) {
                try {
                    byteStream.close();
                } catch (IOException e) {
                    LogUtil.info("readPassData, close byteStream error.");
                }
            }
        }
    }

    private static String getTempPassPath() {
        String tempDirPath = "/hwpass";
        File dirFile = new File(tempDirPath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                LogUtil.info("createPassPackage, mkdir ");
                return null;
            }
        }
        return tempDirPath + "/" + "common.zip";
    }

    private static boolean generatePassZipFile(String passJson, String manifest, String signature, String zipFilePath) {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
            generateZipEntry("hwpass.json", passJson, zipOut);
            generateZipEntry("manifest.json", manifest, zipOut);
            generateZipEntry("signature", signature, zipOut);
        } catch (FileNotFoundException e) {
            LogUtil.info("FileNotFoundException");
            return false;
        } catch (IOException e) {
            LogUtil.info("IOException");
            return false;
        } finally {
            if (zipOut != null) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    LogUtil.info("Close zipOut fail.");
                }
            }
            LogUtil.info("Success.");
        }
        return true;
    }

    private static void generateZipEntry(String fileName, String content, ZipOutputStream zipOut)
            throws IOException, UnsupportedEncodingException {
        zipOut.putNextEntry(new ZipEntry(fileName));
        byte[] bytes = content.getBytes("UTF-8");
        zipOut.write(bytes);
        zipOut.closeEntry();
    }
}
