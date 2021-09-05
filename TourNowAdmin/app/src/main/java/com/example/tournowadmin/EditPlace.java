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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditPlace extends AppCompatDialogFragment implements View.OnClickListener{

    View view;
    EditText placeName, aboutPlace, guidePlace;
    Button create;
    DatabaseReference databaseReference;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String division[], places[], divisionValue, placeType, placeImageUrl, districtValue, district[];
    Spinner divisionSpinner, placesSpinner, districtSpinner;
    DivisionSpinnerAdapter divisionSpinnerAdapter;
    PlacesSpinnerAdapter placesSpinnerAdapter;
    DistrictSpinnerAdapter districtSpinnerAdapter;
    ProgressDialog dialog;
    String guides, abouts, names, divisions, districts, placeTypes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.edit_place, null);
        builder.setView(view).setCancelable(false).setTitle("Edit place")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        dialog = new ProgressDialog(getActivity());
        placeName = view.findViewById(R.id.placeNameEditID);
        aboutPlace = view.findViewById(R.id.placeDetailsEditID);
        guidePlace = view.findViewById(R.id.placeGuideEditID);

        create = view.findViewById(R.id.createPlaceCompleteEditID);
        create.setOnClickListener(this);

        division = getResources().getStringArray(R.array.division_array);
        divisionSpinner = view.findViewById(R.id.divisionEditSpinnerId);
        places = getResources().getStringArray(R.array.places_array);
        placesSpinner = view.findViewById(R.id.placeTypeEditSpinnerId);
        district = getResources().getStringArray(R.array.district_array);
        districtSpinner = view.findViewById(R.id.districtEditSpinnerId);

        divisionSpinnerAdapter = new DivisionSpinnerAdapter(getActivity(), division);
        divisionSpinner.setAdapter(divisionSpinnerAdapter);
        placesSpinnerAdapter = new PlacesSpinnerAdapter(getActivity(), places);
        placesSpinner.setAdapter(placesSpinnerAdapter);
        districtSpinnerAdapter = new DistrictSpinnerAdapter(getActivity(), district);
        districtSpinner.setAdapter(districtSpinnerAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        names = getArguments().getString("placeNameKey");
        abouts = getArguments().getString("descriptionKey");
        guides = getArguments().getString("guideInfoKey");
        placeImageUrl = getArguments().getString("imageUrlKey");
        divisions = getArguments().getString("divisionKey");
        districts = getArguments().getString("districtKey");
        placeTypes = getArguments().getString("placeTypeKey");

        placeName.setText(names);
        aboutPlace.setText(abouts);
        guidePlace.setText(guides);

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

        if(v.getId()==R.id.createPlaceCompleteEditID){
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
                        storePlacesDataMethod(divisionValue, districtValue, placeType, placeNameString,
                                aboutPlaceString, guidePlaceString, placeImageUrl);
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

        Toast.makeText(getActivity(), "Place updated successfully", Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }
}
