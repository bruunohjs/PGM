package com.dev.marcellocamara.pgm.utils;

import android.graphics.Color;
import android.widget.ImageView;

/***
    marcellocamara@id.uff.br
            2019
***/

public class Tooltip {

    /**
     * receives params to build and show the tooltip
     * @param imageView - view to anchor the tooltip
     * @param gravity - int value of Gravity
     * @param text - message displayed at tooltip
     * @param background - paints the background tooltip with an int color id
     */
    public static void show(ImageView imageView, int gravity, String text, int background){
        new com.tooltip.Tooltip.Builder(imageView)
                .setText(text)
                .setCancelable(true)
                .setBackgroundColor(background)
                .setTextColor(Color.WHITE)
                .setGravity(gravity)
                .setCornerRadius(8f)
                .show();
    }

}