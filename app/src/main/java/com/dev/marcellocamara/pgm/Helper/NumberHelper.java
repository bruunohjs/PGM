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

            //.x
            if (decimals.length() < 2) {
                decimals = decimals + "0";
            //.xx
            }else {
                //.xxx
                if (decimals.length() > 2){
                    //.99x
                    if ( ( (Character.getNumericValue(decimals.charAt(0))) == 9 )  &&
                            ( (Character.getNumericValue(decimals.charAt(1))) == 9 ) &&
                            (decimals.charAt(2) > 5) ){
                        //.995+
                        decimals = "00";
                        Split[0] = String.valueOf( (Integer.parseInt(Split[0]) ) + 1 );

                    }
                }

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
