package com.dev.marcellocamara.pgm.Contract;

import android.app.Activity;
import android.net.Uri;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IProfile {

    interface Presenter {

        void OnRequestUserData();

        void OnUpdateUserName(String newName, String nameSaved);

        void OnCheckPermissions(Activity activity);

        void OnCheckUri(Uri uri, String format);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name, String email);

        void OnUpdateUserSuccessful();

        void OnUpdateUserFailure(String message);

        void OnCheckPermissionsSuccessful();

        void OnSetUserImage(Uri uri);

        void OnBlankField();

    }

    interface Model {

        String GetUserDisplayName();

        String GetUserEmail();

        Uri GetUserPhotoUri();

        void DoUpdateUserName(String name);

        void DoUpdateUserImage(Uri uri, String format);

    }

}