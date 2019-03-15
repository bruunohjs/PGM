package com.dev.marcellocamara.pgm.View.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dev.marcellocamara.pgm.Contract.IDialog;
import com.dev.marcellocamara.pgm.Helper.Permissions;
import com.dev.marcellocamara.pgm.R;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/***
    marcellocamara@id.uff.br
            2019
***/

public class PhotoDialog extends DialogFragment {

    @BindView(R.id.imageViewCamera) protected ImageView imageViewCamera;
    @BindView(R.id.imageViewGallery) protected ImageView imageViewGallery;

    @BindView(R.id.btnCancel) protected Button btnCancel;

    private IDialog.Photo photoListener;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_photo, container, false);

        unbinder = ButterKnife.bind(this, itemView);

        return itemView;
    }

    @OnClick(R.id.imageViewCamera)
    public void OnCameraClick(){
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Permissions.CAMERA_REQUEST);
    }

    @OnClick(R.id.imageViewGallery)
    public void OnGalleryClick(){
        startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), Permissions.GALLERY_REQUEST);
    }

    @OnClick(R.id.btnCancel)
    public void OnButtonClick(){
        getDialog().dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case Permissions.CAMERA_REQUEST : {
                    Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    photoListener.getUri(getImageUri(Objects.requireNonNull(bitmap)));
                    getDialog().dismiss();
                    break;
                }
                case Permissions.GALLERY_REQUEST : {
                    Uri selectedImage = data.getData();
                    photoListener.getUri(selectedImage);
                    getDialog().dismiss();
                    break;
                }
            }
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                Objects.requireNonNull(getContext()).getContentResolver(),
                bitmap,
                "Title",
                null
        );
        return Uri.parse(path);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            photoListener = (IDialog.Photo) getTargetFragment();
        }catch (Exception e){
            Log.d("IDialog.Photo Exception", "onAttach: " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}