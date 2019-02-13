package com.dev.marcellocamara.pgm.View;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aniket.mutativefloatingactionbutton.MutativeFab;
import com.dev.marcellocamara.pgm.R;

/***
    marcellocamara@id.uff.br
            2019
***/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MutativeFab mutativeFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mutativeFab = findViewById(R.id.floatingAB);
        mutativeFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingAB : {
                Snackbar.make(v,"Funcionou", Snackbar.LENGTH_LONG).show();
                break;
            }
        }
    }

}
