/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.whitecard.manager;

import com.huawei.walletkit.tool.security.LogUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Tool class for data convert
 *
 * @since 2020-01-19
 */
public class DataConvertUtil {
    /**
     * The encryption mode of wallet TA
     */
    public static final String TA_PUBLIC_KEY_ENCRYPT_MODE = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /**
     * The encryption mode of applet
     */
    public static final String PERSONALIZE_DATA_ENCRYPT_MODE = "RSA/NONE/OAEPWithSHA1AndMGF1Padding";

    /**
     * The signature algorithm, used by wallet server
     */
    public static final String SIGN_MODE_SHA256_RSA = "SHA256WithRSA";

    /**
     * The signature algorithm, used by applet
     */
    public static final String SIGN_MODE_SHA256_RSA_MGF1 = "SHA256WithRSAandMGF1";

    /**
     * Encrypt with SHA256 to get the hash value of string
     *
     * @param str String to be encrypted
     * @return String after encryption，hex format
     */
    public static String encodeSHA256(String str) {
        MessageDigest messageDigest;
        String encodedResult = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(Constants.UTF8_ENCODING));
            encodedResult = byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.info("DataConvertUtil", "NoSuchAlgorithmException " + LogUtil.parseSensitiveinfo(e.toString()));
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("DataConvertUtil", "UnsupportedEncodingException " + LogUtil.parseSensitiveinfo(e.toString()));
        }
        return encodedResult;
    }

    /**
     * RSA encryption with public key
     *
     * @param data data to be encrypted
     * @param key public key, encoded in Base64
     * @param mode encryption mode
     * @return encryption result, encoded in base64 mode
     */
    public static String encryptToBase64ByPublicKey(String data, String key, String mode) {
        if (CommonUtils.isStringEmpty(key) || CommonUtils.isStringEmpty(mode) || CommonUtils.isStringEmpty(data)) {
            LogUtil.info("encryptByPublicKey, some param is empty.");
            return null;
        }
        try {
            byte[] byteResult = encryptByPublicKey(data.getBytes(Constants.UTF8_ENCODING), key, mode);
            if (byteResult != null) {
                return base64EncodeToString(byteResult);
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("encryptByPublicKey, UnsupportedEncodingException.");
        }
        return null;
    }

    /**
     * RSA encryption with public key
     *
     * @param data data to be encrypted
     * @param key public key, encoded in Base64
     * @param mode encryption mode
     * @return encryption result
     */
    public static String encryptToHexByPublicKey(String data, String key, String mode) {
        if (CommonUtils.isStringEmpty(key) || CommonUtils.isStringEmpty(mode) || CommonUtils.isStringEmpty(data)) {
            LogUtil.info("encryptByPublicKey, some param is empty.");
            return null;
        }
        try {
            return encryptToHexByPublicKey(data.getBytes(Constants.UTF8_ENCODING), key, mode);
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("encryptByPublicKey, UnsupportedEncodingException.");
        }
        return null;
    }

    /**
     * RSA encryption with public key
     *
     * @param data data to be encrypted
     * @param key public key, encoded in Base64
     * @param mode encryption mode
     * @return encryption result
     */
    public static String encryptToHexByPublicKey(byte[] data, String key, String mode) {
        byte[] byteResult = encryptByPublicKey(data, key, mode);
        if (byteResult == null) {
            return null;
        } else {
            return byteArrayToHexString(byteResult);
        }
    }

    /**
     * RSA encryption with public key
     *
     * @param data data to be encrypted
     * @param key public key, encoded in Base64
     * @param mode encryption mode
     * @return encryption result, byte array
     */
    private static byte[] encryptByPublicKey(byte[] data, String key, String mode) {
        if (CommonUtils.isStringEmpty(key) || CommonUtils.isStringEmpty(mode) || data == null) {
            LogUtil.info("encryptByPublicKey, some param is empty.");
            return null;
        }
        PublicKey pubKey = generateRSAPublicKey(key);
        if (pubKey == null) {
            LogUtil.info("encryptByPublicKey， generateRSAPublicKey error.");
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(mode);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey NoSuchAlgorithmException.");
        } catch (NoSuchPaddingException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey NoSuchPaddingException. ");
        } catch (InvalidKeyException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey InvalidKeyException.");
        } catch (IllegalBlockSizeException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey IllegalBlockSizeException.");
        } catch (BadPaddingException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey BadPaddingException.");
        }
        return null;
    }

    /**
     * RSA signature
     *
     * @param data data to be signed
     * @param privateKeyStr private key
     * @return signature result
     */
    public static String signData(String data, String privateKeyStr) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] keyBytes = decoder.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(SIGN_MODE_SHA256_RSA);
            signature.initSign(privateKey);
            signature.update(data.getBytes(Constants.UTF8_ENCODING));
            byte[] mi = signature.sign();
            return base64EncodeToString(mi);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey NoSuchAlgorithmException.");
        } catch (InvalidKeyException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey InvalidKeyException.");
        } catch (InvalidKeySpecException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey InvalidKeySpecException.");
        } catch (SignatureException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey SignatureException.");
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("requestBoardWhiteCardInfo encryptByPublicKey UnsupportedEncodingException.");
        }
        return null;
    }

    /**
     * Check sign with RSA
     *
     * @param content Unsigned content
     * @param sign Signature content, encoded in base64
     * @param publicKey public key, encoded in base64
     * @param signMode sign mode
     * @return boolean Signature check result
     */
    public static boolean checkSign(String content, String sign, String publicKey, String signMode) {
        if (CommonUtils.isStringEmpty(content)) {
            LogUtil.info("checkSign， some param is empty.");
            return false;
        }
        try {
            byte[] srcBytes = (content.getBytes(Constants.UTF8_ENCODING));
            return checkSign(srcBytes, sign, publicKey, signMode);
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("checkSign:UnsupportedEncodingException");
        }
        return false;
    }

    /**
     * Check sign with RSA
     *
     * @param content Unsigned content
     * @param sign Signature content, encoded in base64
     * @param publicKey public key, encoded in base64
     * @param signMode sign mode
     * @return boolean Signature check result
     */
    public static boolean checkSign(byte[] content, String sign, String publicKey, String signMode) {
        if (content == null || CommonUtils.isStringEmpty(sign) || CommonUtils.isStringEmpty(publicKey)
            || CommonUtils.isStringEmpty(signMode)) {
            LogUtil.info("checkSign， some param is empty.");
            return false;
        }
        PublicKey pubKey = generateRSAPublicKey(publicKey);
        if (pubKey == null) {
            LogUtil.info("checkSign， generateRSAPublicKey error.");
            return false;
        }
        try {
            Signature signature = Signature.getInstance(signMode);
            signature.initVerify(pubKey);
            signature.update(content);
            byte[] signatureBytes = base64Decode(sign);
            return signature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.info("checkSign:NoSuchAlgorithmException");
        } catch (InvalidKeyException e) {
            LogUtil.info("checkSign:InvalidKeyException");
        } catch (SignatureException e) {
            LogUtil.info("checkSign:SignatureException");
        }
        return false;
    }

    private static PublicKey generateRSAPublicKey(String publicKey) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encodedKey = decoder.decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException e) {
            LogUtil.info("generateRSAPublicKey:NoSuchAlgorithmException");
        } catch (InvalidKeySpecException e) {
            LogUtil.info("generateRSAPublicKey:InvalidKeySpecException");
        } catch (IllegalArgumentException e) {
            LogUtil.info("generateRSAPublicKey:IllegalArgumentException");
        }
        return null;
    }

    /**
     * Convert byte array to string in base64 format
     *
     * @param valueByte data to be converted
     * @return Base64 encoded string
     */
    public static String base64EncodeToString(byte[] valueByte) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(valueByte);
    }

    /**
     * Decode string in base64 format
     *
     * @param srcData data to be converted
     * @return byte array decoded
     */
    public static byte[] base64Decode(String srcData) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            return decoder.decode(srcData);
        } catch (IllegalArgumentException e) {
            LogUtil.info("base64Decode, IllegalArgumentException");
            return null;
        }
    }

    /**
     * Convert byte array to hex string
     *
     * @param bytes Byte array to be converted
     * @return Hex string result
     */
    public static String byteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int temp;
        for (int j = 0; j < bytes.length; j++) {
            temp = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[temp >>> 4];
            hexChars[j * 2 + 1] = hexArray[temp & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Convert hex string to byte array
     *
     * @param s hex string
     * @return byte array result
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Get the byte error of data length, adapter to applet encryption
     *
     * @param length data length
     * @return byte array of data length
     */
    public static byte[] dataLengthToBytes(int length) {
        byte[] result = null;
        if (length < 128) {
            result = new byte[1];
            result[0] = (byte) length;
        } else if (length < 256) {
            result = new byte[2];
            result[0] = (byte) 0x81;
            result[1] = (byte) length;
        } else if (length < 65536) {
            result = new byte[3];
            result[0] = (byte) 0x82;
            result[1] = (byte) (length >> 8);
            result[2] = (byte) length;
        } else {
            return new byte[0];
        }
        return result;
    }
}
