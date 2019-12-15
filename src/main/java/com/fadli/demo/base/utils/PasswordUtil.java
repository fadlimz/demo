package com.fadli.demo.base.utils;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class PasswordUtil {

    public static boolean isPasswordValid(String plainTextPassword, String userPassword) {
        return new BasicPasswordEncryptor().checkPassword(plainTextPassword, userPassword);
    }
}
