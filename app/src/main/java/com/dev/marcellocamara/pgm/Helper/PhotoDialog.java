package com.dev.marcellocamara.pgm.Helper;

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

import com.dev.marcellocamara.pgm.Contract.IPhoto;
import com.dev.marcellocamara.pgm.R;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

/***
    marcellocamara@id.uff.br
            2019
***/

public class PhotoDialog extends DialogFragment implements View.OnClickListener {

    private IPhoto photoListener;
    private ImageView imageViewCamera, imageViewGallery;
    private Button buttonCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_photo, container, false);

        ViewBind(itemView);

        return itemView;
    }

    private void ViewBind(View itemView) {

        imageViewCamera = itemView.findViewById(R.id.imageViewCamera);
        imageViewGallery = itemView.findViewById(R.id.imageViewGallery);
        imageViewCamera.setOnClickListener(this);
        imageViewGallery.setOnClickListener(this);

        buttonCancel = itemView.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCancel : {
                getDialog().dismiss();
                break;
            }
            case R.id.imageViewCamera : {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Permissions.CAMERA_REQUEST);
                break;
            }
            case R.id.imageViewGallery : {
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), Permissions.GALLERY_REQUEST);
                break;
            }
        }
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
            photoListener = (IPhoto) getTargetFragment();
        }catch (Exception e){
            Log.d("IPhoto Exception", "onAttach: " + e.getMessage());
        }
    }
}
