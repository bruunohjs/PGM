package com.dev.marcellocamara.pgm.Helper;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardHelper {

    public static Drawable getBackground(Context context, int drawable){
        switch (drawable){
            case 1 : {
                return (context.getResources().getDrawable(R.drawable.card_yellow));
            }
            case 2 : {
                return (context.getResources().getDrawable(R.drawable.card_purple));
            }
            case 3 : {
                return (context.getResources().getDrawable(R.drawable.card_green));
            }
            case 4 : {
                return (context.getResources().getDrawable(R.drawable.card_grey));
            }
            case 5 : {
                return (context.getResources().getDrawable(R.drawable.card_red));
            }
            default : {
                return null;
            }
        }
    }

    public static Drawable getFlag(Context context, int flag) {
        switch (flag){
            case 1 : {
                return (context.getResources().getDrawable(R.drawable.flag_mastercard));
            }
            case 2 : {
                return (context.getResources().getDrawable(R.drawable.flag_visa));
            }
            case 3 : {
                return (context.getResources().getDrawable(R.drawable.flag_elo));
            }
            default : {
                return null;
            }
        }
    }

    public static int getColor(Context context, int flag) {
        switch (flag){
            case 1 : {
                return (context.getResources().getColor(R.color.cardYellow));
            }
            case 2 : {
                return (context.getResources().getColor(R.color.cardPurple));
            }
            case 3 : {
                return (context.getResources().getColor(R.color.cardGreen));
            }
            case 4 : {
                return (context.getResources().getColor(R.color.cardGrey));
            }
            case 5 : {
                return (context.getResources().getColor(R.color.cardRed));
            }
            default : {
                return 0;
            }
        }
    }

}