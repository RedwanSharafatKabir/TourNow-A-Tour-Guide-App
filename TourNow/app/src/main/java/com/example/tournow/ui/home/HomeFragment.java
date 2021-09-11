package com.example.tournow.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.ModelClasses.StorePlaceData;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.PlacesCustomAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<StorePlaceData> storePlaceDataArrayList;
    PlacesCustomAdapter placesCustomAdapter;
    Parcelable recyclerViewState;
    View views;
    EditText enterPlaceName;
    ImageView placeImage;
//    TextView name, division, district, details, guide, type;
    TextView searchPlace;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    String districtStr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_home, container, false);

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");

        recyclerView = views.findViewById(R.id.placeRecyclerViewByDistrictId1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storePlaceDataArrayList = new ArrayList<StorePlaceData>();
        recyclerView.setVisibility(View.GONE);

        enterPlaceName = views.findViewById(R.id.enterPlaceNameId);
        searchPlace = views.findViewById(R.id.searchPlaceId);

        searchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = enterPlaceName.getText().toString();

                if(value.equals("")){
                    enterPlaceName.setError("Search place by district");
                }

                else {
                    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                        try {
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    storePlaceDataArrayList.clear();

                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            districtStr = dataSnapshot1.getKey();

                                            try {
                                                if (value.equals(districtStr)) {
                                                    recyclerView.setVisibility(View.VISIBLE);

                                                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                                            StorePlaceData storePlaceData = dataSnapshot3.getValue(StorePlaceData.class);
                                                            storePlaceDataArrayList.add(storePlaceData);
                                                        }
                                                    }
                                                }

                                            } catch (Exception e){
                                                recyclerView.setVisibility(View.GONE);
                                                Log.i("Error_Db ", e.getMessage());
                                            }
                                        }
                                    }

                                    placesCustomAdapter = new PlacesCustomAdapter(getActivity(), storePlaceDataArrayList);
                                    recyclerView.setAdapter(placesCustomAdapter);
                                    placesCustomAdapter.notifyDataSetChanged();
                                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        } catch (Exception e){
                            Log.i("Error_Db ", e.getMessage());
                        }
                    }

                    else {
                        Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return views;
    }
}
