package com.example.tournow.ui.home;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.tournow.NetworkErrorActivity;
import com.example.tournow.R;

public class DivisionFragment extends Fragment {

    GridLayout gridLayout;
    CardView cardView;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    View views;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_division, container, false);

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        gridLayout = (GridLayout) views.findViewById(R.id.division_gridlayout);

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            gridLayoutevent(gridLayout);
        }
        else {
            Toast.makeText(getActivity(), "Turn on internet connection", Toast.LENGTH_SHORT).show();
        }

        return views;
    }

    private void gridLayoutevent (GridLayout gridLayout) {
        for (int i=0;i<gridLayout.getChildCount();i++) {

            cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Dhaka");
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Chittagong");
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Khulna");
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Barisal");
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Rajshahi");
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Sylhet");
                            startActivity(intent);
                        }

                        if (finalI == 6) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Rangpur");
                            startActivity(intent);
                        }

                        if (finalI == 7) {
                            Intent intent = new Intent(getActivity(), DistrictListActivity.class);
                            intent.putExtra("division_key", "Mymensingh");
                            startActivity(intent);
                        }
                    }
                });
        }
    }
}
