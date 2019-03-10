package com.dev.marcellocamara.pgm.Contract;

import android.net.Uri;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IDialog {

    interface Photo {

        void getUri(Uri uri);

    }

    interface Color {

        void getSelectedColor(int color);

    }

    interface Flag {

        void getFlag(int flag);

    }

}
