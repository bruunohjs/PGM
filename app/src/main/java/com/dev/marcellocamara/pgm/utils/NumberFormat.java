package com.dev.marcellocamara.pgm.utils;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NumberFormat {

    public static String getDecimal(double value){

        return String.format("%.2f", value);

    }

    public static String getMonth(int month){

        String result = String.valueOf(month);

        if ( (result.length()) < 2 ) {

            result = "0" + result;

        }

        return result;

    }

}
