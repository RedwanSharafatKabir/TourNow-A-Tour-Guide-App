package com.example.tournow.ui.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tournow.R;

public class TravelFragment extends Fragment implements View.OnClickListener{

    View views;
    CardView park, forest, hills, rivers, museum, historical;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_travel, container, false);

        park = views.findViewById(R.id.binodonKendroId);
        park.setOnClickListener(this);
        forest = views.findViewById(R.id.bonancholId);
        forest.setOnClickListener(this);
        hills = views.findViewById(R.id.paharPorbotId);
        hills.setOnClickListener(this);
        rivers = views.findViewById(R.id.riverSeaId);
        rivers.setOnClickListener(this);
        museum = views.findViewById(R.id.museumId);
        museum.setOnClickListener(this);
        historical = views.findViewById(R.id.historicalPlaceId);
        historical.setOnClickListener(this);

        return views;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.binodonKendroId){
            getActivity().finish();
            Intent it = new Intent(getActivity(), PlaceListByPlaceType.class);
            it.putExtra("place_key", "Park");
            startActivity(it);
        }

        if(v.getId()==R.id.bonancholId){
            getActivity().finish();
            Intent it = new Intent(getActivity(), PlaceListByPlaceType.class);
            it.putExtra("place_key", "Forest");
            startActivity(it);
        }

        if(v.getId()==R.id.paharPorbotId){
            getActivity().finish();
            Intent it = new Intent(getActivity(), PlaceListByPlaceType.class);
            it.putExtra("place_key", "Hill");
            startActivity(it);
        }

        if(v.getId()==R.id.riverSeaId){
            getActivity().finish();
            Intent it = new Intent(getActivity(), PlaceListByPlaceType.class);
            it.putExtra("place_key", "River");
            startActivity(it);
        }

        if(v.getId()==R.id.museumId){
            getActivity().finish();
            Intent it = new Intent(getActivity(), PlaceListByPlaceType.class);
            it.putExtra("place_key", "Museum");
            startActivity(it);
        }

        if(v.getId()==R.id.historicalPlaceId){
            getActivity().finish();
            Intent it = new Intent(getActivity(), PlaceListByPlaceType.class);
            it.putExtra("place_key", "History");
            startActivity(it);
        }
    }
}
