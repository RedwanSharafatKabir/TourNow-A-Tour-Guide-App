package com.example.tournowadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DistrictSpinnerAdapter extends BaseAdapter {

    String district[];
    Context context;
    LayoutInflater layoutInflater;

    public DistrictSpinnerAdapter(Context context, String[] district){
        this.context = context;
        this.district = district;
    }

    @Override
    public int getCount() {
        return district.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View SpinnerView, ViewGroup parent) {
        if(SpinnerView==null){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            SpinnerView = layoutInflater.inflate(R.layout.district_spinner_adapter, parent, false);
        }

        TextView divisionTextView = SpinnerView.findViewById(R.id.districtTextViewId);

        divisionTextView.setText(district[position]);

        return SpinnerView;
    }
}
