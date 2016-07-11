package br.com.base.extras;

import java.text.DecimalFormat;

public class Functions {

    public static String decimalFormatMoney(double value) {
        return new DecimalFormat("##,##0.00").format(value);
    }

    public static String decimalFormat(double value) {
        return new DecimalFormat("#.###").format(value);
    }
}