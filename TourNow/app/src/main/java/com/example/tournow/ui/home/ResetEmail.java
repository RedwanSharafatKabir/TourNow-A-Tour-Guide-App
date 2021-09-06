package com.example.tournow.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.tournow.ModelClasses.Users;
import com.example.tournow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetEmail extends AppCompatDialogFragment implements View.OnClickListener {

    ProgressBar progressBar;
    EditText newEmailEdit, oldEmailEdit, enterPassword;
    View view;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String prevEmail, userPhone, userName;
    TextView reset, no;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.reset_email, null);
        builder.setView(view).setCancelable(false).setTitle("Reset email ?");

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        progressBar = view.findViewById(R.id.progressBarId);
        progressBar.setVisibility(View.GONE);
        oldEmailEdit = view.findViewById(R.id.enterOldEmailID);
        newEmailEdit = view.findViewById(R.id.enterNewEmailID);
        enterPassword = view.findViewById(R.id.enterPasswordID);

        no = view.findViewById(R.id.notChangeId);
        no.setOnClickListener(this);
        reset = view.findViewById(R.id.resetEmailId);
        reset.setOnClickListener(this);

        Bundle mArgs = getArguments();
        prevEmail = mArgs.getString("email_key");

        userPhone = user.getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        String newEmail = newEmailEdit.getText().toString();
        String oldEmail = oldEmailEdit.getText().toString();
        String pass = enterPassword.getText().toString();

        if (v.getId() == R.id.resetEmailId) {
            progressBar.setVisibility(View.VISIBLE);

            if (oldEmail.isEmpty()) {
                oldEmailEdit.setError("Enter old email");
                progressBar.setVisibility(View.GONE);
            }

            if (newEmail.isEmpty()) {
                newEmailEdit.setError("Enter new email");
                progressBar.setVisibility(View.GONE);
            }

            else {
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    if(prevEmail.equals(oldEmail)) {
                        AuthCredential credential = EmailAuthProvider.getCredential(oldEmail, pass);

                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Email Changed Successfully", Toast.LENGTH_SHORT).show();

                                                databaseReference.child(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        userName = snapshot.child("name").getValue().toString();

                                                        storeNewInfo(newEmail, pass, userPhone, userName);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {}
                                                });

                                                progressBar.setVisibility(View.GONE);
                                                getDialog().dismiss();

                                            } else {
                                                Toast.makeText(getActivity(), "Email Reset Failed", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(getActivity(), "Email Reset Failed", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Invalid Old Email", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }

                else {
                    Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }

        if (v.getId() == R.id.notChangeId) {
            getDialog().dismiss();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void storeNewInfo(String newEmail, String pass, String userPhone, String userName){
        Users users = new Users(userName, newEmail, pass, userPhone);
        databaseReference.child(userPhone).setValue(users);
    }
}
