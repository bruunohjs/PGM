package com.dev.marcellocamara.pgm.Model;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.dev.marcellocamara.pgm.Contract.IExpense;
import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Contract.IMain;
import com.dev.marcellocamara.pgm.Contract.IOverview;
import com.dev.marcellocamara.pgm.Contract.IProfile;
import com.dev.marcellocamara.pgm.Contract.IRecoverPassword;
import com.dev.marcellocamara.pgm.Contract.IRegister;
import com.dev.marcellocamara.pgm.Contract.ITaskListener;
import com.dev.marcellocamara.pgm.Helper.NumberHelper;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
    marcellocamara@id.uff.br
            2019
***/

public class DatabaseModel implements ILogin.Model, IRegister.Model, IRecoverPassword.Model, IMain.Model, IHome.Model, IExpense.Model, IOverview.Model, IProfile.Model {

    private ITaskListener taskListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ValueEventListener valueEventListener;
    private List<ExpenseModel> list = new ArrayList<>();

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

    //Singleton StorageReference
    private StorageReference getStorageReference(){
        if (storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
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
                                UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder().setDisplayName(user.getName()).build();
                                getFirebaseAuthInstance().getCurrentUser().updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            taskListener.OnSuccess();
                                        }else {
                                            taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                                        }
                                    }
                                });
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
    public void DoRecoverPassword(String email) {
        getFirebaseAuthInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    taskListener.OnSuccess();
                }else {
                    taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    @Override
    public void DoLogout() {
        getFirebaseAuthInstance().signOut();
        taskListener.OnSuccess();
    }

    @Override
    public String GetUserDisplayName() {
        return Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getDisplayName();
    }

    @Override
    public Uri GetUserPhotoUri() {
        return Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getPhotoUrl();
    }

    @Override
    public void DoUpdateUserName(final String name) {
        UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    getDatabaseReference()
                            .child("Users")
                            .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                            .child("name")
                            .setValue(name)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                taskListener.OnSuccess();
                            }else {
                                taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                            }
                        }
                    });
                }else {
                    taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    @Override
    public void DoUpdateUserImage(Uri uri, String format) {
        final StorageReference reference = getStorageReference()
                .child("profile_images")
                .child((Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid()) + format);
        UploadTask uploadTask = reference.putFile(uri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                // Continue with the task to get the download URL
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder().setPhotoUri(task.getResult()).build();
                    getFirebaseAuthInstance().getCurrentUser().updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                taskListener.OnError("response");
                            }else {
                                taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                            }
                        }
                    });
                }else {
                    taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    @Override
    public String GetUserEmail() {
        return Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getEmail();
    }

    @Override
    public List<ExpenseModel> DoRecoverExpenses(String monthYear) {

        valueEventListener = getDatabaseReference()
                .child("Expenses")
                .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                .child(monthYear)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ExpenseModel expenseModel = data.getValue(ExpenseModel.class);
                    Objects.requireNonNull(expenseModel).setUniqueId(data.getKey()); //Retrieves UniqueKey from each item of month
                    list.add(expenseModel);
                }
                taskListener.OnSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                taskListener.OnError(databaseError.getMessage());
            }
        });

        return list;
    }

    @Override
    public void RemoveEventListener() {
        getDatabaseReference().removeEventListener(this.valueEventListener);
    }

    @Override
    public void DoAddExpense(String date, String title, String description, double price, int installments) {

        //TODO: Missing OnCompleteListener

        ExpenseModel expense = new ExpenseModel();
        expense.setTitle(title);
        expense.setPrice(price);
        expense.setDescription(description);
        expense.setPaymentDate(date);
        expense.setInstallments(NumberHelper.GetMonth(installments));

        String dateSplit[] = date.split("/");
        String month = dateSplit[1];
        String year = dateSplit[2];

        //This guarantee same ID for all "n" installments
        final String UniqueID = getDatabaseReference().child("Expenses").push().getKey();

        for (int i = 1 ; i <= installments ; i++){
            if ( (Integer.parseInt(month)) > 12 ){
                month = NumberHelper.GetMonth(1);
                year = String.valueOf( (Integer.parseInt(year)) + 1 );
            }

            expense.setCurrentInstallment( (NumberHelper.GetMonth(i)) + "/" + expense.getInstallments());

            getDatabaseReference()
                    .child("Expenses")
                    .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                    .child(month+year)
                    .child(Objects.requireNonNull(UniqueID))
                    .setValue(expense);

            month = NumberHelper.GetMonth( (Integer.parseInt(month)) + 1) ;

        }

        taskListener.OnSuccess(); //TODO : OnError - no internet

    }

    @Override
    public void DoDeleteExpense(String date, int installments, String uniqueId) {

        //TODO: Missing OnCompleteListener TOO !

        String dateSplit[] = date.split("/");
        String month = dateSplit[1];
        String year = dateSplit[2];

        for (int i = 1 ; i <= installments ; i++){
            if ( (Integer.parseInt(month)) > 12 ){
                month = NumberHelper.GetMonth(1);
                year = String.valueOf( (Integer.parseInt(year)) + 1 );
            }

            getDatabaseReference()
                    .child("Expenses")
                    .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                    .child(month+year)
                    .child(uniqueId).removeValue();

            month = NumberHelper.GetMonth( (Integer.parseInt(month)) + 1) ;

        }

        taskListener.OnSuccess(); //TODO : OnFailure - no internet

    }
}