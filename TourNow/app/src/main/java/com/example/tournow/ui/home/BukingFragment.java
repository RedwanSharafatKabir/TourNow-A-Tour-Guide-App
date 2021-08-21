package com.example.tournow.ui.home;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.tournow.BimanTicketActivity;
import com.example.tournow.BusTicketActivity;
import com.example.tournow.HotelActivityBuking;
import com.example.tournow.LaunchTicketActivity;
import com.example.tournow.R;
import com.example.tournow.TrainTicketActivity;

public class BukingFragment extends Fragment {


    GridLayout maingridlayout;
    CardView cardViewhotel, cardviewbiman, cardviewbus, cardviewlaunch, cardviewtrain;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buking, container, false);

        maingridlayout = (GridLayout) view.findViewById(R.id.buking_gridlayout);
        //setSingleEvent(maingridlayout);

        cardViewhotel = (CardView) view.findViewById(R.id.cardview_hotel);
        cardViewhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), HotelActivityBuking.class);
               startActivity(intent);

            }
        });

        cardviewbiman = (CardView) view.findViewById(R.id.cardview_biman);
        cardviewbiman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BimanTicketActivity.class);
                startActivity(intent);
            }
        });

        cardviewbus = (CardView) view.findViewById(R.id.cardview_bus);
        cardviewbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BusTicketActivity.class);
                startActivity(intent);
            }
        });

        cardviewlaunch = (CardView) view.findViewById(R.id.cardview_launch);
        cardviewlaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LaunchTicketActivity.class);
                startActivity(intent);
            }
        });

        cardviewtrain = (CardView) view.findViewById(R.id.cardview_train);
        cardviewtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrainTicketActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}