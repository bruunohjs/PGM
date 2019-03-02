package com.dev.marcellocamara.pgm.Contract;

import android.app.Activity;
import android.graphics.Bitmap;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IProfile {

    interface Presenter {

        void OnRequestUserData();

        void OnUpdateUserName(String newName, String nameSaved);

        void OnCheckPermissions(Activity activity);

        void OnCheckBitmap(Bitmap bitmap);

        void OnCheckFilePath(String filePath);

        void OnDestroy();
    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name, String email);

        void OnUpdateUserNameSuccessful();

        void OnUpdateUserNameFailure(String message);

        void OnCheckPermissionsSuccessful();

        void OnSetUserImage(Bitmap bitmap);

        void OnSetUserImage(String filePath);

        void OnSetUserImageFailure();

        void OnBlankField();

    }


    interface Model {

        String GetUserDisplayName();

        void DoUpdateUserName(String name);

        String GetUserEmail();

    }

}