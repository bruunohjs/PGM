package com.dev.marcellocamara.pgm.Model;

import android.support.annotation.NonNull;

import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/***
    marcellocamara@id.uff.br
            2018
***/

public class DatabaseModel implements ILogin.Model, IRegister.Model {

    private ITaskListener taskListener;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public DatabaseModel(ITaskListener taskListener) {
        this.taskListener = taskListener;
    }

    //Singleton FirebaseAuth
    private FirebaseAuth getFirebaseAuthInstance(){
        if (firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    //Singleton DatabaseReference
    private DatabaseReference getDatabaseReference(){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    @Override
    public void DoLogin(String email, String password) {
        getFirebaseAuthInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    @Override
    public void DoRegister(String name, String email, String password) {
        getFirebaseAuthInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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