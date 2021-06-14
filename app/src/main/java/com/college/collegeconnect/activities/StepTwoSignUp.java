package com.college.collegeconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.college.collegeconnect.R;
import com.college.collegeconnect.datamodels.SaveSharedPreference;
import com.college.collegeconnect.datamodels.User;
import com.college.collegeconnect.utils.FirebaseUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class StepTwoSignUp extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_PREV = "previous";
    private static String TAG = "Step Two Sign Up";
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase = FirebaseUtil.getDatabase();
    private TextInputLayout rollno, yearText;
    private FirebaseAuth mAuth;
    private Button signup;
    private String receivedPRev;
    private FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    ListenerRegistration listener;
    ValueEventListener valueListener;
    Spinner areaSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two_sign_up);

        mAuth = FirebaseAuth.getInstance();
        rollno = findViewById(R.id.textuser);
        signup = findViewById(R.id.stepTwoButton);
        yearText = findViewById(R.id.year);

        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid());


        Intent intent = getIntent();
        final String receivedName = intent.getStringExtra(EXTRA_NAME);
        final String receivedEmail = intent.getStringExtra(EXTRA_EMAIL);
        final String receivedPassword = intent.getStringExtra(EXTRA_PASSWORD);
        receivedPRev = intent.getStringExtra(EXTRA_PREV);


        FirebaseDatabase.getInstance().getReference().child("branch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("id").getValue(String.class);
                    areas.add(areaName);
                }

                areaSpinner = (Spinner) findViewById(R.id.collegeSpinner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(StepTwoSignUp.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                final String roll = rollno.getEditText().getText().toString();
                final String branch = areaSpinner.getSelectedItem().toString().trim();
                final String year = yearText.getEditText().getText().toString();

                if (roll.isEmpty() && branch.isEmpty() && year.isEmpty()) {
                    rollno.setError("Enter Roll Number");
                    yearText.setError("Enter Academic Year");
                }  else if (roll.isEmpty()) {
                    rollno.setError("Enter Roll Number");
                }   else if (year.isEmpty()) {
                    yearText.setError("Enter Academic Year");
                } else {



                    if (receivedPRev == null) {//google

                        User.addUser(roll, mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getDisplayName(), branch, year);
                      //  SaveSharedPreference.setUserName(getApplicationContext(), mAuth.getCurrentUser().getEmail());
                        startActivity(new Intent(getApplicationContext(), Navigation.class));
                        finish();
                    } else {//email
                        SaveSharedPreference.setUploaded(StepTwoSignUp.this, true);
                        SaveSharedPreference.setBranch(StepTwoSignUp.this, branch);

                        if (SaveSharedPreference.getUser(StepTwoSignUp.this).equals("")) {
                            listener = documentReference.addSnapshotListener((documentSnapshot, error) -> {
                                try {
                                    assert documentSnapshot != null;
                                    String name = documentSnapshot.getString("name");
                                    User.addUser(roll, mAuth.getCurrentUser().getEmail(), name, branch, year);
                                    Log.d(TAG, "Details Uploaded!");
                                    SaveSharedPreference.setUser(StepTwoSignUp.this, name);
                                    Intent intent = new Intent(StepTwoSignUp.this, MainActivity.class);
                                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } catch (Exception ignored) {
                                }
                            });
                        } else {
                            User.addUser(roll, mAuth.getCurrentUser().getEmail(), SaveSharedPreference.getUser(StepTwoSignUp.this), branch, year);
                            Log.d(TAG, "Details Uploaded!");
                            Intent intent = new Intent(StepTwoSignUp.this, MainActivity.class);
                            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        }
                    }
                }



                rollno.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        rollno.setError(null);
                    }
                });


            }
        });


    }

    @Override
    public void onBackPressed() {
        if (receivedPRev != null)
            super.onBackPressed();
        else
            Toast.makeText(this, "Please fill in the details and click submit!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (valueListener != null)
            databaseReference.removeEventListener(valueListener);
        if(listener != null)
            listener.remove();
        super.onDestroy();
    }
}

