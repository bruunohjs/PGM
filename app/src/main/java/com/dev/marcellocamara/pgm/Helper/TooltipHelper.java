package com.dev.marcellocamara.pgm.Helper;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;

/***
    marcellocamara@id.uff.br
            2019
***/

public class TooltipHelper {

    /**
     * receives params to build and show the tooltip
     * @param imageView view to anchor the tooltip
     * @param text - message displayed at tooltip
     * @param background - paints the background tooltip with an int color id
     */
    public static void show(ImageView imageView, String text, int background){
        new com.tooltip.Tooltip.Builder(imageView)
                .setText(text)
                .setCancelable(true)
                .setBackgroundColor(background)
                .setTextColor(Color.WHITE)
                .setGravity(Gravity.TOP)
                .setCornerRadius(8f)
                .show();
    }

}