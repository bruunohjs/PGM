package com.dev.marcellocamara.pgm.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2019
***/

public class CardUtils {

    /**
     * finds the respective drawable of the int background
     * @param context - environment data
     * @param background - int that refers to a specific resource drawable
     * @return resource drawable or null if it does not find
     */
    public static Drawable getBackground(Context context, int background){
        switch (background){
            case 1 : {
                return ContextCompat.getDrawable(context, R.drawable.card_yellow);
            }
            case 2 : {
                return ContextCompat.getDrawable(context, R.drawable.card_purple);
            }
            case 3 : {
                return ContextCompat.getDrawable(context, R.drawable.card_green);
            }
            case 4 : {
                return ContextCompat.getDrawable(context, R.drawable.card_grey);
            }
            case 5 : {
                return ContextCompat.getDrawable(context, R.drawable.card_red);
            }
            case 6 : {
                return ContextCompat.getDrawable(context, R.drawable.card_blue);
            }
            default : {
                return null;
            }
        }
    }

    /**
     * finds the respective drawable of the int flag
     * @param context - environment data
     * @param flag - int that refers to a specific resource drawable
     * @return resource drawable or null if it does not find
     */
    public static Drawable getFlag(Context context, int flag) {
        switch (flag){
            case 1 : {
                return ContextCompat.getDrawable(context, R.drawable.flag_visa);
            }
            case 2 : {
                return ContextCompat.getDrawable(context, R.drawable.flag_mastercard);
            }
            case 3 : {
                return ContextCompat.getDrawable(context, R.drawable.flag_elo);
            }
            case 4 : {
                return ContextCompat.getDrawable(context, R.drawable.flag_american_express);
            }
            case 5 : {
                return ContextCompat.getDrawable(context, R.drawable.flag_dinners_club);
            }
            case 6 : {
                return ContextCompat.getDrawable(context, R.drawable.flag_hipercard);
            }
            default : {
                return null;
            }
        }
    }

    /**
     * finds the respective drawable of the int small flag
     * @param context - environment data
     * @param flag - int that refers to a specific resource drawable
     * @return resource drawable or null if it does not find
     */
    public static Drawable getSmallFlag(Context context, int flag) {
        switch (flag){
            case 1 : {
                return ContextCompat.getDrawable(context, R.drawable.small_flag_visa);
            }
            case 2 : {
                return ContextCompat.getDrawable(context, R.drawable.small_flag_mastercard);
            }
            case 3 : {
                return ContextCompat.getDrawable(context, R.drawable.small_flag_elo);
            }
            case 4 : {
                return ContextCompat.getDrawable(context, R.drawable.small_flag_american_express);
            }
            case 5 : {
                return ContextCompat.getDrawable(context, R.drawable.small_flag_dinners_club);
            }
            case 6 : {
                return ContextCompat.getDrawable(context, R.drawable.small_flag_hipercard);
            }
            default : {
                return null;
            }
        }
    }

    /**
     * finds the respective color of the int color
     * @param context - environment data
     * @param color - int that refers to a specific resource color
     * @return resource color or 0 if it does not find
     */
    public static int getColor(Context context, int color) {
        switch (color){
            case 1 : {
                return ContextCompat.getColor(context, R.color.cardYellow);
            }
            case 2 : {
                return ContextCompat.getColor(context, R.color.cardPurple);
            }
            case 3 : {
                return ContextCompat.getColor(context, R.color.cardGreen);
            }
            case 4 : {
                return ContextCompat.getColor(context, R.color.cardGrey);
            }
            case 5 : {
                return ContextCompat.getColor(context, R.color.cardRed);
            }
            case 6 : {
                return ContextCompat.getColor(context, R.color.cardBlue);
            }
            default : {
                return 0;
            }
        }
    }

}