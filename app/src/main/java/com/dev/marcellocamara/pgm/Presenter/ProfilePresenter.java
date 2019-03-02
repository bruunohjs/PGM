package com.dev.marcellocamara.pgm.Presenter;

import android.app.Activity;
import android.graphics.Bitmap;

import com.dev.marcellocamara.pgm.Contract.IProfile;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.Permissions;
import com.dev.marcellocamara.pgm.Model.DatabaseModel;

import java.io.ByteArrayOutputStream;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ProfilePresenter implements IProfile.Presenter, ITaskListener {

    private IProfile.View view;
    private IProfile.Model model;
    private final int BITMAP_QUALITY = 70;

    public ProfilePresenter(IProfile.View view) {
        this.view = view;
        this.model = new DatabaseModel(this);
    }

    @Override
    public void OnRequestUserData() {
        view.OnRequestUserDataSuccessful(model.GetUserDisplayName(), model.GetUserEmail());
    }

    @Override
    public void OnUpdateUserName(String newName, String nameSaved) {
        if (newName.isEmpty()){
            view.OnBlankField();
        } else if (newName.equals(nameSaved)){
            view.OnUpdateUserNameFailure("No changes.");
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
    public void OnCheckBitmap(Bitmap bitmap) {
        if (bitmap != null){
            view.OnSetUserImage(OnCompressBitmap(bitmap, BITMAP_QUALITY));
        }else {
            view.OnSetUserImageFailure();
        }
    }

    @Override
    public void OnCheckFilePath(String filePath) {
        if ( !(filePath.isEmpty()) ){
            filePath = filePath.replace(":/", "://");
            view.OnSetUserImage(filePath);
        }else {
            view.OnSetUserImageFailure();
        }
    }

    private Bitmap OnCompressBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return bitmap;
    }

    @Override
    public void OnDestroy() {
        this.view = null;
    }

    @Override
    public void OnSuccess() {
        if (view != null){
            view.HideProgress();
            view.OnUpdateUserNameSuccessful();
        }
    }

    @Override
    public void OnError(String message) {
        if (view != null){
            view.HideProgress();
            view.OnUpdateUserNameFailure(message);
        }
    }
}
