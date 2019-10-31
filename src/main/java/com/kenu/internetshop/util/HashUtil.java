package com.kenu.internetshop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

public class HashUtil {
    private static final Logger logger = Logger.getLogger(HashUtil.class);
    private static final int LENGTH_OF_ARRAY = 16;

    public static byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[LENGTH_OF_ARRAY];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("Can't get SHA-512", e);
        }
        return hashedPassword.toString();
    }
}
