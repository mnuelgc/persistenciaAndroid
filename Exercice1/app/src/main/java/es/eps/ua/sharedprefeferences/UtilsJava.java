package es.eps.ua.sharedprefeferences;

import android.util.Base64;

class UtilsJava {
    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }
    public static String decrypt(String input) {
        return new String(Base64.decode(input.getBytes(), Base64.DEFAULT));
    }
}