package com.example.tournowadmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlacesCustomAdapter extends RecyclerView.Adapter<PlacesCustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<StorePlaceData> storePlaceData;
    DatabaseReference databaseReference;

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

        holder.textView1.setText(placeName);
        holder.textView2.setText("Division: " + division);
        holder.textView3.setText(placeType);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, placeName+"\n"+division+"\n"+placeType+"\n"+description+"\n"+guideInfo, Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                Intent intent = new Intent(activity, ParticularPlacePage.class);

//                intent.putExtra("guardianNameKey", guardianName);
//                intent.putExtra("guardianPhoneKey", guardianPhone);
//                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storePlaceData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;
        ImageView placeImage;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            databaseReference = FirebaseDatabase.getInstance().getReference("Place Images");
            placeImage = itemView.findViewById(R.id.guardianImageId);

            textView1 = itemView.findViewById(R.id.placeNameId);
            textView2 = itemView.findViewById(R.id.divisionId);
            textView3 = itemView.findViewById(R.id.placeTypeId);
        }
    }
}
