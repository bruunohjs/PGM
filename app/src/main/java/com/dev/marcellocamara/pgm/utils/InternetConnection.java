package com.dev.marcellocamara.pgm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dev.marcellocamara.pgm.App;

/***
    marcellocamara@id.uff.br
            2019
***/

public class InternetConnection {

    public static boolean hasInternet() {

        Context context = App.getContext();

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());

    }

}