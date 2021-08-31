package com.example.tournow.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tournow.ModelClasses.StorePlaceData;
import com.example.tournow.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlacesCustomAdapter extends RecyclerView.Adapter<PlacesCustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<StorePlaceData> storePlaceData;

    public PlacesCustomAdapter(Context c, ArrayList<StorePlaceData> p) {
        context = c;
        storePlaceData = p;
    }

    @NonNull
    @Override
    public PlacesCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlacesCustomAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.places_custom_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesCustomAdapter.MyViewHolder holder, int position) {
        String placeName = storePlaceData.get(position).getPlaceName();
        String division = storePlaceData.get(position).getDivisionValue();
        String placeType = storePlaceData.get(position).getPlaceType();
        String description = storePlaceData.get(position).getAboutPlace();
        String guideInfo = storePlaceData.get(position).getGuidePlace();
        String imageUrl = storePlaceData.get(position).getPlaceImageUrl();
        String district = storePlaceData.get(position).getDistrictValue();

        holder.textView1.setText(placeName);
        holder.textView2.setText("Division: " + division);
        holder.textView3.setText("Place Type: " + placeType);
        holder.textView4.setText("Descrption: " + description);
        holder.textView5.setText("Tour Guide: " + guideInfo);
        holder.textView6.setText("District: " + district);

        Picasso.get().load(imageUrl).into(holder.placeImage);
    }

    @Override
    public int getItemCount() {
        return storePlaceData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4, textView5, textView6;
        ImageView placeImage;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            placeImage = itemView.findViewById(R.id.guardianImageId);

            textView1 = itemView.findViewById(R.id.placeNameId);
            textView2 = itemView.findViewById(R.id.divisionId);
            textView3 = itemView.findViewById(R.id.placeTypeId);
            textView4 = itemView.findViewById(R.id.placeDetailsId);
            textView5 = itemView.findViewById(R.id.placeGuideId);
            textView6 = itemView.findViewById(R.id.districtId);
        }
    }
}
