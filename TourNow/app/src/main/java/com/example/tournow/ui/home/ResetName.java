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

public class ResetName extends AppCompatDialogFragment implements View.OnClickListener {

    ProgressBar progressBar;
    EditText newNameEdit, oldNameEdit, enterPassword;
    View view;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String email, userPhone, prevName;
    TextView reset, no;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.reset_name, null);
        builder.setView(view).setCancelable(false).setTitle("Reset username ?");

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        progressBar = view.findViewById(R.id.progressBarId1);
        progressBar.setVisibility(View.GONE);
        oldNameEdit = view.findViewById(R.id.enterOldNameID);
        newNameEdit = view.findViewById(R.id.enterNewNameID);
        enterPassword = view.findViewById(R.id.enterPasswordID1);

        no = view.findViewById(R.id.notChangeId1);
        no.setOnClickListener(this);
        reset = view.findViewById(R.id.resetEmailId1);
        reset.setOnClickListener(this);

        Bundle mArgs = getArguments();
        email = mArgs.getString("email_key");
        prevName = mArgs.getString("name_key");

        userPhone = user.getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        String newName = newNameEdit.getText().toString();
        String oldName = oldNameEdit.getText().toString();
        String passrd = enterPassword.getText().toString();

        if (v.getId() == R.id.resetEmailId1) {
            progressBar.setVisibility(View.VISIBLE);

            if (oldName.isEmpty()) {
                oldNameEdit.setError("Enter old name");
                progressBar.setVisibility(View.GONE);
            }

            if (newName.isEmpty()) {
                newNameEdit.setError("Enter new name");
                progressBar.setVisibility(View.GONE);
            }

            if (passrd.isEmpty()) {
                enterPassword.setError("Enter password");
                progressBar.setVisibility(View.GONE);
            }

            else {
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    if(prevName.equals(oldName)) {

                        databaseReference.child(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String pass = snapshot.child("password").getValue().toString();

                                if(pass.equals(passrd)){
                                    storeNewInfo(email, pass, userPhone, newName);
                                    progressBar.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(getActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                    } else {
                        Toast.makeText(getActivity(), "Invalid Previous Username", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }

                else {
                    Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }

        if (v.getId() == R.id.notChangeId1) {
            getDialog().dismiss();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void storeNewInfo(String email, String pass, String userPhone, String userName){
        Users users = new Users(userName, email, pass, userPhone);
        databaseReference.child(userPhone).setValue(users);

        Toast.makeText(getActivity(), "Username updated successfully", Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }
}
