package com.example.tournow.BudgetNewsfeedAbout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    TextView budgetResult, budgetAlert;
    EditText totalPerson, totalDays;
    Spinner divisionSpinner, placesSpinner, transportSpinner, hotelSpinner;
    DivisionSpinnerAdapter divisionSpinnerAdapter;
    PlacesSpinnerAdapter placesSpinnerAdapter;
    TransportSpinnerAdapter transportSpinnerAdapter;
    HotelSpinnerAdapter hotelSpinnerAdapter;
    String division[], places[], transport[], hotel[];
    String totalPersonValue, divisionValue, placesValue, transportValue, hotelValue, totalDaysValue, taka;
    int breakfast=50, lunch=150, snacks=150, dinner=150, acBus=0, nonAcBus=0, trainAc=0;
    int trainNonAc=0, trainLocal=0, launchdake=0, launchDblCbn=0, launchSnglCbn=0, plane=0, basicCost=0;
    int perHeadTransportCost=0, finalCostOnePerson=0, perHeadLivingCost=0, perHeadTravelCost=0, finalCostMultiplePerson=0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_budget, container, false);

        taka = (String) getResources().getText(R.string.taka_text);

        calculateOutputBtn = views.findViewById(R.id.calculateOutputBtnId);
        calculateOutputBtn.setOnClickListener(this);

        totalPerson = views.findViewById(R.id.enterTotalPersonId);
        totalDays = views.findViewById(R.id.enterTotalDaysId);

        budgetResult = views.findViewById(R.id.budgetResultId);
        budgetResult.setVisibility(View.GONE);
        budgetAlert = views.findViewById(R.id.budgetAlertId);
        budgetAlert.setVisibility(View.GONE);

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

            if( !divisionValue.equals("Barisal") && !divisionValue.equals("বরিশাল") &&
                    ((transportValue.equals("Launch (Single Cabin)") || transportValue.equals("Launch (Double Cabin)")) ||
                    (transportValue.equals("Launch (Dake)") || transportValue.equals("লঞ্চ (সিঙ্গেল কেবিন)")) ||
                    (transportValue.equals("লঞ্চ (ডাবল কেবিন)") || transportValue.equals("লঞ্চ (ডেক)")))){

                Toast.makeText(getActivity(), "You can travel only Barisal by launch", Toast.LENGTH_SHORT).show();
            }

            else if (!totalPersonValue.equals("") && !totalDaysValue.equals("")){
                int persons = Integer.parseInt(totalPersonValue);
                int days = Integer.parseInt(totalDaysValue);
                basicCost = breakfast + lunch + snacks + dinner;

                // Divisions
                if(divisionValue.equals("Dhaka") || divisionValue.equals("ঢাকা")){
                    acBus = 150*2;
                    nonAcBus = 80*2;
                    trainAc = 0;
                    trainNonAc = 0;
                    trainLocal = 0;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 0;
                }

                if(divisionValue.equals("Chittagong") || divisionValue.equals("চট্টগ্রাম")){
                    nonAcBus = 600*2;
                    acBus = 850*2;
                    trainAc = 1500*2;
                    trainNonAc = 650*2;
                    trainLocal = 120*2;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 10500*2;
                }

                if(divisionValue.equals("Khulna") || divisionValue.equals("খুলনা")){
                    nonAcBus = 500*2;
                    acBus = 750*2;
                    trainAc = 1500*2;
                    trainNonAc = 650*2;
                    trainLocal = 120*2;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 9500*2;
                }

                if(divisionValue.equals("Barisal") || divisionValue.equals("বরিশাল")){
                    nonAcBus = 500*2;
                    acBus = 800*2;
                    trainAc = 0;
                    trainNonAc = 0;
                    trainLocal = 0;
                    launchdake = 250*2;
                    launchSnglCbn = 1200*2;
                    launchDblCbn = 1700*2;
                    plane = 8500*2;
                }

                if(divisionValue.equals("Rajshahi") || divisionValue.equals("রাজশাহি")){
                    nonAcBus = 450*2;
                    acBus = 700*2;
                    trainAc = 1500*2;
                    trainNonAc = 650*2;
                    trainLocal = 120*2;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 7500*2;
                }

                if(divisionValue.equals("Sylhet") || divisionValue.equals("সিলেট")){
                    nonAcBus = 450*2;
                    acBus = 750*2;
                    trainAc = 1500*2;
                    trainNonAc = 650*2;
                    trainLocal = 120*2;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 7500*2;
                }

                if(divisionValue.equals("Rangpur") || divisionValue.equals("রংপুর")){
                    nonAcBus = 500*2;
                    acBus = 750*2;
                    trainAc = 1500*2;
                    trainNonAc = 650*2;
                    trainLocal = 120*2;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 7000*2;
                }

                if(divisionValue.equals("Mymensingh") || divisionValue.equals("ময়মনসিংহ")){
                    nonAcBus = 400*2;
                    acBus = 650*2;
                    trainAc = 1000*2;
                    trainNonAc = 450*2;
                    trainLocal = 100*2;
                    launchdake = 0;
                    launchSnglCbn = 0;
                    launchDblCbn = 0;
                    plane = 0;
                }

                // Transport
                if(transportValue.equals("Non AC Bus") || transportValue.equals("নন এসি বাস")){
                    perHeadTransportCost = nonAcBus;
                }

                if(transportValue.equals("AC Bus") || transportValue.equals("এসি বাস")){
                    perHeadTransportCost = acBus;
                }

                if(transportValue.equals("Train (Local)") || transportValue.equals("ট্রেন (লোকাল)")){
                    perHeadTransportCost = trainLocal;
                }

                if(transportValue.equals("Train (Inter-City AC)") || transportValue.equals("ট্রেন (ইন্টার-সিটি এসি)")){
                    perHeadTransportCost = trainAc;
                }

                if(transportValue.equals("Train (Inter-City Non AC)") || transportValue.equals("ট্রেন (ইন্টার-সিটি নন এসি)")){
                    perHeadTransportCost = trainNonAc;
                }

                if(transportValue.equals("Launch (Dake)") || transportValue.equals("লঞ্চ (ডেক)")){
                    perHeadTransportCost = launchdake;
                }

                if(transportValue.equals("Launch (Double Cabin)") || transportValue.equals("লঞ্চ (ডাবল কেবিন)")){
                    perHeadTransportCost = launchDblCbn;
                }

                if(transportValue.equals("Launch (Single Cabin)") || transportValue.equals("লঞ্চ (সিঙ্গেল কেবিন)")){
                    perHeadTransportCost = launchSnglCbn;
                }

                if(transportValue.equals("Airlines (Any)") || transportValue.equals("প্লেন (যেকোন)")){
                    perHeadTransportCost = plane;
                }

                // Hotel
                if(hotelValue.equals("5 Star (AC)") || hotelValue.equals("পাঁচ তারকা (এসি)")){
                    perHeadLivingCost = 8000;
                }

                if(hotelValue.equals("4 Star (AC)") || hotelValue.equals("চার তারকা (এসি)")){
                    perHeadLivingCost = 5500;
                }

                if(hotelValue.equals("General (AC)") || hotelValue.equals("সাধারণ (এসি)")){
                    perHeadLivingCost = 1500;
                }

                if(hotelValue.equals("General (Non AC)") || hotelValue.equals("সাধারণ (নন এসি)")){
                    perHeadLivingCost = 600;
                }

                perHeadTravelCost = basicCost + perHeadTransportCost + perHeadLivingCost;

                if(days>1){
                    finalCostOnePerson = ((days-1) * (basicCost+perHeadLivingCost)) + perHeadTravelCost;
                } else {
                    finalCostOnePerson = perHeadTravelCost;
                }

                finalCostMultiplePerson = finalCostOnePerson * persons;

                if(persons>1){
                    finalCostMultiplePerson = finalCostMultiplePerson - (persons*100);
                }

                budgetResult.setVisibility(View.VISIBLE);
                budgetResult.setText("Breakfast: " + breakfast + taka + "\nLunch: " + lunch + taka + "\nSnacks: " + snacks + taka +
                        "\nDinner: " + dinner + taka + "\n\nTotal Food Cost: " + basicCost + taka + "\n\nDestination: " + divisionValue +
                        "\nTransport: " + transportValue + "\nLiving Hotel: " + hotelValue + "\n\nPer Head Cost: " + finalCostOnePerson + taka +
                        "\n\nTotal Cost: " + finalCostMultiplePerson + taka);

                budgetAlert.setVisibility(View.VISIBLE);
            }
        }
    }
}
