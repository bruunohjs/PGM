package com.dev.marcellocamara.pgm.Helper;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NumberHelper {

    public static String GetDecimal(double value){

        String result = String.valueOf(value);

        String Split[] = result.split("\\.");

        if ( (Split[1].length()) > 0 ) {

            String decimals = Split[1];

            if (decimals.length() < 2) {
                decimals = decimals + "0";
            }

            return ( (Split[0]) + "." + (decimals.charAt(0)) + "" + (decimals.charAt(1)) );

        }else {

            return result;

        }

    }

    public static String GetMonth(int month){

        String result = String.valueOf(month);

        if ( (result.length()) < 2 ) {

            result = "0" + result;

        }

        return result;

    }

}
