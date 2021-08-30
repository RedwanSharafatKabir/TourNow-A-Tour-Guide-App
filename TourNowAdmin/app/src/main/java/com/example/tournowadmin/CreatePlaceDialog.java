package com.example.tournowadmin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

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

public class CreatePlaceDialog extends AppCompatDialogFragment implements View.OnClickListener{

    View view;
    Uri uriPlaceImage;
    EditText placeName, aboutPlace, guidePlace;
    Button create;
    LinearLayout uploadImageBtn;
    TextView uploadText;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String division[], places[], divisionValue, placeType, placeImageUrl="", image_name, districtValue, district[];
    Spinner divisionSpinner, placesSpinner, districtSpinner;
    DivisionSpinnerAdapter divisionSpinnerAdapter;
    PlacesSpinnerAdapter placesSpinnerAdapter;
    DistrictSpinnerAdapter districtSpinnerAdapter;
    ProgressDialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.create_place_dialog, null);
        builder.setView(view).setCancelable(false).setTitle("Add new place")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        dialog = new ProgressDialog(getActivity());
        placeName = view.findViewById(R.id.placeNameInputID);
        aboutPlace = view.findViewById(R.id.placeDetailsInputID);
        guidePlace = view.findViewById(R.id.placeGuideInputID);
        uploadText = view.findViewById(R.id.uploadImageTextId);

        create = view.findViewById(R.id.createPlaceCompleteID);
        create.setOnClickListener(this);
        uploadImageBtn = view.findViewById(R.id.uploadImageId);
        uploadImageBtn.setOnClickListener(this);

        division = getResources().getStringArray(R.array.division_array);
        divisionSpinner = view.findViewById(R.id.divisionSpinnerId);
        places = getResources().getStringArray(R.array.places_array);
        placesSpinner = view.findViewById(R.id.placeTypeSpinnerId);
        district = getResources().getStringArray(R.array.district_array);
        districtSpinner = view.findViewById(R.id.districtSpinnerId);

        divisionSpinnerAdapter = new DivisionSpinnerAdapter(getActivity(), division);
        divisionSpinner.setAdapter(divisionSpinnerAdapter);
        placesSpinnerAdapter = new PlacesSpinnerAdapter(getActivity(), places);
        placesSpinner.setAdapter(placesSpinnerAdapter);
        districtSpinnerAdapter = new DistrictSpinnerAdapter(getActivity(), district);
        districtSpinner.setAdapter(districtSpinnerAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        divisionMethod();
        placesMethod();
        districtMethod();

        return builder.create();
    }

    private void placesMethod() {
        placesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placeType = places[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void divisionMethod(){
        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisionValue = division[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void districtMethod(){
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtValue = district[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void onClick(View v) {
        String placeNameString = placeName.getText().toString();
        String aboutPlaceString = aboutPlace.getText().toString();
        String guidePlaceString = guidePlace.getText().toString();

        if(v.getId()==R.id.uploadImageId){
            someActivityResultLauncher.launch("image/*");
        }

        if(v.getId()==R.id.createPlaceCompleteID){
            if(placeType.equals("Place") || divisionValue.equals("Division") || districtValue.equals("District")){
                Toast.makeText(getActivity(), "Specify division and place", Toast.LENGTH_SHORT).show();
            }

            if(placeNameString.isEmpty()){
                placeName.setError("Enter class name");
            }

            if(aboutPlaceString.isEmpty()){
                aboutPlace.setError("Enter class Id");
            }

            if(guidePlaceString.isEmpty()){
                guidePlace.setError("Enter class Id");
            }

            else if(!placeType.equals("Place") && !divisionValue.equals("Division") && !districtValue.equals("District")) {
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    if(!placeImageUrl.equals("")){
                        storePlacesDataMethod(divisionValue, districtValue, placeType, placeNameString, aboutPlaceString, guidePlaceString, placeImageUrl);
                    }

                    else if(placeImageUrl.equals("")){
                        Toast.makeText(getActivity(), "Upload Place Image", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void storePlacesDataMethod(String divisionValue, String districtValue, String placeType, String placeName,
                                       String aboutPlace, String guidePlace, String placeImageUrl) {
        StorePlaceData storePlaceData = new StorePlaceData(divisionValue, placeType, placeName, aboutPlace, guidePlace, placeImageUrl, districtValue);
        databaseReference.child(divisionValue).child(districtValue).child(placeType).child(placeName).setValue(storePlaceData);

        Toast.makeText(getActivity(), "Place added successfully", Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    ActivityResultLauncher<String> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        uriPlaceImage = result;
                        uploadImageToFirebase(uriPlaceImage);
                    }
                }
            });

    private void uploadImageToFirebase(Uri uriPlaceImage){
        dialog.setMessage("Uploading.....");
        dialog.show();

        image_name = String.valueOf(System.currentTimeMillis());
        storageReference = FirebaseStorage.getInstance()
                .getReference("place images/" + image_name + ".jpg");

        if(uriPlaceImage!=null){
            storageReference.putFile(uriPlaceImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            placeImageUrl = uri.toString();
                            dialog.dismiss();
                            uploadText.setText("Image Added");
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
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Could not upload", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
