package com.dev.marcellocamara.pgm.Contract;

public interface IMain {

    interface Presenter {

        void OnLogout();

    }

    interface View {

        void OnLogoutSuccessful();

    }

    interface Model {

        void DoLogout();

    }

}
