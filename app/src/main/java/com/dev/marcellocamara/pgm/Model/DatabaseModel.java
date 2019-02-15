package com.dev.marcellocamara.pgm.Model;

import android.support.annotation.NonNull;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/***
    marcellocamara@id.uff.br
            2019
***/

public class DatabaseModel implements ILogin.Model, IRegister.Model, IHome.Model, IExpense.Model {

    private ITaskListener taskListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
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

    //Singleton FirebaseAuth
    private FirebaseDatabase getFirebaseDatabaseInstance(){
        if (firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase;
    }

    //Singleton DatabaseReference
    private DatabaseReference getDatabaseReference(){
        if (databaseReference == null){
            databaseReference = getFirebaseDatabaseInstance().getReference();
        }
        return databaseReference;
    }

    @Override
    public void DoLogin(String email, String password) {
        getFirebaseAuthInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
        final UserModel user = new UserModel(name, email);
        getFirebaseAuthInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    getDatabaseReference()
                            .child("Users")
                            .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                            .setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                taskListener.OnSuccess();
                            }else{
                                if (task.getException() != null){
                                    taskListener.OnError(task.getException().getMessage());
                                }
                            }
                        }
                    });
                }else {
                    if (task.getException() != null){
                        taskListener.OnError(task.getException().getMessage());
                    }
                }
            }
        });
    }

    @Override
    public boolean CheckLoggedIn() {
        return getFirebaseAuthInstance().getCurrentUser() != null;
    }

    @Override
    public void DoLogout() {
        getFirebaseAuthInstance().signOut();
    }

    @Override
    public void DoAddExpense(String date, String title, String description, double price, int installments) {

        ExpenseModel expense = new ExpenseModel(date, title, description, price, String.valueOf(installments));
        expense.setInstallments(ValidateNumber(expense.getInstallments()));

        String DateSplited[] = date.split("/");
        String month = DateSplited[1];
        String year = DateSplited[2];

        for (int i = 1 ; i <= installments ; i++){
            if (Integer.parseInt(month)>12){
                month = ValidateNumber(String.valueOf(1));
                year = String.valueOf((Integer.parseInt(year))+1);
            }

            expense.setCurrentInstallment(ValidateNumber(String.valueOf(i)) + "/" + expense.getInstallments());

            getDatabaseReference()
                    .child("Expenses")
                    .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                    .child((month)+year)
                    .push()
                    .setValue(expense);

            month = ValidateNumber(String.valueOf(((Integer.parseInt(month))+1)));
        }

        taskListener.OnSuccess();
    }

    private String ValidateNumber(String number){
        if (number.length() < 2){
            number = "0" + number;
        }
        return number;
    }
}