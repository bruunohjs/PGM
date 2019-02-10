package com.dev.marcellocamara.pgm.Presenter;

import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Model.RegisterModel;

/***
    marcellocamara@id.uff.br
            2018
***/

public class RegisterPresenter implements IRegister.Presenter {

    private IRegister.View view;
    private IRegister.Model model;

    public RegisterPresenter(IRegister.View view) {
        this.view = view;
        this.model = new RegisterModel();
    }

    @Override
    public void OnRegister(String name, String email, String password) {
        //TODO: OnRegister
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

}