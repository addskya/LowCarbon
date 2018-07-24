package com.mylowcarbon.app.utils;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 *
 */

public class AmountUtil {
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String getToltalAmountToRMB(int price, float num) {
        float resoult= (float)price *num/100;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        return "¥" + df.format(resoult);
    }

    public static String getFeesToLCL(int price, float num ,float rate) {
        float resoult = (float)price * num * rate / (100*100);
        DecimalFormat df = new DecimalFormat("0.00000000");//格式化小数
        return "LCL" + df.format(resoult);
    }
    public static String getFeesToLCL(float price, float num ,float rate) {
        float resoult = price * num * rate / 100 ;
        DecimalFormat df = new DecimalFormat("0.00000000");//格式化小数
        return "LCL" + df.format(resoult);
    }

    public static String priceToRMB(int price) {
        float resoult= (float)price/100;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        return "¥" +  df.format(resoult);
    }

}
