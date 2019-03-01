package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IProfile {

    interface Presenter {

        void OnRequestUserData();

        void OnUpdateUserName(String newName, String nameSaved);

        void OnDestroy();
    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name, String email);

        void OnUpdateUserNameSuccessful();

        void OnUpdateUserNameFailure(String message);

        void OnBlankField();

    }


    interface Model {

        String GetUserDisplayName();

        void DoUpdateUserName(String name);

        String GetUserEmail();

    }

}