package com.example.tournowadmin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreatePlaceDialog extends AppCompatDialogFragment implements View.OnClickListener{

    EditText placeName, aboutPlace, guidePlace;
    Button create;
    DatabaseReference databaseReference;
    View view;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String division[], places[], divisionValue, placeType;
    Spinner divisionSpinner, placesSpinner;
    DivisionSpinnerAdapter divisionSpinnerAdapter;
    PlacesSpinnerAdapter placesSpinnerAdapter;

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

        placeName = view.findViewById(R.id.placeNameInputID);
        aboutPlace = view.findViewById(R.id.placeDetailsInputID);
        guidePlace = view.findViewById(R.id.placeGuideInputID);

        create = view.findViewById(R.id.createPlaceCompleteID);
        create.setOnClickListener(this);

        division = getResources().getStringArray(R.array.division_array);
        divisionSpinner = view.findViewById(R.id.divisionSpinnerId);
        places = getResources().getStringArray(R.array.places_array);
        placesSpinner = view.findViewById(R.id.placeTypeSpinnerId);

        divisionSpinnerAdapter = new DivisionSpinnerAdapter(getActivity(), division);
        divisionSpinner.setAdapter(divisionSpinnerAdapter);
        placesSpinnerAdapter = new PlacesSpinnerAdapter(getActivity(), places);
        placesSpinner.setAdapter(placesSpinnerAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        divisionMethod();
        placesMethod();

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

    @Override
    public void onClick(View v) {
        String placeNameString = placeName.getText().toString();
        String aboutPlaceString = aboutPlace.getText().toString();
        String guidePlaceString = guidePlace.getText().toString();

        if(v.getId()==R.id.createPlaceCompleteID){
            if(placeType.equals("Place") || divisionValue.equals("Division")){
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

            else if(!placeType.equals("Place") & !divisionValue.equals("Division")) {
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    storePlacesDataMethod(divisionValue, placeType, placeNameString, aboutPlaceString, guidePlaceString);

                } else {
                    Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void storePlacesDataMethod(String divisionValue, String placeType, String placeName, String aboutPlace, String guidePlace) {
        StorePlaceData storePlaceData = new StorePlaceData(divisionValue, placeType, placeName, aboutPlace, guidePlace);
        databaseReference.child(divisionValue).child(placeType).child(placeName).setValue(storePlaceData);

        Toast.makeText(getActivity(), "Place added successfully", Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }
}
