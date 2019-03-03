package com.dev.marcellocamara.pgm.View.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private CircularImageView imageViewProfile, navHeaderImageView;
    private TextInputLayout layoutName;
    private TextInputEditText editTextEmail, editTextName;
    private TextView navHeaderUserName;
    private Button buttonPhoto, buttonSave;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public ProfileFragment() { }

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

        imageViewProfile = view.findViewById(R.id.circularImageView);

        layoutName = view.findViewById(R.id.layoutName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextEmail.setEnabled(false);
        editTextName = view.findViewById(R.id.editTextName);

        buttonPhoto = view.findViewById(R.id.buttonPhoto);
        buttonPhoto.setOnClickListener(this);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        NavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navHeaderUserName = headerView.findViewById(R.id.textViewUserName);
        navHeaderImageView = headerView.findViewById(R.id.imageViewUserProfile);

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
                profilePresenter.OnUpdateUserName(
                        editTextName.getText().toString().trim(),
                        navHeaderUserName.getText().toString().trim()
                );
                UIUtil.hideKeyboard(Objects.requireNonNull(getActivity()));
                break;
            }
        }
    }

    @Override
    public void OnRequestUserDataSuccessful(String name, String email) {
        editTextEmail.setText(email);
        editTextName.setText(name);
    }

    @Override
    public void OnUpdateUserSuccessful() {
        builder.setMessage(R.string.view_profile_successful);
        builder.setPositiveButton(R.string.view_overview_dialog_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navHeaderUserName.setText(editTextName.getText().toString().trim());
            }
        });
        builder.show();
        layoutName.clearFocus();
    }

    @Override
    public void OnUpdateUserFailure(String message) {
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
    public void getUri(Uri uri) {
        profilePresenter.OnCheckFilePath(uri);
    }

    @Override
    public void OnSetUserImage(Bitmap bitmap) {
        Glide.with(this).load(bitmap).into(imageViewProfile);
        Glide.with(Objects.requireNonNull(getActivity())).load(bitmap).into(navHeaderImageView);
    }

    @Override
    public void OnSetUserImage(Uri uri) {
        Glide.with(this).load(uri).into(imageViewProfile);
        Glide.with(Objects.requireNonNull(getActivity())).load(uri).into(navHeaderImageView);
    }

    @Override
    public void OnSetUserImageFailure() {
        builder.setMessage(R.string.view_profile_name_failure);
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