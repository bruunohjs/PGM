package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface INewCard {

    interface Presenter {

        void OnRequestUserData();

        void OnGetFinalNumbers(String numbers);

        void OnAddCard(String title, String numbers, String date, String annuity, int cardColor, int cardFlag);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name);

        void OnSetZeroFinalNumbers();

        void OnSetOneFinalNumber(String num);

        void OnSetTwoFinalNumbers(String num);

        void OnSetThreeFinalNumbers(String num);

        void OnSetFourFinalNumbers(String num);

        void OnTitleEmpty();

        void OnFinalNumbersInvalid();

        void OnDateInvalid();

        void OnAnnuityInvalid();

        void OnDateInvalidValue();

        void OnAnnuityInvalidValue();

        void OnAddCardSuccess();

    }

    interface Model {

        String GetUserDisplayName();

        //List<CardModel> GetUserCardList();

        void DoAddCard(String title, String numbers, String date, String annuity, int cardColor, int cardFlag);

    }

}