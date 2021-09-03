package com.example.tournowadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText email, password;
    Button buttonLogin;
    String passedString = "I_Admin", passedStringCheck="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference("Admin Info");

        buttonLogin = findViewById(R.id.buttonLoginId);

        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if(emailText.equals("") || passwordText.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                }

                else if (!emailText.equals("") || !passwordText.equals("")){
                    try {
                        databaseReference.child("admin1").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                try{
                                    if (emailText.equals(snapshot.child("email").getValue().toString()) &&
                                            passwordText.equals(snapshot.child("password").getValue().toString())) {

                                        rememberUser(passedString);
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                        email.setText("");
                                        password.setText("");

                                    }
                                    else {
                                        try{
                                            databaseReference.child("admin2").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    try{
                                                        if (emailText.equals(snapshot.child("email").getValue().toString()) &&
                                                                passwordText.equals(snapshot.child("password").getValue().toString())) {

                                                            rememberUser(passedString);
                                                            finish();
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            startActivity(intent);

                                                            email.setText("");
                                                            password.setText("");

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } catch (Exception e){
                                                        Log.i("Error_db ", e.getMessage());
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {}
                                            });

                                        } catch (Exception e){
                                            Log.i("Error_db ", e.getMessage());
                                        }
                                    }

                                } catch (Exception e){
                                    Log.i("Error_db ", e.getMessage());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                    } catch (Exception e){
                        Log.i("Error_db ", e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        rememberPassMethod();

        if (!passedStringCheck.isEmpty()) {
            finish();
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
        }

        super.onStart();
    }

    private void rememberUser(String passedString){
        try {
            FileOutputStream fileOutputStream = openFileOutput("Admin_Info.txt", Context.MODE_PRIVATE);
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

    private void rememberPassMethod(){
        try {
            String recievedMessageTc;
            FileInputStream fileInputStreamTc = openFileInput("Admin_Info.txt");
            InputStreamReader inputStreamReaderTc = new InputStreamReader(fileInputStreamTc);
            BufferedReader bufferedReaderTc = new BufferedReader(inputStreamReaderTc);
            StringBuffer stringBufferTc = new StringBuffer();

            while((recievedMessageTc=bufferedReaderTc.readLine())!=null){
                stringBufferTc.append(recievedMessageTc);
            }

            passedStringCheck = stringBufferTc.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
