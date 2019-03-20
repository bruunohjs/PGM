package com.dev.marcellocamara.pgm.Model;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.dev.marcellocamara.pgm.Contract.ICardOverview;
import com.dev.marcellocamara.pgm.Contract.ICards;
import com.dev.marcellocamara.pgm.Contract.INewCard;
import com.dev.marcellocamara.pgm.Contract.INewExpense;
import com.dev.marcellocamara.pgm.Contract.IHome;
import com.dev.marcellocamara.pgm.Contract.ILogin;
import com.dev.marcellocamara.pgm.Contract.IMain;
import com.dev.marcellocamara.pgm.Contract.IExpenseOverview;
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

public class DatabaseModel implements ILogin.Model, IRegister.Model, IRecoverPassword.Model, IMain.Model, IHome.Model, INewExpense.Model, IExpenseOverview.Model, IProfile.Model, INewCard.Model, ICards.Model, ICardOverview.Model {

    private static boolean isPersistenceEnabled = false;
    private ITaskListener taskListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ValueEventListener expensesValueEventListener, cardsValueEventListener;
    private List<ExpenseModel> expensesList = new ArrayList<>();
    private ArrayList<CardModel> cardsList = new ArrayList<>();

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

    //Singleton FirebaseDatabase
    private FirebaseDatabase getFirebaseDatabaseInstance(){
        if (firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            if (!isPersistenceEnabled){
                firebaseDatabase.setPersistenceEnabled(true);
                isPersistenceEnabled = true;
            }
        }
        return firebaseDatabase;
    }

    //Singleton DatabaseReference
    private DatabaseReference getDatabaseReference(){
        if (databaseReference == null){
            databaseReference = getFirebaseDatabaseInstance().getReference();
            databaseReference.keepSynced(true);
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
    public void DoAddCard(String title, String numbers, String date, int cardColor, int cardFlag) {

        CardModel card = new CardModel(title, numbers, date, cardColor, cardFlag);

        final String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        getDatabaseReference()
                .child("Cards")
                .child(userID)
                .push()
                .setValue(card)
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
    }

    @Override
    public void DoUpdateCard(String uniqueId, String title, String numbers, String date, int cardColor, int cardFlag) {

        CardModel card = new CardModel(title, numbers, date, cardColor, cardFlag);

        final String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        getDatabaseReference()
                .child("Cards")
                .child(userID)
                .child(uniqueId)
                .setValue(card)
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

        expensesValueEventListener = getDatabaseReference()
                .child("Expenses")
                .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                .child(monthYear)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expensesList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ExpenseModel expenseModel = data.getValue(ExpenseModel.class);
                    //Retrieves UniqueKey from each item of month
                    Objects.requireNonNull(expenseModel).setUniqueId(data.getKey());
                    expensesList.add(expenseModel);
                }
                taskListener.OnSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                taskListener.OnError(databaseError.getMessage());
            }
        });

        return expensesList;
    }

    @Override
    public void RemoveExpensesEventListener() {
        getDatabaseReference().removeEventListener(this.expensesValueEventListener);
    }

    @Override
    public void RemoveCardsEventListener() {
        getDatabaseReference().removeEventListener(this.cardsValueEventListener);
    }

    @Override
    public void DoAddExpense(String date, String title, String description, double price, int installments, String creditCard, String betterDayCard) {

        ExpenseModel expense = new ExpenseModel();
        expense.setPaymentDate(date);
        expense.setTitle(title);
        expense.setDescription(description);
        expense.setPrice(price);
        expense.setInstallments(NumberHelper.GetMonth(installments));
        expense.setCreditCard(creditCard);

        String dateSplit[] = date.split("/");
        String day = dateSplit[0];
        String month = dateSplit[1];
        String year = dateSplit[2];

        //TODO : Change "betterDayCard" for "invoiceClosingDay" and subtract the running days to pay of current card
        if ( (Integer.parseInt(day)) >= (Integer.parseInt(betterDayCard)) ){
            month = NumberHelper.GetMonth((Integer.parseInt(month)) + 1);
            if ( (Integer.parseInt(month)) > 12 ){
                month = NumberHelper.GetMonth(1);
                year = String.valueOf( (Integer.parseInt(year)) + 1 );
            }
            expense.setClosedInvoice(true);
        }

        //This guarantee same ID for all "n" installments
        final String UniqueID = getDatabaseReference().child("Expenses").push().getKey();

        final String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        DoAddExpenseRecursion(1, installments, userID, UniqueID, month, year, expense);

    }

    private void DoAddExpenseRecursion(final Integer n, final Integer countStop, final String userId, final String uniqueId, final String month, final String year, final ExpenseModel expense){

        final String monthAux;
        final String yearAux;

        if ( (Integer.parseInt(month)) > 12 ){
            monthAux = NumberHelper.GetMonth(1);
            yearAux = String.valueOf( (Integer.parseInt(year)) + 1 );
        }else {
            monthAux = month;
            yearAux = year;
        }

        ExpenseModel expenseAux = new ExpenseModel();
        expenseAux.setPaymentDate(expense.getPaymentDate());
        expenseAux.setTitle(expense.getTitle());
        expenseAux.setDescription(expense.getDescription());
        expenseAux.setPrice(expense.getPrice());
        expenseAux.setInstallments(expense.getInstallments());
        expenseAux.setCreditCard(expense.getCreditCard());
        expenseAux.setClosedInvoice(expense.getClosedInvoice());

        expenseAux.setCurrentInstallment( (NumberHelper.GetMonth(n)) + "/" + expense.getInstallments());

        getDatabaseReference()
                .child("Expenses")
                .child(userId)
                .child(monthAux + yearAux)
                .child(uniqueId)
                .setValue(expenseAux)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            if (n.equals(countStop)){
                                taskListener.OnSuccess();
                            }else {
                                DoAddExpenseRecursion(
                                        (n+1),
                                        countStop,
                                        userId,
                                        uniqueId,
                                        (NumberHelper.GetMonth( (Integer.parseInt(monthAux)) + 1)),
                                        yearAux,
                                        expense
                                );
                            }
                        }else {
                            taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });
    }

    @Override
    public void DoDeleteExpense(ExpenseModel expense) {

        String dateSplit[] = expense.getPaymentDate().split("/");
        String month = dateSplit[1];
        String year = dateSplit[2];

        if (expense.getClosedInvoice()){
            month = NumberHelper.GetMonth( (Integer.parseInt(month) + 1) );
            if ( (Integer.parseInt(month)) > 12 ) {
                month = NumberHelper.GetMonth(1);
                year = String.valueOf( (Integer.parseInt(year)) + 1 );
            }
        }

        String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        DoDeleteExpenseRecursion(1, Integer.parseInt(expense.getInstallments()), userID, expense.getUniqueId(), month, year);

    }

    private void DoDeleteExpenseRecursion(final Integer n, final Integer countStop, final String userId, final String uniqueId, final String month, final String year) {

        final String monthAux;
        final String yearAux;

        if ((Integer.parseInt(month)) > 12) {
            monthAux = NumberHelper.GetMonth(1);
            yearAux = String.valueOf((Integer.parseInt(year)) + 1);
        } else {
            monthAux = month;
            yearAux = year;
        }

        getDatabaseReference()
                .child("Expenses")
                .child(userId)
                .child(monthAux + yearAux)
                .child(uniqueId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (n.equals(countStop)) {
                                taskListener.OnSuccess();
                            } else {
                                DoDeleteExpenseRecursion(
                                        (n + 1),
                                        countStop,
                                        userId,
                                        uniqueId,
                                        (NumberHelper.GetMonth((Integer.parseInt(monthAux)) + 1)),
                                        yearAux
                                );
                            }
                        } else {
                            taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });
    }

    @Override
    public ArrayList<CardModel> DoRecoverCards() {

        cardsValueEventListener = getDatabaseReference()
                .child("Cards")
                .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cardsList.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            CardModel card = data.getValue(CardModel.class);
                            //Retrieves UniqueKey from each card
                            Objects.requireNonNull(card).setUniqueId(data.getKey());
                            cardsList.add(card);
                        }
                        taskListener.OnSuccess();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        taskListener.OnError(databaseError.getMessage());
                    }
                });

        return cardsList;
    }

}