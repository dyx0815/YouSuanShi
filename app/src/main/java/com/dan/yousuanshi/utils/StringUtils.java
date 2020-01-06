package com.dan.yousuanshi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }
    public static boolean isMobilePhone(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isPassword(String password){
        String regex="^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(password);
        boolean isMatch=m.matches();
        return isMatch;
    }
}
