package com.dev.marcellocamara.pgm;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/***
    marcellocamara@id.uff.br
            2019
***/

public class App extends Application {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        MultiDex.install(getContext());
    }

}