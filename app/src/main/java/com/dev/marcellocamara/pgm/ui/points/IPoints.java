package com.dev.marcellocamara.pgm.ui.points;

import com.dev.marcellocamara.pgm.model.CardModel;
import com.dev.marcellocamara.pgm.ui.IProgressLoading;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface IPoints {

    interface Presenter {

        void OnAddPoints(String value);

        void OnSubtractPoints(String value);

        void OnDestroy();

    }

    interface View extends IProgressLoading {

        void OnUpdatePointsSuccessful(String points);

        void OnUpdatePointsFailure(String message);

        void OnValueEmpty();

        void OnAddPointsFailure();

        void OnSubtractPointsFailure();

    }

    interface Model {

        void DoUpdateCardPoints(CardModel card);

    }

}