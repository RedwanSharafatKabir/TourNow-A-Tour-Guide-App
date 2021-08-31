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

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class PlaceListByDivision extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list_by_division);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        progressBar = findViewById(R.id.placeListProgressbarId2);

        recyclerView = findViewById(R.id.placeRecyclerViewByDivisionId);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlaceListByDivision.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storePlaceDataArrayList = new ArrayList<StorePlaceData>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        Intent it = getIntent();
        message = it.getStringExtra("place_key");

        if(message.equals("Park")){
            showParks();
        }

        backPage = findViewById(R.id.backPageId2);
        backPage.setOnClickListener(this);

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

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByDivision.this, storePlaceDataArrayList);
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
            Toast.makeText(PlaceListByDivision.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(PlaceListByDivision.this, MainActivity.class);
        intent.putExtra("EXTRA", "openDivisionFragment");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backPageId2){
            finish();
            Intent intent = new Intent(PlaceListByDivision.this, MainActivity.class);
            intent.putExtra("EXTRA", "openDivisionFragment");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}