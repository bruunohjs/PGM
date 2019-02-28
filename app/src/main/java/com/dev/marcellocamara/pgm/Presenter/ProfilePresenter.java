package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IProfile;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ProfilePresenter implements IProfile.Presenter, ITaskListener {

    private IProfile.View view;
    private IProfile.Model model;

    public ProfilePresenter(IProfile.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnError(String message) {

    }
}
