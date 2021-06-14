package com.college.collegeconnect.ui.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import com.college.collegeconnect.R;
import com.college.collegeconnect.datamodels.SaveSharedPreference;
import com.college.collegeconnect.datamodels.doc;
import com.college.collegeconnect.ui.event.UpcomingEvents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Objects;

public class ToolsFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    TextView tv;
    CardView roomLocator, events;

    private DatabaseReference reference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        roomLocator = view.findViewById(R.id.roomLocator);
        events = view.findViewById(R.id.events);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null)
            bottomNavigationView = getActivity().findViewById(R.id.bottomNav);
        tv = getActivity().findViewById(R.id.navTitle);
        tv.setText("TOOLS");

        roomLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
                LayoutInflater inflater = ((AppCompatActivity) getContext()).getLayoutInflater();
                View view = inflater.inflate(R.layout.document, null);
                builder.setView(view);
                Button set = view.findViewById(R.id.set_dialog_button_doc);
                TextInputLayout placeholder = view.findViewById(R.id.placeholder_doc);

                final AlertDialog dialog = builder.create();
                dialog.show();
                Objects.requireNonNull(dialog.getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(placeholder.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                    placeholder.requestFocus();
                } catch (Exception ignored) {
                }
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!placeholder.getEditText().getText().toString().equals(""))
                            FirebaseApp.initializeApp(getContext());

                        reference = FirebaseDatabase.getInstance().getReference().child("doc");
                        String id = reference.push().getKey();
                        String b = SaveSharedPreference.getBranch(getContext());
                        String nom = SaveSharedPreference.getUser(getContext());
                        doc model = new doc(nom, placeholder.getEditText().getText().toString(), b, id );
                        //update the data to Firebase
                        reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "demande has been inserted successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    String error = task.getException().toString();
                                    Toast.makeText(getContext(), "Failed: " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    });

            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UpcomingEvents.class));
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        bottomNavigationView.getMenu().findItem(R.id.nav_tools).setChecked(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().findItem(R.id.nav_tools).setChecked(true);
    }
}
