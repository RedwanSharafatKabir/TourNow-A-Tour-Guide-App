package com.example.tournow.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.MainActivity;
import com.example.tournow.ModelClasses.StorePlaceData;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.PlacesCustomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaceListByPlaceType extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    ArrayList<StorePlaceData> storePlaceDataArrayList;
    PlacesCustomAdapter placesCustomAdapter;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    Parcelable recyclerViewState;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String message;
    ImageView backPage;
    TextView typeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list_by_place_type);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        progressBar = findViewById(R.id.placeListProgressbarId);

        recyclerView = findViewById(R.id.placeRecyclerViewByTypeId);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlaceListByPlaceType.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storePlaceDataArrayList = new ArrayList<StorePlaceData>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        typeName = findViewById(R.id.typeNameId);

        Intent it = getIntent();
        message = it.getStringExtra("place_key");

        if(message.equals("Park")){
            typeName.setText(getResources().getText(R.string.travel_binodon));
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                showParks();
            } else {
                Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        if(message.equals("Forest")){
            typeName.setText(getResources().getText(R.string.travel_bonanchol));
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                showForest();
            } else {
                Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        if(message.equals("Hill")){
            typeName.setText(getResources().getText(R.string.travel_pahar));
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                showHills();
            } else {
                Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        if(message.equals("River")){
            typeName.setText(getResources().getText(R.string.travel_somudro));
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                showRivers();
            } else {
                Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        if(message.equals("Museum")){
            typeName.setText(getResources().getText(R.string.travel_oitihasik));
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                showMuseum();
            } else {
                Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        if(message.equals("History")){
            typeName.setText(getResources().getText(R.string.travel_jadughor));
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                showHistory();
            } else {
                Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }

        backPage = findViewById(R.id.backPageId1);
        backPage.setOnClickListener(this);

    }

    private void showHistory() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                try {
                                    String placeName = dataSnapshot1.getKey();
                                    if (placeName.equals("Historical")) {
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                            StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                            storePlaceDataArrayList.add(storePlaceData);
                                        }
                                    }
                                } catch (Exception e){
                                    Log.i("Error ", e.getMessage());
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByPlaceType.this, storePlaceDataArrayList);
                    recyclerView.setAdapter(placesCustomAdapter);
                    placesCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        else {
            Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showMuseum() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                try{
                                    String placeName = dataSnapshot1.getKey();
                                    if(placeName.equals("Museum")) {
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                            StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                            storePlaceDataArrayList.add(storePlaceData);
                                        }
                                    }
                                } catch (Exception e){
                                    Log.i("Error ", e.getMessage());
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByPlaceType.this, storePlaceDataArrayList);
                    recyclerView.setAdapter(placesCustomAdapter);
                    placesCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        else {
            Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showRivers() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                try{
                                    String placeName = dataSnapshot1.getKey();
                                    if(placeName.equals("Sea Beach")) {
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                            StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                            storePlaceDataArrayList.add(storePlaceData);
                                        }
                                    }
                                } catch (Exception e){
                                    Log.i("Error ", e.getMessage());
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByPlaceType.this, storePlaceDataArrayList);
                    recyclerView.setAdapter(placesCustomAdapter);
                    placesCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        else {
            Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showHills() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                try{
                                    String placeName = dataSnapshot1.getKey();
                                    if(placeName.equals("Hill Tracts")) {
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                            StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                            storePlaceDataArrayList.add(storePlaceData);
                                        }
                                    }
                                } catch (Exception e){
                                    Log.i("Error ", e.getMessage());
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByPlaceType.this, storePlaceDataArrayList);
                    recyclerView.setAdapter(placesCustomAdapter);
                    placesCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        else {
            Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showForest() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                try{
                                    String placeName = dataSnapshot1.getKey();
                                    if(placeName.equals("Forests")) {
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                            StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                            storePlaceDataArrayList.add(storePlaceData);
                                        }
                                    }
                                } catch (Exception e){
                                    Log.i("Error ", e.getMessage());
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByPlaceType.this, storePlaceDataArrayList);
                    recyclerView.setAdapter(placesCustomAdapter);
                    placesCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        else {
            Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showParks() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                try{
                                    String placeName = dataSnapshot1.getKey();
                                    if(placeName.equals("Park")) {
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                            StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                            storePlaceDataArrayList.add(storePlaceData);
                                        }
                                    }
                                } catch (Exception e){
                                    Log.i("Error ", e.getMessage());
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByPlaceType.this, storePlaceDataArrayList);
                    recyclerView.setAdapter(placesCustomAdapter);
                    placesCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        else {
            Toast.makeText(PlaceListByPlaceType.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(PlaceListByPlaceType.this, MainActivity.class);
        intent.putExtra("EXTRA", "openPlaceTypeFragment");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backPageId1){
            finish();
            Intent intent = new Intent(PlaceListByPlaceType.this, MainActivity.class);
            intent.putExtra("EXTRA", "openPlaceTypeFragment");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
