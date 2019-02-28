package com.dev.marcellocamara.pgm.View.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.marcellocamara.pgm.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/***
    marcellocamara@id.uff.br
            2019
***/

public class ProfileFragment extends Fragment {

    private CircularImageView imageView;
    private TextInputLayout layoutName;
    private TextInputEditText editTextEmail, editTextName;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ViewBind(view);

        return view;
    }

    private void ViewBind(View view) {

        imageView = view.findViewById(R.id.circularImageView);

        layoutName = view.findViewById(R.id.layoutName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextEmail.setEnabled(false);
        editTextName = view.findViewById(R.id.editTextName);

    }

}