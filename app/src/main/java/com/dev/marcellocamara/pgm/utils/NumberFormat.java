package com.dev.marcellocamara.pgm.utils;

import android.annotation.SuppressLint;

import com.dev.marcellocamara.pgm.model.ExpenseModel;

import java.util.List;
import java.util.Locale;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NumberFormat {

    public static final int MAX_POINTS = 999999999;

    @SuppressLint("DefaultLocale")
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

    public static String getTotalExpenses(List<ExpenseModel> list){

        double total = 0.00;

        if ( !(list.isEmpty()) ){
            for (ExpenseModel expenseModel : list){
                total += ( (expenseModel.getPrice()) / (Double.parseDouble(expenseModel.getInstallments())) );
            }
        }

        return NumberFormat.getDecimal(total);

    }

    public static String getIntSeparated(int value){

        return String.format(
                Locale.getDefault(),
                "%,d",
                value
        );

    }

}
