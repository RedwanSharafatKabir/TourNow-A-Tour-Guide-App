package com.example.tournow.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;
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

public class PlaceListByDistrict extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    ArrayList<StorePlaceData> storePlaceDataArrayList;
    PlacesCustomAdapter placesCustomAdapter;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    Parcelable recyclerViewState;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String messageDiv, messageDist;
    ImageView backPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list_by_district);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        progressBar = findViewById(R.id.placeListProgressbarId2);

        recyclerView = findViewById(R.id.placeRecyclerViewByDistrictId);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlaceListByDistrict.this));
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
        messageDiv = it.getStringExtra("division_key");
        messageDist = it.getStringExtra("district_key");

        showParks();

        backPage = findViewById(R.id.backPageId2);
        backPage.setOnClickListener(this);

    }

    private void showParks() {
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.child(messageDiv).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(messageDist.equals(dataSnapshot.getKey())){
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    StorePlaceData storePlaceData = dataSnapshot2.getValue(StorePlaceData.class);
                                    storePlaceDataArrayList.add(storePlaceData);
                                }
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(PlaceListByDistrict.this, storePlaceDataArrayList);
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
            Toast.makeText(PlaceListByDistrict.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(PlaceListByDistrict.this, DistrictListActivity.class);
        intent.putExtra("division_key", messageDiv);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backPageId2){
            finish();
            Intent intent = new Intent(PlaceListByDistrict.this, DistrictListActivity.class);
            intent.putExtra("division_key", messageDiv);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
