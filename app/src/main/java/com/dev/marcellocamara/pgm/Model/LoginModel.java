package com.dev.marcellocamara.pgm.Model;

import android.support.annotation.NonNull;

import com.dev.marcellocamara.pgm.Interfaces.ILogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/***
    marcellocamara@id.uff.br
            2018
***/

public class LoginModel implements ILogin.Model {

    private ILogin.TaskListener taskListener;
    private FirebaseAuth auth;

    public LoginModel(ILogin.TaskListener taskListener) {
        this.taskListener = taskListener;
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void DoLogin(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    taskListener.OnSuccess();
                }else {
                    if (task.getException() != null){
                        taskListener.OnError(task.getException().getMessage());
                    }
                }
            }
        });

    }

}
