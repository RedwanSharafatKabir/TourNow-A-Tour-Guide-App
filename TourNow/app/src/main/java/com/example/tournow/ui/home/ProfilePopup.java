package com.example.tournow.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.tournow.LoginActivity;
import com.example.tournow.MainActivity;
import com.example.tournow.ModelClasses.StoreUserImage;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.DivisionSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.HotelSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.NameEmailSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.PlacesSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.TransportSpinnerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePopup extends AppCompatDialogFragment implements View.OnClickListener{

    ProgressBar progressBar;
    View views;
    TextView txtclose, profilename, profileemail;
    Button buttonlogout, buttonpasswordchange;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference, imageReference;
    FirebaseDatabase firebaseDatabase;
    LayoutInflater inflater;
    CircleImageView profileImage;
    String profileImageUrl, userPhone, image_name;
    StorageReference storageReference;
    ProgressDialog dialog;
    Uri uriProfileImage;
    String namesEmails[];
    Spinner nameEmailSpinner;
    NameEmailSpinnerAdapter nameEmailSpinnerAdapter;
    ConnectivityManager cm;
    NetworkInfo netInfo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        views = inflater.inflate(R.layout.profile_popup, null);
        builder.setView(views).setCancelable(false);

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        dialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userPhone = user.getDisplayName();

        progressBar = views.findViewById(R.id.profileProgressBarId);
        txtclose =(TextView) views.findViewById(R.id.txtclose);
        profilename = (TextView) views.findViewById(R.id.pro_name);
        profilename.setOnClickListener(this);
        profileemail = (TextView) views.findViewById(R.id.pro_email);
        profileemail.setOnClickListener(this);
        buttonlogout = (Button) views.findViewById(R.id.log_out);
        buttonpasswordchange = (Button) views.findViewById(R.id.change_password);
        profileImage = views.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);

        namesEmails = getResources().getStringArray(R.array.name_email_array);
        nameEmailSpinner = views.findViewById(R.id.nameEmailSpinnerId);
        nameEmailSpinnerAdapter = new NameEmailSpinnerAdapter(getActivity(), namesEmails);
        nameEmailSpinner.setAdapter(nameEmailSpinnerAdapter);
        getSpinnerValue();

        imageReference = FirebaseDatabase.getInstance().getReference("User Avatar");

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            getProfileInfo();
        } else {
            Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

        return builder.create();
    }

    private void getSpinnerValue() {
        nameEmailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    String value = namesEmails[position];

                    if(value.equals("Username") || value.equals("নাম")){
                        String userName = profilename.getText().toString();
                        String userEmail = profileemail.getText().toString();

                        Bundle armgs = new Bundle();
                        armgs.putString("name_key", userName);
                        armgs.putString("email_key", userEmail);

                        ResetName resetName = new ResetName();
                        resetName.setArguments(armgs);
                        resetName.show(getActivity().getSupportFragmentManager(), "Sample dialog");
                    }

                    if(value.equals("Email") || value.equals("ইমেইল")){
                        String userEmail = profileemail.getText().toString();

                        Bundle armgs = new Bundle();
                        armgs.putString("email_key", userEmail);

                        ResetEmail resetEmail = new ResetEmail();
                        resetEmail.setArguments(armgs);
                        resetEmail.show(getActivity().getSupportFragmentManager(), "Sample dialog");
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void refresh(int milliSecond){
        final Handler handler = new Handler(Looper.getMainLooper());

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getProfileInfo();
            }
        };

        handler.postDelayed(runnable, milliSecond);
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

                imageReference.child(userPhone).child("avatar").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            Picasso.get().load(snapshot.getValue().toString()).into(profileImage);
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e){
                            Log.i("Error", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
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

        refresh(1000);
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
                    setNullDataMethod("");

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

    private void setNullDataMethod(String passedString){
        try {
            FileOutputStream fileOutputStream = getActivity().openFileOutput("User_Info.txt", Context.MODE_PRIVATE);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profile_image){
            someActivityResultLauncher.launch("image/*");
        }
    }

    ActivityResultLauncher<String> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        profileImage.setImageURI(result);
                        uriProfileImage = result;
                        uploadImageToFirebase();
                    }
                }
            });

    private void uploadImageToFirebase(){
        dialog.setMessage("Uploading.....");
        dialog.show();

        image_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        storageReference = FirebaseStorage.getInstance()
                .getReference("profile images/" + image_name + ".jpg");

        if(uriProfileImage!=null){
            storageReference.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profileImageUrl = uri.toString();
                            saveUserInfo();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Could not upload", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {}
            });
        }
    }

    private void saveUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null && profileImageUrl!=null){
            UserProfileChangeRequest profile;
            profile = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(profileImageUrl)).build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {}
            });

            storeImageMethod(profileImageUrl);
            dialog.dismiss();
            Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();

            setImageInMainActivity();
        }
    }

    private void storeImageMethod(String profileImageUrl){
        StoreUserImage storeUserImage = new StoreUserImage(profileImageUrl);
        imageReference.child(userPhone).setValue(storeUserImage);
    }

    private void setImageInMainActivity(){
        try{
            imageReference.child(userPhone).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try{
                        Picasso.get().load(snapshot.getValue().toString()).into(((MainActivity) getActivity()).fab);
                    } catch (Exception e){
                        Log.i("Error ", "Image not founr");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

        } catch (Exception e){
            Log.i("Error ", "Image not founr");
        }
    }
}
