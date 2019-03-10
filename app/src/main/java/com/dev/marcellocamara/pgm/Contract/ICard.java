package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ICard {

    interface Presenter {

        void OnRequestUserData();

        void OnGetFinalNumbers(String numbers);

        void OnDestroy();

    }

    interface View {

        void OnRequestUserDataSuccessful(String name);

        void OnSetZeroFinalNumbers();

        void OnSetOneFinalNumber(String num);

        void OnSetTwoFinalNumbers(String num);

        void OnSetThreeFinalNumbers(String num);

        void OnSetFourFinalNumbers(String num);

    }

    interface Model {

        String GetUserDisplayName();

    }

}