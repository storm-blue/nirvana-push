package com.nirvana.purist.utils;

/**
 * Created by Nirvana on 2017/11/15.
 */
public class StringUtils {

    private StringUtils() {}

    public static boolean isBlank(String string) {
        return string == null || string.equals("");
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

}
