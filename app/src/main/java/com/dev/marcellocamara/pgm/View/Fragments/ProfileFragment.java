package com.dev.marcellocamara.pgm.View.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.dev.marcellocamara.pgm.Contract.IPhoto;
import com.dev.marcellocamara.pgm.Contract.IProfile;
import com.dev.marcellocamara.pgm.Helper.PhotoDialog;
import com.dev.marcellocamara.pgm.Presenter.ProfilePresenter;
import com.dev.marcellocamara.pgm.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ProfileFragment extends Fragment implements IProfile.View, View.OnClickListener, IPhoto {

    private IProfile.Presenter profilePresenter;
    private CircularImageView imageView;
    private TextInputLayout layoutName;
    private TextInputEditText editTextEmail, editTextName;
    private Button buttonPhoto, buttonSave;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String name;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ViewBind(view);

        profilePresenter = new ProfilePresenter(this);

        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.view_profile_alert_dialog_title);
        builder.setCancelable(false);

        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(R.string.view_profile_loading_title)
                .setCancelable(false)
                .build();

        return view;
    }

    private void ViewBind(View view) {

        imageView = view.findViewById(R.id.circularImageView);

        layoutName = view.findViewById(R.id.layoutName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextEmail.setEnabled(false);
        editTextName = view.findViewById(R.id.editTextName);

        buttonPhoto = view.findViewById(R.id.buttonPhoto);
        buttonPhoto.setOnClickListener(this);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPhoto : {
                profilePresenter.OnCheckPermissions(getActivity());
                break;
            }
            case R.id.buttonSave: {
                layoutName.setErrorEnabled(false);
                profilePresenter.OnUpdateUserName(editTextName.getText().toString().trim(), name);
                UIUtil.hideKeyboard(Objects.requireNonNull(getActivity()));
                break;
            }
        }
    }

    @Override
    public void OnRequestUserDataSuccessful(String name, String email) {
        editTextEmail.setText(email);
        editTextName.setText(name);
        this.name = name;
    }

    @Override
    public void OnUpdateUserNameSuccessful() {
        builder.setMessage("Name updated successful !");
        builder.setPositiveButton(R.string.view_overview_dialog_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO : Update navigation drawer textviewUserName
            }
        });
        builder.show();
        name = editTextName.getText().toString().trim();
        layoutName.clearFocus();
    }

    @Override
    public void OnUpdateUserNameFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(R.string.view_overview_dialog_close, null);
        builder.show();
    }

    @Override
    public void OnCheckPermissionsSuccessful() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.show(Objects.requireNonNull(getFragmentManager()),"ChangePhoto");
        dialog.setTargetFragment(ProfileFragment.this, 0);
    }

    @Override
    public void OnBlankField() {
        layoutName.setError(getResources().getString(R.string.presenter_register_name));
        layoutName.setErrorEnabled(true);
    }

    @Override
    public void getBitmap(Bitmap bitmap) {
        profilePresenter.OnCheckBitmap(bitmap);
    }

    @Override
    public void getFilePath(String filePath) {
        profilePresenter.OnCheckFilePath(filePath);
    }

    @Override
    public void OnSetUserImage(Bitmap bitmap) {
        //TODO : Put into Firebase and Navigation Drawer
        Glide.with(this).load(bitmap).into(imageView);
    }

    @Override
    public void OnSetUserImage(String filePath) {
        //TODO : Put into Firebase and Navigation Drawer
        Glide.with(this).load(filePath).into(imageView);
    }

    @Override
    public void OnSetUserImageFailure() {
        builder.setMessage("Oops! Something went wrong. Try again !");
        builder.setPositiveButton(R.string.view_overview_dialog_close, null);
        builder.show();
    }

    @Override
    public void ShowProgress() {
        alertDialog.show();
    }

    @Override
    public void HideProgress() {
        alertDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        profilePresenter.OnRequestUserData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profilePresenter.OnDestroy();
    }
}