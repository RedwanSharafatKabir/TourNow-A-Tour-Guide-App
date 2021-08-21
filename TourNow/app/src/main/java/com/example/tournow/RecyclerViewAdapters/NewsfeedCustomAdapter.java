package com.example.tournow.RecyclerViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tournow.ModelClasses.StoreLikedPost;
import com.example.tournow.ModelClasses.StorePostInfo;
import com.example.tournow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsfeedCustomAdapter extends RecyclerView.Adapter<NewsfeedCustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<StorePostInfo> storePostInfo;
    DatabaseReference databaseReference, databaseReference2;

    public NewsfeedCustomAdapter(Context c, ArrayList<StorePostInfo> p) {
        context = c;
        storePostInfo = p;
    }

    @NonNull
    @Override
    public NewsfeedCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsfeedCustomAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.newsfeed_custom_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsfeedCustomAdapter.MyViewHolder holder, int position) {
        String postUserName = storePostInfo.get(position).getName();
        String postDescription = storePostInfo.get(position).getPost();
        int count = storePostInfo.get(position).getLikeNumbers();
        String postUserPhone =  storePostInfo.get(position).getUserPhone();
        String userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        holder.nametext.setText(postUserName);
        holder.postText.setText(postDescription);
        holder.likedPeople.setText(String.valueOf(count));

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            for (DataSnapshot snapshot : item.getChildren()) {
                                String temp = snapshot.child("post").getValue().toString();

                                if(postDescription.equals(temp)){
                                    databaseReference2.child(userPhone).child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            try {
                                                String temp2 = snapshot.child("postText").getValue().toString();
                                                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();

                                            } catch (Exception e){
                                                storePostData(snapshot.getKey(), postDescription, postUserName, postUserPhone, (count+1));
                                                storeLikedData(userPhone, snapshot.getKey(), postDescription, true);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {}
                                    });
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });
    }

    private void storeLikedData(String userPhone, String targetKey, String postText, boolean liked){
        StoreLikedPost storeLikedPost = new StoreLikedPost(userPhone, postText, liked);
        databaseReference2.child(userPhone).child(targetKey).setValue(storeLikedPost);
    }

    private void storePostData(String targetKey, String postText, String userName, String postUserPhone, int count){
        StorePostInfo storePostInfo = new StorePostInfo(postUserPhone, userName, postText, count);
        databaseReference.child(postUserPhone).child(targetKey).setValue(storePostInfo);
    }

    @Override
    public int getItemCount() {
        return storePostInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nametext, postText, commentBtn, likedPeople;
        ImageView likeBtn;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nametext = itemView.findViewById(R.id.publisherNameId);
            postText = itemView.findViewById(R.id.publishedPostId);

            commentBtn = itemView.findViewById(R.id.commentBtnId);
            likedPeople = itemView.findViewById(R.id.likedPeopleId);
            likeBtn = itemView.findViewById(R.id.likePostId);

            databaseReference = FirebaseDatabase.getInstance().getReference("Post Info");
            databaseReference2 = FirebaseDatabase.getInstance().getReference("Liked Post");
        }
    }
}
