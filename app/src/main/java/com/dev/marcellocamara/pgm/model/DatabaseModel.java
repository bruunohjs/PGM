package com.dev.marcellocamara.pgm.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.dev.marcellocamara.pgm.ui.card_expenses.ICardExpenses;
import com.dev.marcellocamara.pgm.ui.card_overview.ICardOverview;
import com.dev.marcellocamara.pgm.ui.cards.ICards;
import com.dev.marcellocamara.pgm.ui.contact.IContact;
import com.dev.marcellocamara.pgm.ui.new_card.INewCard;
import com.dev.marcellocamara.pgm.ui.new_expense.INewExpense;
import com.dev.marcellocamara.pgm.ui.home.IHome;
import com.dev.marcellocamara.pgm.ui.login.ILogin;
import com.dev.marcellocamara.pgm.ui.main.IMain;
import com.dev.marcellocamara.pgm.ui.expense_overview.IExpenseOverview;
import com.dev.marcellocamara.pgm.ui.points.IPoints;
import com.dev.marcellocamara.pgm.ui.profile.IProfile;
import com.dev.marcellocamara.pgm.ui.recover_password.IRecoverPassword;
import com.dev.marcellocamara.pgm.ui.register.IRegister;
import com.dev.marcellocamara.pgm.ui.ITaskListener;
import com.dev.marcellocamara.pgm.utils.NumberFormat;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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

public class DatabaseModel implements ILogin.Model, IRegister.Model, IRecoverPassword.Model, IMain.Model, IHome.Model, INewExpense.Model, IExpenseOverview.Model, IProfile.Model, INewCard.Model, ICards.Model, ICardOverview.Model, ICardExpenses.Model, IPoints.Model, IContact.Model {

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
    private FirebaseAuth getFirebaseAuthInstance() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    //Singleton FirebaseDatabase
    private FirebaseDatabase getFirebaseDatabaseInstance() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            if (!isPersistenceEnabled) {
                firebaseDatabase.setPersistenceEnabled(true);
                isPersistenceEnabled = true;
            }
        }
        return firebaseDatabase;
    }

    //Singleton DatabaseReference
    private DatabaseReference getDatabaseReference() {
        if (databaseReference == null) {
            databaseReference = getFirebaseDatabaseInstance().getReference();
            databaseReference.keepSynced(true);
        }
        return databaseReference;
    }

    //Singleton StorageReference
    private StorageReference getStorageReference() {
        if (storageReference == null) {
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }

    @Override
    public void DoLogin(final String email, final String password) {
        getFirebaseAuthInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Forces token to be updated in database - overwriting old one
                            UserModel user = new UserModel(GetUserDisplayName(), email);
                            DoInsertUser(user);
                        } else {
                            if (task.getException() != null) {
                                taskListener.OnError(task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void DoLogin(GoogleSignInAccount account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        getFirebaseAuthInstance().signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            UserModel user = new UserModel(firebaseUser.getDisplayName(), firebaseUser.getEmail());
                            DoInsertUser(user);
                        } else {
                            taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
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
                        if (task.isSuccessful()) {
                            DoInsertUser(user);
                        } else {
                            if (task.getException() != null) {
                                taskListener.OnError(task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    private void DoInsertUser(final UserModel user) {
        // Get instance id token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        // Get token
                        String token;
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                        }else {
                            token = Objects.requireNonNull(task.getException()).getMessage();
                        }
                        //Insert user with refreshed token
                        UserModel aux = new UserModel(user.getName(), user.getEmail());
                        aux.setToken(token);
                        getDatabaseReference()
                                .child("Users")
                                .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                                .setValue(aux)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder().setDisplayName(user.getName()).build();
                                            getFirebaseAuthInstance().getCurrentUser().updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        taskListener.OnSuccess();
                                                    } else {
                                                        taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                                                    }
                                                }
                                            });
                                        } else {
                                            if (task.getException() != null) {
                                                taskListener.OnError(task.getException().getMessage());
                                            }
                                        }
                                    }
                                });
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
        taskListener.OnSuccess();
    }

    @Override
    public String GetUserDisplayName() {
        return Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getDisplayName();
    }

    @Override
    public String GetUserEmail() {
        return Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getEmail();
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
                if (task.isSuccessful()) {
                    getDatabaseReference()
                            .child("Users")
                            .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                            .child("name")
                            .setValue(name)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        taskListener.OnSuccess();
                                    } else {
                                        taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                }
                            });
                } else {
                    taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
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
                        if (task.isSuccessful()) {
                            taskListener.OnSuccess();
                        } else {
                            taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });

    }

    @Override
    public void DoUpdateCard(CardModel card) {

        final String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        getDatabaseReference()
                .child("Cards")
                .child(userID)
                .child(card.getUniqueId())
                .setValue(card)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            taskListener.OnSuccess();
                        } else {
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
                if (task.isSuccessful()) {
                    UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder().setPhotoUri(task.getResult()).build();
                    getFirebaseAuthInstance().getCurrentUser().updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                taskListener.OnError("response");
                            } else {
                                taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                            }
                        }
                    });
                } else {
                    taskListener.OnError(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    @Override
    public void DoAddExpense(String date, String title, String description, double price, int installments, String creditCard, String betterDayCard) {

        ExpenseModel expense = new ExpenseModel();
        expense.setPaymentDate(date);
        expense.setTitle(title);
        expense.setDescription(description);
        expense.setPrice(price);
        expense.setInstallments(NumberFormat.getMonth(installments));
        expense.setCreditCard(creditCard);

        String dateSplit[] = date.split("/");
        String day = dateSplit[0];
        String month = dateSplit[1];
        String year = dateSplit[2];

        if ((Integer.parseInt(day)) >= (Integer.parseInt(betterDayCard))) {
            month = NumberFormat.getMonth((Integer.parseInt(month)) + 1);
            if ((Integer.parseInt(month)) > 12) {
                month = NumberFormat.getMonth(1);
                year = String.valueOf((Integer.parseInt(year)) + 1);
            }
            expense.setClosedInvoice(true);
        }

        //This guarantee same ID for all "n" installments
        final String uniqueID = getDatabaseReference().child("Expenses").push().getKey();

        final String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        DoSafeAddExpenseRecursion(1, installments, userID, uniqueID, month, year, expense);

    }

    private void DoSafeAddExpenseRecursion(final Integer n, final Integer countStop, final String userId, final String uniqueId, final String month, final String year, final ExpenseModel expense) {

        final String monthAux;
        final String yearAux;

        if ((Integer.parseInt(month)) > 12) {
            monthAux = NumberFormat.getMonth(1);
            yearAux = String.valueOf((Integer.parseInt(year)) + 1);
        } else {
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

        expenseAux.setCurrentInstallment((NumberFormat.getMonth(n)) + "/" + expense.getInstallments());

        getDatabaseReference()
                .child("Expenses")
                .child(userId)
                .child(monthAux + yearAux)
                .child(uniqueId)
                .setValue(expenseAux);

        if (n.equals(countStop)) {
            taskListener.OnSuccess();
        } else {
            DoSafeAddExpenseRecursion(
                    (n + 1),
                    countStop,
                    userId,
                    uniqueId,
                    (NumberFormat.getMonth((Integer.parseInt(monthAux)) + 1)),
                    yearAux,
                    expense
            );
        }

    }

    @Override
    public void DoDeleteExpense(ExpenseModel expense) {

        String dateSplit[] = expense.getPaymentDate().split("/");
        String month = dateSplit[1];
        String year = dateSplit[2];

        if (expense.getClosedInvoice()) {
            month = NumberFormat.getMonth((Integer.parseInt(month) + 1));
            if ((Integer.parseInt(month)) > 12) {
                month = NumberFormat.getMonth(1);
                year = String.valueOf((Integer.parseInt(year)) + 1);
            }
        }

        String userID = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        DoSafeDeleteExpenseRecursion(1, Integer.parseInt(expense.getInstallments()), userID, expense.getUniqueId(), month, year);

    }

    private void DoSafeDeleteExpenseRecursion(final Integer n, final Integer countStop, final String userId, final String uniqueId, final String month, final String year) {

        final String monthAux;
        final String yearAux;

        if ((Integer.parseInt(month)) > 12) {
            monthAux = NumberFormat.getMonth(1);
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
                .removeValue();

        if (n.equals(countStop)) {
            taskListener.OnSuccess();
        } else {
            DoSafeDeleteExpenseRecursion(
                    (n + 1),
                    countStop,
                    userId,
                    uniqueId,
                    (NumberFormat.getMonth((Integer.parseInt(monthAux)) + 1)),
                    yearAux
            );
        }

    }

    @Override
    public void DoDeleteCard(final String uniqueId) {

        //Searches for all card uniqueId expenses
        getDatabaseReference()
                .child("Expenses")
                .child(Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> dates = new ArrayList<>();
                        List<String> keys = new ArrayList<>();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataAux : data.getChildren()) {
                                ExpenseModel expenseModel = dataAux.getValue(ExpenseModel.class);
                                if (Objects.requireNonNull(expenseModel).getCreditCard().equals(uniqueId)) {
                                    dates.add(data.getKey());
                                    keys.add(dataAux.getKey());
                                }
                            }
                        }
                        DoSafeDeleteCardExpenses(dates, keys, uniqueId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        taskListener.OnError(databaseError.getMessage());
                    }
                });

    }

    private void DoSafeDeleteCardExpenses(List<String> dates, List<String> keys, String cardUniqueId) {

        if (dates.size() != keys.size()) {

            taskListener.OnError("Wrong tuple of data. Try again or contact support.");

        } else {
            if (keys.size() > 0) {

                final String userId = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

                for (int i = 0; i < dates.size(); i++) {
                    getDatabaseReference()
                            .child("Expenses")
                            .child(userId)
                            .child(dates.get(i))
                            .child(keys.get(i))
                            .removeValue();
                }
            }
            DoSafeDeleteCard(cardUniqueId);
        }

    }

    private void DoSafeDeleteCard(String uniqueId) {

        final String userId = Objects.requireNonNull(getFirebaseAuthInstance().getCurrentUser()).getUid();

        getDatabaseReference()
                .child("Cards")
                .child(userId)
                .child(uniqueId)
                .removeValue();

        taskListener.OnError("response");

    }

    @Override
    public void DoRecoverPassword(String email) {
        getFirebaseAuthInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    taskListener.OnSuccess();
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

}