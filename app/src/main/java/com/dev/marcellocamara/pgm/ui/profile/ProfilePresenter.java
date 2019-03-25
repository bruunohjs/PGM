package com.dev.marcellocamara.pgm.ui.profile;

import android.app.Activity;
import android.net.Uri;

import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.Permissions;
import com.dev.marcellocamara.pgm.model.DatabaseModel;

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
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName(), model.GetUserEmail());
        if (model.GetUserPhotoUri() != null){
            view.OnSetUserImage(model.GetUserPhotoUri());
        }
    }

    @Override
    public void OnUpdateUserName(String newName, String nameSaved) {
        if (newName.isEmpty()){
            view.OnBlankField();
        } else if (newName.equals(nameSaved)){
            view.OnNoChangesUpdate();
        } else {
            view.ShowProgress();
            model.DoUpdateUserName(newName);
        }
    }

    @Override
    public void OnCheckPermissions(Activity activity) {
        if (Permissions.verify(activity, Permissions.PHOTO_PERMISSIONS, 1)) {
            view.OnCheckPermissionsSuccessful();
        }
    }
    @Override
    public void OnCheckUri(Uri uri, String format) {
        view.ShowProgress();
        model.DoUpdateUserImage(uri, format);
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnUpdateUserSuccessful();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            if (message.equals("response")){
                view.HideProgress();
                if (model.GetUserPhotoUri() != null){
                    view.OnSetUserImage(model.GetUserPhotoUri());
                }
                view.OnUpdateUserSuccessful();
            }else {
                view.HideProgress();
                view.OnUpdateUserFailure(message);
            }
        }
    }
}