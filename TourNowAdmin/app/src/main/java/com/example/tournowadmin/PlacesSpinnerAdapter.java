package com.example.tournowadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlacesSpinnerAdapter extends BaseAdapter {

    String places[];
    Context context;
    LayoutInflater layoutInflater;

    public PlacesSpinnerAdapter(Context context, String[] places){
        this.context = context;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.length;
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
            SpinnerView = layoutInflater.inflate(R.layout.plaes_spinner_adapter, parent, false);
        }

        TextView divisionTextView = SpinnerView.findViewById(R.id.placesTextViewId);

        divisionTextView.setText(places[position]);

        return SpinnerView;
    }
}
