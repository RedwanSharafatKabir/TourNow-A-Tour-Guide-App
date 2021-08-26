package com.example.tournow.BudgetNewsfeedAbout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.DivisionSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.HotelSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.PlacesSpinnerAdapter;
import com.example.tournow.RecyclerViewAdapters.TransportSpinnerAdapter;

public class BudgetCalculateFragment extends Fragment implements View.OnClickListener{

    View views;
    Button calculateOutputBtn;
    EditText totalPerson, totalDays;
    String division[], places[], transport[], hotel[];
    String totalPersonValue, divisionValue, placesValue, transportValue, hotelValue, totalDaysValue;
    Spinner divisionSpinner, placesSpinner, transportSpinner, hotelSpinner;
    DivisionSpinnerAdapter divisionSpinnerAdapter;
    PlacesSpinnerAdapter placesSpinnerAdapter;
    TransportSpinnerAdapter transportSpinnerAdapter;
    HotelSpinnerAdapter hotelSpinnerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_budget, container, false);

        calculateOutputBtn = views.findViewById(R.id.calculateOutputBtnId);
        calculateOutputBtn.setOnClickListener(this);

        totalPerson = views.findViewById(R.id.enterTotalPersonId);
        totalDays = views.findViewById(R.id.enterTotalDaysId);

        division = getResources().getStringArray(R.array.division_array);
        divisionSpinner = views.findViewById(R.id.divisionSpinnerId);
        places = getResources().getStringArray(R.array.places_array);
        placesSpinner = views.findViewById(R.id.placesSpinnerId);
        transport = getResources().getStringArray(R.array.transport_array);
        transportSpinner = views.findViewById(R.id.transportSpinnerId);
        hotel = getResources().getStringArray(R.array.hotel_array);
        hotelSpinner = views.findViewById(R.id.hotelsSpinnerId);

        divisionSpinnerAdapter = new DivisionSpinnerAdapter(getActivity(), division);
        divisionSpinner.setAdapter(divisionSpinnerAdapter);
        placesSpinnerAdapter = new PlacesSpinnerAdapter(getActivity(), places);
        placesSpinner.setAdapter(placesSpinnerAdapter);
        transportSpinnerAdapter = new TransportSpinnerAdapter(getActivity(), transport);
        transportSpinner.setAdapter(transportSpinnerAdapter);
        hotelSpinnerAdapter = new HotelSpinnerAdapter(getActivity(), hotel);
        hotelSpinner.setAdapter(hotelSpinnerAdapter);

        divisionMethod();
        placesMethod();
        transportMethod();
        hotelMethod();

        return views;
    }

    private void placesMethod() {
        placesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placesValue = places[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void transportMethod(){
        transportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transportValue = transport[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void hotelMethod(){
        hotelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hotelValue = hotel[position];
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
        totalPersonValue = totalPerson.getText().toString();
        totalDaysValue = totalDays.getText().toString();

        if(v.getId()==R.id.calculateOutputBtnId){
            if(totalPersonValue.equals("") || totalDaysValue.equals("")){
                Toast.makeText(getActivity(), "Please fill-up all fields", Toast.LENGTH_SHORT).show();
            }

            if(placesValue.equals("Place") || divisionValue.equals("Division") || transportValue.equals("Transport") || hotelValue.equals("Hotel")
                || placesValue.equals("স্থান") || divisionValue.equals("বিভাগ") || transportValue.equals("যানবাহন") || hotelValue.equals("হোটেল")){

                Toast.makeText(getActivity(), "You must enter valid info", Toast.LENGTH_SHORT).show();
            }

            else {

            }
        }
    }
}
