package com.example.tournow.BudgetNewsfeedAbout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.DivisionSpinnerAdapter;

public class BudgetCalculateFragment extends Fragment {

    View views;
    String division[];
    Spinner divisionSpinner;
    DivisionSpinnerAdapter divisionSpinnerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_budget, container, false);

        division = getResources().getStringArray(R.array.division_array);
        divisionSpinner = views.findViewById(R.id.divisionSpinnerId);

        divisionSpinnerAdapter = new DivisionSpinnerAdapter(getActivity(), division);
        divisionSpinner.setAdapter(divisionSpinnerAdapter);

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String value = division[position];

                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return views;
    }
}
