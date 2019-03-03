package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IMain;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class MainPresenter implements IMain.Presenter, ITaskListener {

    private IMain.View view;
    private IMain.Model model;

    public MainPresenter(IMain.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName(), model.GetUserEmail());
        if (model.GetUserPhotoUri() != null){
            view.OnSetUserImage(model.GetUserPhotoUri());
        }
    }

    @Override
    public void OnLogout() {
        model.DoLogout();
    }

    @Override
    public void OnSuccess() {
        if ( view!= null ){
            view.OnLogoutSuccessful();
        }
    }

    @Override
    public void OnError(String message) { }
}
