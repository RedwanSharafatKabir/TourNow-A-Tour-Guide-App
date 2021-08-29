package com.example.tournowadmin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View views;
    RecyclerView recyclerView;
    ArrayList<StorePlaceData> storePlaceDataArrayList;
    PlacesCustomAdapter placesCustomAdapter;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    Parcelable recyclerViewState;
    ConnectivityManager cm;
    NetworkInfo netInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_home, container, false);

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        progressBar = views.findViewById(R.id.placeListProgressbarId);

        recyclerView = views.findViewById(R.id.placesRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storePlaceDataArrayList = new ArrayList<StorePlaceData>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        showData();

        return views;
    }

    private void refresh(int milliSecond){
        final Handler handler = new Handler(Looper.getMainLooper());

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showData();
            }
        };

        handler.postDelayed(runnable, milliSecond);
    }

    private void showData(){
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    storePlaceDataArrayList.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot : item.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                StorePlaceData storePlaceData = dataSnapshot1.getValue(StorePlaceData.class);
                                storePlaceDataArrayList.add(storePlaceData);
                            }
                        }
                    }

                    placesCustomAdapter = new PlacesCustomAdapter(getActivity(), storePlaceDataArrayList);
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
            Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }

        refresh(1000);
    }
}
