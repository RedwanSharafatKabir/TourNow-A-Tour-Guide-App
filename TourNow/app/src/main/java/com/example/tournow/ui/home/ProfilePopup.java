package com.example.tournow.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.tournow.LoginActivity;
import com.example.tournow.MainActivity;
import com.example.tournow.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfilePopup extends AppCompatDialogFragment {

    ProgressBar progressBar;
    View views;
    TextView txtclose, profilename, profileemail;
    Button buttonlogout, buttonpasswordchange;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    LayoutInflater inflater;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        views = inflater.inflate(R.layout.profile_popup, null);
        builder.setView(views).setCancelable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        progressBar = views.findViewById(R.id.profileProgressBarId);
        txtclose =(TextView) views.findViewById(R.id.txtclose);
        profilename = (TextView) views.findViewById(R.id.pro_name);
        profileemail = (TextView) views.findViewById(R.id.pro_email);
        buttonlogout = (Button) views.findViewById(R.id.log_out);
        buttonpasswordchange = (Button) views.findViewById(R.id.change_password);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        getProfileInfo();

        return builder.create();
    }

    private void getProfileInfo(){
        //For display database value
        databaseReference = firebaseDatabase.getInstance().getReference().child("Visitor")
                .child(user.getDisplayName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                String name = (String) datasnapshot.child("name").getValue();
                String email = (String) datasnapshot.child("email").getValue();

                profilename.setText(name);
                profileemail.setText(email);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // For logout
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutApp();
            }
        });

        // password change
        buttonpasswordchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonpasswordchange.setBackgroundResource(R.drawable.button_click_background);

                View view = inflater.inflate(R.layout.update_password,null);
                EditText new_password = view.findViewById(R.id.new_password);
                EditText c_new_password = view.findViewById(R.id.c_new_password);
                Button update_password = view.findViewById(R.id.button_update_password);

                androidx.appcompat.app.AlertDialog.Builder forgot_password= new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                forgot_password.setTitle("Update Your Password?")
                        .setMessage("Enter your new password ");
                update_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String new_pass, c_new_pass;
                        new_pass = new_password.getText().toString();
                        c_new_pass = c_new_password.getText().toString();

                        if (new_pass.isEmpty()){
                            new_password.setError("Password is required");
                            return;
                        }

                        if (c_new_pass.isEmpty()){
                            new_password.setError("Confirm password is required");
                            return;
                        }

                        if (!new_pass.equals(c_new_pass)){
                            c_new_password.setError("Password doesn't match");
                            return;
                        }

                        user.updatePassword(new_pass.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
                                Map<String, Object> map = new HashMap<>();
                                map.put("password", new_pass);
                                databaseReference.updateChildren(map);

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getDialog().dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                forgot_password.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buttonpasswordchange.setBackgroundResource(android.R.color.transparent);
                    }
                })
                .setCancelable(false)
                .setView(view)
                .create()
                .show();
            }
        });
    }

    private void logoutApp(){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Logout !");
        alertDialogBuilder.setMessage("Are you sure you want to logout ?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(user!=null){
                    buttonlogout.setBackgroundResource(R.drawable.button_click_background);
                    getActivity().finish();
                    firebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    dialog.cancel();
                }
            }
        });

        alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
