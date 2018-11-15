package com.wytianxiatuan.wytianxia.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/25 0025.
 */

public class RegesUtils {
    /**
     * 判断都是数字，并且长度是11位
     */
    public static boolean IsPhone(String phNum) {

        // 手机号验证规则
        String regEx = "^[1][34578][0-9]{9}$";//^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phNum);
        // 括号里的是判断是不是手机号并且长度必须是11位，如果是进入方法判断长度
        if (matcher.matches() && phNum.length() == 11) {
            return true;
        }
        return false;
    }
}
