package com.example.tournow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.ModelClasses.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText textInputEditTextfullname, textInputEditTextphone, textInputEditTextpassword, textInputEditTextemail, textInputEditTextc_password;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressDialog progressDialog;
    String fullname, email, phone, password, c_password, passedString = "I_am_user";
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputEditTextfullname = (TextInputEditText) findViewById(R.id.fullname);
        textInputEditTextphone = (TextInputEditText) findViewById(R.id.phone);
        textInputEditTextpassword = (TextInputEditText) findViewById(R.id.password);
        textInputEditTextc_password = (TextInputEditText) findViewById(R.id.c_password);
        textInputEditTextemail = (TextInputEditText) findViewById(R.id.email);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        textViewLogin = (TextView) findViewById(R.id.loginText);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);

        Users users = new Users();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Visitor");

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullname = textInputEditTextfullname.getText().toString().trim();
                email = textInputEditTextemail.getText().toString().trim();
                phone = textInputEditTextphone.getText().toString().trim();
                password = textInputEditTextpassword.getText().toString().trim();
                c_password = textInputEditTextc_password.getText().toString().trim();

                if (fullname.equals("")){
                    textInputEditTextfullname.setError("Fullname is required");
                    return;
                }
                if (email.equals("")){
                    textInputEditTextemail.setError("Email is required");
                    return;
                }
                if (phone.equals("")){
                    textInputEditTextphone.setError("Phone number is required");
                    return;
                }
                if (password.equals("")){
                    textInputEditTextpassword.setError("Password is required");
                    return;
                }
                if (c_password.equals("")){
                    textInputEditTextc_password.setError("Confirmation Password is required");
                    return;
                }
                if (!password.equals(c_password)){
                    textInputEditTextc_password.setError("Password doesn't match");
                    return;
                }

                progressDialog.show();

                rememberMethod(passedString);
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.hide();
                        Toast.makeText(RegisterActivity.this, "Register Successfully",Toast.LENGTH_SHORT).show();
                        Users users = new Users(fullname, email, password, phone);

                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(user!=null){
                            UserProfileChangeRequest profile;
                            profile= new UserProfileChangeRequest.Builder().setDisplayName(phone).build();
                            user.updateProfile(profile).addOnCompleteListener(task -> {});
                        }

                        databaseReference.child(phone).setValue(users);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.hide();
                        Toast.makeText(RegisterActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void rememberMethod(String passedString){
        try {
            FileOutputStream fileOutputStream = openFileOutput("User_Info.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(passedString.getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}