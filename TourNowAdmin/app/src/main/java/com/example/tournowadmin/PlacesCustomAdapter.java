package com.example.tournowadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
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
        String imageUrl = storePlaceData.get(position).getPlaceImageUrl();
        String district = storePlaceData.get(position).getDistrictValue();

        holder.textView1.setText(placeName);
        holder.textView2.setText("Division: " + division);
        holder.textView3.setText("Place Type: " + placeType);
        holder.textView4.setText("Descrption: " + description);
        holder.textView5.setText("Tour Guide: " + guideInfo);
        holder.textView6.setText("District: " + district);
        Picasso.get().load(imageUrl).into(holder.placeImage);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("divisionKey", division);
                bundle.putString("districtKey", district);
                bundle.putString("placeTypeKey", placeType);
                bundle.putString("placeNameKey", placeName);
                bundle.putString("imageUrlKey", imageUrl);
                bundle.putString("descriptionKey", description);
                bundle.putString("guideInfoKey", guideInfo);

                EditPlace editPlace = new EditPlace();
                editPlace.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                editPlace.show(activity.getSupportFragmentManager(), "Sample dialog");
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot : item.getChildren()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                        if(placeName.equals(dataSnapshot2.getKey())){
                                            deletePostMethod(division, district, placeType, placeName);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i("Error ", error.getMessage());
                    }
                });
            }
        });
    }

    private void deletePostMethod(String division, String district, String placeType, String placeName){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete this post ?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    databaseReference.child(division).child(district).child(placeType).child(placeName).removeValue();

                } catch (Exception e){
                    Log.i("Message ", "Deleted");
                }
            }
        });

        alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return storePlaceData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4, textView5, textView6, delete, edit;
        ImageView placeImage;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            placeImage = itemView.findViewById(R.id.guardianImageId);

            delete = itemView.findViewById(R.id.deleteId);
            edit = itemView.findViewById(R.id.editId);
            textView1 = itemView.findViewById(R.id.placeNameId);
            textView2 = itemView.findViewById(R.id.divisionId);
            textView3 = itemView.findViewById(R.id.placeTypeId);
            textView4 = itemView.findViewById(R.id.placeDetailsId);
            textView5 = itemView.findViewById(R.id.placeGuideId);
            textView6 = itemView.findViewById(R.id.districtId);

            databaseReference = FirebaseDatabase.getInstance().getReference("Travel Places");
        }
    }
}
