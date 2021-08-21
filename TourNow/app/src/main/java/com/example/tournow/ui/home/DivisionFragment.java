package com.example.tournow.ui.home;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.tournow.DhakaDivisionActivity;
import com.example.tournow.NetworkErrorActivity;
import com.example.tournow.R;

public class DivisionFragment extends Fragment {

    GridLayout gridLayout;
    CardView cardView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_division, container, false);

        gridLayout = (GridLayout) view.findViewById(R.id.division_gridlayout);
        gridLayoutevent(gridLayout);

        return view;
    }

    private void gridLayoutevent (GridLayout gridLayout) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting() && activeNetwork.isAvailable();

        for (int i=0;i<gridLayout.getChildCount();i++) {

            cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI==0){
                        if (isConnected){
                            Intent intent = new Intent(getActivity(), DhakaDivisionActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getActivity(), NetworkErrorActivity.class);
                            intent.putExtra("name", getString(R.string.dhaka_division));
                            startActivity(intent);
                        }
                    }

                    if (finalI==1){
                        if (isConnected){
                            Intent intent = new Intent(getActivity(), DhakaDivisionActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getActivity(), NetworkErrorActivity.class);
                            intent.putExtra("name", getString(R.string.dhaka_division));
                            startActivity(intent);
                        }
                    }
                }
            });

        }
    }

}