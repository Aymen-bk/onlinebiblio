package com.library.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {
    
    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }
    
    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
