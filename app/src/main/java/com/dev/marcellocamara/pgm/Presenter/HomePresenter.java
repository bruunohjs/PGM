package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

/***
    marcellocamara@id.uff.br
            2019
***/

public class HomePresenter implements IHome.Presenter, ITaskListener {

    private IHome.View view;
    private IHome.Model model;

    public HomePresenter(IHome.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnLogout() {
        if (view != null){
            model.DoLogout();
            view.OnLogoutSuccessful();
        }
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void OnError(String message) {

    }
}
