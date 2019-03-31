package com.dev.marcellocamara.pgm.ui.profile;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.dev.marcellocamara.pgm.ui.dialogs.IDialog;
import com.dev.marcellocamara.pgm.ui.dialogs.PhotoDialog;
import com.dev.marcellocamara.pgm.R;

import com.bumptech.glide.Glide;

import com.mikhaellopez.circularimageview.CircularImageView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import dmax.dialog.SpotsDialog;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.BindString;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ProfileFragment extends Fragment implements IProfile.View, IDialog.Photo {

    @BindView(R.id.circularImageViewProfile) protected CircularImageView circularImageViewProfile;

    @BindView(R.id.layoutName) protected TextInputLayout layoutName;

    @BindView(R.id.editTextName) protected TextInputEditText editTextName;
    @BindView(R.id.editTextEmail) protected TextInputEditText editTextEmail;

    @BindString(R.string.profile) protected String profile;
    @BindString(R.string.dot) protected String dot;
    @BindString(R.string.empty_name) protected String empty_name;
    @BindString(R.string.no_changes) protected String no_changes;
    @BindString(R.string.close) protected String close;
    @BindString(R.string.profile_updating) protected String updating_profile;
    @BindString(R.string.profile_update_success) protected String update_success;

    private IProfile.Presenter profilePresenter;
    private CircularImageView navHeaderImageView;
    private TextView navHeaderUserName;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Unbinder unbinder;

    public ProfileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        unbinder = ButterKnife.bind(this, view);

        profilePresenter = new ProfilePresenter(this);

        NavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        navHeaderUserName = headerView.findViewById(R.id.textViewUserName);
        navHeaderImageView = headerView.findViewById(R.id.imageViewUserProfile);

        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(profile);
        builder.setCancelable(false);

        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.CustomAlertDialog)
                .setMessage(updating_profile)
                .setCancelable(false)
                .build();

        return view;
    }

    @OnClick(R.id.btnSave)
    public void OnButtonClick(){
        layoutName.clearFocus();
        layoutName.setErrorEnabled(false);
        profilePresenter.OnUpdateUserName(
                editTextName.getText().toString().trim(),
                navHeaderUserName.getText().toString().trim()
        );
        UIUtil.hideKeyboard(Objects.requireNonNull(getActivity()));
    }

    @OnClick(R.id.btnPhoto)
    public void OnButtonPhotoClick(){
        layoutName.clearFocus();
        profilePresenter.OnCheckPermissions(getActivity());
        UIUtil.hideKeyboard(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void OnRequestUserDataSuccessful(String name, String email) {
        editTextEmail.setText(email);
        editTextName.setText(name);
    }

    @Override
    public void OnUpdateUserSuccessful() {
        builder.setMessage(update_success);
        builder.setPositiveButton(close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navHeaderUserName.setText(Objects.requireNonNull(editTextName.getText()).toString().trim());
            }
        });
        builder.show();
        layoutName.clearFocus();
    }

    @Override
    public void OnUpdateUserFailure(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnNoChangesUpdate() {
        builder.setMessage(no_changes);
        builder.setPositiveButton(close, null);
        builder.show();
    }

    @Override
    public void OnCheckPermissionsSuccessful() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.show(Objects.requireNonNull(getFragmentManager()), "ChangePhoto");
        dialog.setTargetFragment(ProfileFragment.this, 0);
    }

    @Override
    public void OnBlankField() {
        layoutName.setError(empty_name);
        layoutName.setErrorEnabled(true);
    }

    @Override
    public void getUri(Uri uri) {
        ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
        String format = dot + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));
        profilePresenter.OnCheckUri(uri, format);
    }

    @Override
    public void OnSetUserImage(Uri uri) {
        Glide.with(this).load(uri).into(circularImageViewProfile);
        Glide.with(Objects.requireNonNull(getActivity())).load(uri).into(navHeaderImageView);
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
        unbinder.unbind();
    }
}