package com.dev.marcellocamara.pgm.Contract;

public interface IMain {

    interface Presenter {

        void OnRequestUserData();

        void OnLogout();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name, String email);

        void OnLogoutSuccessful();

    }

    interface Model {

        String GetUserDisplayName();

        String GetUserEmail();

        void DoLogout();

    }

}