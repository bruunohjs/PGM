package com.dev.marcellocamara.pgm.Helper;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;

/***
    marcellocamara@id.uff.br
            2019
***/

public class TooltipHelper {

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