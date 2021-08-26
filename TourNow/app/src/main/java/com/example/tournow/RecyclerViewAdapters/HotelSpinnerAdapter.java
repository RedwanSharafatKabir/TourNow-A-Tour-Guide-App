package com.example.tournow.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.tournow.R;

public class HotelSpinnerAdapter extends BaseAdapter {

    String hotel[];
    Context context;
    LayoutInflater layoutInflater;

    public HotelSpinnerAdapter(Context context, String[] hotel){
        this.context = context;
        this.hotel = hotel;
    }

    @Override
    public int getCount() {
        return hotel.length;
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
            SpinnerView = layoutInflater.inflate(R.layout.hotel_spinner_adapter, parent, false);
        }

        TextView divisionTextView = SpinnerView.findViewById(R.id.hotelTextViewId);

        divisionTextView.setText(hotel[position]);

        return SpinnerView;
    }
}

