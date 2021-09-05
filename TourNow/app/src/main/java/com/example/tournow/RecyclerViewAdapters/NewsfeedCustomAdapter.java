package com.example.tournow.RecyclerViewAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tournow.BudgetNewsfeedAbout.ParticularPostActivity;
import com.example.tournow.LoginActivity;
import com.example.tournow.ModelClasses.StoreLikedPost;
import com.example.tournow.ModelClasses.StorePostInfo;
import com.example.tournow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsfeedCustomAdapter extends RecyclerView.Adapter<NewsfeedCustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<StorePostInfo> storePostInfo;
    DatabaseReference databaseReference, databaseReference2, databaseReference3;

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
        String postUserPhone = storePostInfo.get(position).getUserPhone();
        String userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String imageUrl = storePostInfo.get(position).getImageUrl();
        String videoUrl = storePostInfo.get(position).getVideoUrl();
        String postToken = storePostInfo.get(position).getPostToken();

        holder.nametext.setText(postUserName);
        holder.postText.setText(postDescription);
        holder.likedPeople.setText(String.valueOf(count));

        if(!imageUrl.equals("No_Image")){
            holder.postImage.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUrl).into(holder.postImage);

        }

        else if(imageUrl.equals("No_Image")){
            holder.postImage.setVisibility(View.GONE);
        }

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
                                                Log.i("Message ", "Liked");

                                            } catch (Exception e){
                                                storePostData(snapshot.getKey(), postDescription, postUserName,
                                                        postUserPhone, (count+1), imageUrl, videoUrl, postToken);

                                                storeLikedData(userPhone, snapshot.getKey(), postDescription, true, imageUrl,
                                                        videoUrl, postUserPhone, postToken);
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

        holder.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            for (DataSnapshot snapshot : item.getChildren()) {
                                String temp = snapshot.child("post").getValue().toString();

                                if(postDescription.equals(temp)){
                                    if(postUserPhone.equals(userPhone)) {
                                        deletePostMethod(userPhone, snapshot.getKey());
                                        break;
                                    } else {
                                        Toast.makeText(context, "You are not owner", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(context, ParticularPostActivity.class);
                it.putExtra("postOwnerName_key", postUserName);
                it.putExtra("postDescription_key", postDescription);
                it.putExtra("postImageUrl_key", imageUrl);
                it.putExtra("postOwnerPhone_key", postUserPhone);
                it.putExtra("postLikes_key", String.valueOf(count));
                it.putExtra("postVideoUrl_key", videoUrl);
                it.putExtra("postIdentity_key", postToken);
                context.startActivity(it);

                Log.i("Open_particular_post_page ", "Why man why");
            }
        });
    }

    private void deletePostMethod(String userPhone, String postToken){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete this post ?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    databaseReference.child(userPhone).child(postToken).removeValue();
                    databaseReference2.child(userPhone).child(postToken).removeValue();
                    databaseReference3.child(userPhone).child(postToken).removeValue();
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

    private void storeLikedData(String userPhone, String targetKey, String postText, boolean liked,
                                String imageUrl, String videoUrl, String postUserPhone, String postToken){

        StoreLikedPost storeLikedPost = new StoreLikedPost(userPhone, postText, liked, imageUrl, videoUrl, postUserPhone, postToken);
        databaseReference2.child(userPhone).child(targetKey).setValue(storeLikedPost);
    }

    private void storePostData(String targetKey, String postText, String userName, String postUserPhone,
                               int count,  String imageUrl, String videoUrl, String postToken){
        StorePostInfo storePostInfo = new StorePostInfo(postUserPhone, userName, postText, count, imageUrl, videoUrl, postToken);
        databaseReference.child(postUserPhone).child(targetKey).setValue(storePostInfo);
    }

    @Override
    public int getItemCount() {
        return storePostInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nametext, postText, commentBtn, likedPeople;
        ImageView likeBtn, postImage, deletePost;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            nametext = itemView.findViewById(R.id.publisherNameId);
            postText = itemView.findViewById(R.id.publishedPostId);
            deletePost = itemView.findViewById(R.id.deletePostId);
            postImage = itemView.findViewById(R.id.postImageId);

            commentBtn = itemView.findViewById(R.id.commentBtnId);
            likedPeople = itemView.findViewById(R.id.likedPeopleId);
            likeBtn = itemView.findViewById(R.id.likePostId);

            databaseReference = FirebaseDatabase.getInstance().getReference("Post Info");
            databaseReference2 = FirebaseDatabase.getInstance().getReference("Liked Post");
            databaseReference3 = FirebaseDatabase.getInstance().getReference("Post Comments");
        }
    }
}
