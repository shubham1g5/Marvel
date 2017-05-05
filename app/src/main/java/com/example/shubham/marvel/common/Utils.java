package com.example.shubham.marvel.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String MD5(String md5) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(md5.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String toSentenceCase(String input){
        if(input == null || input.isEmpty()){
            return input;
        }

        String result = Character.toUpperCase(input.charAt(0)) + "";
        if(input.length() > 1){
            result += input.substring(1);
        }
        return result;
    }
}
