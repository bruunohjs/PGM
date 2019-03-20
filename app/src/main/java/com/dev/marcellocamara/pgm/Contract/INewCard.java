package com.dev.marcellocamara.pgm.Contract;

import com.dev.marcellocamara.pgm.Model.CardModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface INewCard {

    interface Presenter {

        void OnRequestUserData();

        void OnCheckCardDataUpdate(CardModel card);

        void OnGetFinalNumbers(String numbers);

        void OnAddCard(String title, String numbers, String date, int cardColor, int cardFlag);

        void OnUpdateCard(CardModel card, String title, String numbers, String date, int cardColor, int cardFlag);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnRequestUserDataSuccessful(String name);

        void OnCheckCardDataUpdateSuccessful();

        void OnSetZeroFinalNumbers();

        void OnSetOneFinalNumber(String num);

        void OnSetTwoFinalNumbers(String num);

        void OnSetThreeFinalNumbers(String num);

        void OnSetFourFinalNumbers(String num);

        void OnTitleEmpty();

        void OnFinalNumbersInvalid();

        void OnDateInvalid();

        void OnDateInvalidValue();

        void OnAddCardSuccess();

        void OnAddCardFailure(String message);

        void OnUpdateCardFailure();

    }

    interface Model {

        String GetUserDisplayName();

        void DoAddCard(String title, String numbers, String date, int cardColor, int cardFlag);

        void DoUpdateCard(String uniqueId, String title, String numbers, String date, int cardColor, int cardFlag);

    }

}