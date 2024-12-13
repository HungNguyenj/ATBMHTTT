package com.ltweb_servlet_ecommerce.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    private static KeyUtil keyUtil = null;
    public static KeyUtil getInstance() {
        if (keyUtil == null) {
            synchronized (KeyUtil.class) {
                if (keyUtil == null) {
                    keyUtil = new KeyUtil();
                }
            }
        }
        return keyUtil;
    }

    public static String publicKeyToBase64(PublicKey publicKey) {
        if (publicKey == null) {
            throw new IllegalArgumentException("PublicKey cannot be null");
        }
        // Encode the public key to Base64
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String privateKeyToBase64(PrivateKey privateKey) {
        if (privateKey == null) {
            throw new IllegalArgumentException("PrivateKey cannot be null");
        }
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static PrivateKey base64ToPrivateKey(String base64PrivateKey) throws Exception {
        if (base64PrivateKey == null || base64PrivateKey.isEmpty()) {
            throw new IllegalArgumentException("Base64 private key cannot be null or empty");
        }
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePrivate(keySpec);
    }
    public static PublicKey base64ToPublicKey(String base64PublicKey) throws Exception {
        if (base64PublicKey == null || base64PublicKey.isEmpty()) {
            throw new IllegalArgumentException("Base64 public key cannot be null or empty");
        }
        // Decode Base64 string to byte array
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);

        // Generate PublicKey object
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(keySpec);
    }
}
