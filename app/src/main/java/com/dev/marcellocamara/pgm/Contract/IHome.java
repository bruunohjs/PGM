package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IHome {

    interface Presenter {

        void OnLogout();

        void OnDestroy();

    }

    interface View {

        void OnLogoutSuccessful();

    }

    interface Model {

        void DoLogout();

    }

}
