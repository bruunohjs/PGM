package com.dev.marcellocamara.pgm.Contract;

import android.graphics.Bitmap;
import android.net.Uri;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IPhoto {

    void getBitmap(Bitmap bitmap);

    void getUri(Uri uri);

}
