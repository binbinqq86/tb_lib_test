package com.tb.baselib.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;

/**
 * Created by : tb on 2017/7/20 上午9:19.
 * Description :字符串转数字专用处理工具类
 */
public class NumberUtils {
    
    public static int objToInt(Object args) {
        int value = 0;
        if (args == null || !TextUtils.isEmpty(args.toString().trim())) {
            try {
                value = Integer.parseInt(args.toString());
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static int stringToInt(String args) {
        int value = 0;
        if (!TextUtils.isEmpty(args)) {
            try {
                value = Integer.parseInt(args);
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static double objToDouble(Object args) {
        double value = 0;
        if (args == null || !TextUtils.isEmpty(args.toString().trim())) {
            try {
                value = Double.parseDouble(args.toString());
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static double stringToDouble(String args) {
        double value = 0;
        if (!TextUtils.isEmpty(args)) {
            try {
                value = Double.parseDouble(args);
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static float objToFloat(Object args) {
        float value = 0;
        if (args == null || !TextUtils.isEmpty(args.toString().trim())) {
            try {
                value = Float.parseFloat(args.toString());
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static float stringToFloat(String args) {
        float value = 0;
        if (!TextUtils.isEmpty(args)) {
            try {
                value = Float.parseFloat(args);
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static long objToLong(Object args) {
        long value = 0;
        if (args == null || !TextUtils.isEmpty(args.toString().trim())) {
            try {
                value = Long.parseLong(args.toString());
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    public static long stringToLong(String args) {
        long value = 0;
        if (!TextUtils.isEmpty(args)) {
            try {
                value = Long.parseLong(args);
            } catch (NumberFormatException e) {
                value = 0;
            }
        }
        return value;
    }
    
    /**
     * 大数据处理
     *
     * @param money 需要处理的钱，可以是字符串或者浮点，整型
     * @param isFen 默认单位为分还是元——true代表分，false代表元
     * @param num   保留的小数位数(目前只支持一位或两位小数)
     * @return
     */
    public static String formatMoney(Object money, boolean isFen, int num) {
        String pattern = "###,##0";
        if (num == 1) {
            pattern += ".0";
        } else if (num == 2) {
            pattern += ".00";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String value = "0.00";
        if (money == null) {
            return value;
        }
        String mMoney = String.valueOf(money);
        if (!TextUtils.isEmpty(mMoney)) {
            if (stringToDouble(mMoney) == 0) {
                return value;
            }
            BigDecimal bigDecimal = new BigDecimal(mMoney);
            try {
                if (isFen) {
                    return decimalFormat.format(bigDecimal.divide(new BigDecimal(100), num, BigDecimal.ROUND_DOWN)).toString();
                } else {
                    return decimalFormat.format(bigDecimal.setScale(num, BigDecimal.ROUND_DOWN)).toString();
                }
            } catch (ArithmeticException e) {
                return value;
            } catch (NullPointerException e) {
                return value;
            }
        }
        return value;
    }
    
}
