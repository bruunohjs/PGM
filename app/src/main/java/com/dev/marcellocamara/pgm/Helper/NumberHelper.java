package com.dev.marcellocamara.pgm.Helper;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NumberHelper {

    public static String GetDecimal(double value){

        return String.format("%.2f", value);

    }

    public static String GetMonth(int month){

        String result = String.valueOf(month);

        if ( (result.length()) < 2 ) {

            result = "0" + result;

        }

        return result;

    }

}
