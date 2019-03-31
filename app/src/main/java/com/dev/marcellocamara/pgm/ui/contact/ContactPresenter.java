package com.dev.marcellocamara.pgm.ui.contact;

import com.dev.marcellocamara.pgm.model.DatabaseModel;
import com.dev.marcellocamara.pgm.ui.ITaskListener;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ContactPresenter implements IContact.Presenter, ITaskListener {

    private IContact.View view;
    private IContact.Model model;

    public ContactPresenter(IContact.View view){
        this.view = view;
        model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName(),model.GetUserEmail());
    }

    @Override
    public void OnSendMessage(String name, String email, String defaultSubject, String subject, String message) {
        if (defaultSubject.equals(subject)){
            view.OnInvalidSubject();
        }else if (message.isEmpty()){
            view.OnEmptyMessage();
        }else {
            view.DoSendMessage();
        }
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() { }

    @Override
    public void OnError(String message) { }

}