package com.tb.baselib.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by : tb on 2017/7/20 上午9:19.
 * Description :字符串转数字专用处理工具类
 */
public class NumberUtils {
    public static final String TYPE_NONE = "";
    public static final String TYPE_¥ = "¥";
    public static final String TYPE_FEN = "分";
    public static final String TYPE_YUAN = "元";
    public static final String TYPE_WAN = "万";
    public static final String TYPE_SHIWAN = "十万";
    public static final String TYPE_BAIWAN = "百万";
    public static final String TYPE_QIANWAN = "千万";
    public static final String TYPE_YI = "亿";
    public static final String TYPE_SHIYI = "十亿";
    public static final String TYPE_BAIYI = "百亿";
    public static final String TYPE_QIANYI = "千亿";
    public static final String TYPE_WANYI = "万亿";
    
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
     * 大数据处理，按照指定的格式来展示数字
     *
     * @param obj      需要处理的数据，可以是字符串或者浮点，整型
     * @param divider  除数(0,10,100,1000,10000...)
     * @param decimal  保留的小数位数(目前只支持0位,1位,2位,3位,4位小数)
     * @param isFormat 数字是否三位一个逗号隔开(千分位)
     * @param need¥    是否需要¥符号
     * @param suffix   是否需要单位（参考：{@link #TYPE_YUAN,#TYPE_WAN,#TYPE_FEN}）
     * @return
     */
    public static String formatNumber(Object obj, int divider, int decimal, boolean isFormat, boolean need¥, String suffix) {
        return formatNumber(obj, divider, decimal, BigDecimal.ROUND_DOWN, isFormat, need¥, suffix);
    }
    
    /**
     * 大数据处理，按照指定的格式来展示数字
     *
     * @param obj      需要处理的数据，可以是字符串或者浮点，整型
     * @param divider  除数(0,10,100,1000,10000...)
     * @param decimal  保留的小数位数(目前只支持0位,1位,2位,3位,4位小数)
     * @param round    四舍五入还是舍弃类似的参考值 {@link BigDecimal#ROUND_DOWN,BigDecimal#ROUND_UP ...}
     * @param isFormat 数字是否三位一个逗号隔开(千分位)
     * @param need¥    是否需要¥符号
     * @param suffix   是否需要单位（参考：{@link #TYPE_YUAN,#TYPE_WAN,#TYPE_FEN}）
     * @return
     */
    public static String formatNumber(Object obj, int divider, int decimal, int round, boolean isFormat, boolean need¥, String suffix) {
        String pattern = isFormat ? "###,##0" : "##0";
        if (decimal == 1) {
            pattern += ".0";
        } else if (decimal == 2) {
            pattern += ".00";
        } else if (decimal == 3) {
            pattern += ".000";
        } else if (decimal == 4) {
            pattern += ".0000";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String value = decimalFormat.format(0) + suffix;
        if (need¥) {
            value = TYPE_¥ + value;
        }
        if (obj == null) {
            return value;
        }
        String mMoney = String.valueOf(obj);
        if (!TextUtils.isEmpty(mMoney)) {
            BigDecimal bigDecimal = new BigDecimal(mMoney);
            if (bigDecimal.compareTo(BigDecimal.ZERO)==0) {
                return value;
            }
            try {
                if (divider > 0) {
                    value = decimalFormat.format(bigDecimal.divide(new BigDecimal(divider), decimal, round)).toString();
                } else {//小于等于0返回原数据
                    value = decimalFormat.format(bigDecimal.setScale(decimal, round)).toString();
                }
                if (need¥) {
                    value = TYPE_¥ + value;
                }
                return value + suffix;
            } catch (ArithmeticException e) {
                return value;
            } catch (NullPointerException e) {
                return value;
            }
        }
        return value;
    }
}
