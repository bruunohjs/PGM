package com.dev.marcellocamara.pgm.ui.profile;

import android.app.Activity;
import android.net.Uri;

import com.dev.marcellocamara.pgm.ui.IProgressLoading;

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

        void OnNoChangesUpdate();

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