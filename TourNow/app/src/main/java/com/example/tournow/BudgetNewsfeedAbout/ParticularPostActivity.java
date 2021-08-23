package com.example.tournow.BudgetNewsfeedAbout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.MainActivity;
import com.example.tournow.ModelClasses.StoreCommentData;
import com.example.tournow.ModelClasses.StoreLikedPost;
import com.example.tournow.ModelClasses.StorePostInfo;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.NewsfeedCustomAdapter;
import com.example.tournow.RecyclerViewAdapters.PostCommentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParticularPostActivity extends AppCompatActivity implements View.OnClickListener{

    int count = 0;
    EditText writeComment;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Parcelable recyclerViewState;
    PostCommentAdapter postCommentAdapter;
    ArrayList<StoreCommentData> storeCommentDataArrayList;
    TextView nametext, postText, likedPeople;
    ImageView likeBtn, postImage, deletePost, backPage, sendComment;
    String postUserName, postDescription, postLikesCount, postUserPhone;
    String postImageUrl, postVideoUrl, userPhone, postTokenKey;
    DatabaseReference databaseReference1, databaseReference2, databaseReference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_post);

        userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        progressBar = findViewById(R.id.commentProgressId);
        nametext = findViewById(R.id.postOwnerNameId);
        postText = findViewById(R.id.postDetailsId);
        likedPeople = findViewById(R.id.likesCountId);
        postImage = findViewById(R.id.postPictureId);
        writeComment = findViewById(R.id.writeCommentId);

        sendComment = findViewById(R.id.sendCommentId);
        sendComment.setOnClickListener(this);
        backPage = findViewById(R.id.backFromCommentsActivityId);
        backPage.setOnClickListener(this);
        likeBtn = findViewById(R.id.likeSignId);
        likeBtn.setOnClickListener(this);
        deletePost = findViewById(R.id.deleteId);
        deletePost.setOnClickListener(this);

        Intent it = getIntent();
        postUserName = it.getStringExtra("postOwnerName_key");
        postDescription = it.getStringExtra("postDescription_key");
        postImageUrl = it.getStringExtra("postImageUrl_key");
        postUserPhone = it.getStringExtra("postOwnerPhone_key");
        postLikesCount = it.getStringExtra("postLikes_key");
        postVideoUrl = it.getStringExtra("postVideoUrl_key");
        postTokenKey = it.getStringExtra("postIdentity_key");
        count = Integer.parseInt(postLikesCount);

        if(postImageUrl.equals("No_Image")){
            postImage.setVisibility(View.GONE);
        } else {
            postImage.setVisibility(View.VISIBLE);
            Picasso.get().load(postImageUrl).into(postImage);
        }

        nametext.setText(postUserName);
        postText.setText(postDescription);
        likedPeople.setText(postLikesCount);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Post Info");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Liked Post");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Post Comments");

        recyclerView = findViewById(R.id.postCommentsListId);
        recyclerView.setLayoutManager(new LinearLayoutManager(ParticularPostActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storeCommentDataArrayList = new ArrayList<StoreCommentData>();

        showCommentData();
    }

    private void refresh(int milliSecond){
        final Handler handler = new Handler(Looper.getMainLooper());

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showCommentData();
            }
        };

        handler.postDelayed(runnable, milliSecond);
    }

    private void showCommentData(){
        try {
            databaseReference3.child(postUserPhone).child(postTokenKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    storeCommentDataArrayList.clear();

                    for (DataSnapshot items : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshots : items.getChildren()) {
                            StoreCommentData storeCommentData = snapshots.getValue(StoreCommentData.class);
                            storeCommentDataArrayList.add(storeCommentData);
                        }
                    }

                    postCommentAdapter = new PostCommentAdapter(ParticularPostActivity.this, storeCommentDataArrayList);
                    recyclerView.setAdapter(postCommentAdapter);
                    postCommentAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        } catch (Exception e){
            progressBar.setVisibility(View.GONE);
            Log.i("Error ", e.getMessage());
        }

        refresh(1000);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backFromCommentsActivityId){
            finish();
            Intent intent = new Intent(ParticularPostActivity.this, MainActivity.class);
            intent.putExtra("EXTRA", "openChatFragment");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        if(v.getId()==R.id.deleteId){
            databaseReference1.addValueEventListener(new ValueEventListener() {
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
                                    Toast.makeText(ParticularPostActivity.this, "You are not owner", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        if(v.getId()==R.id.likeSignId){
            databaseReference1.addValueEventListener(new ValueEventListener() {
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
                                                    postUserPhone, (count+1), postImageUrl, postVideoUrl);

                                            storeLikedData(userPhone, snapshot.getKey(), postDescription, true, postImageUrl,
                                                    postVideoUrl, postUserPhone);

                                            likedPeople.setText(String.valueOf(count+1));
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

        if(v.getId()==R.id.sendCommentId){
            String commentText = writeComment.getText().toString();

            if(!commentText.equals("")){
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            for (DataSnapshot snapshot : item.getChildren()) {
                                String temp = snapshot.child("post").getValue().toString();

                                if(postDescription.equals(temp)){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Visitor").child(userPhone);
                                    ref.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String commenterName = snapshot.getValue().toString();
                                            storeCommentMethod(postUserPhone, postTokenKey, userPhone, commenterName, commentText);
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

        }
    }

    private void storeCommentMethod(String postUserPhone, String postKey, String commenterPhone, String commenterName, String commentText){
        StoreCommentData storeCommentData = new StoreCommentData(commenterPhone, commenterName, commentText);
        databaseReference3.child(postUserPhone).child(postKey).child(userPhone).child(commentText).setValue(storeCommentData);
        writeComment.setText("");
    }

    private void deletePostMethod(String userPhone, String postKey){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(ParticularPostActivity.this);
        alertDialogBuilder.setMessage("Do you want to delete this post ?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    databaseReference1.child(userPhone).child(postKey).removeValue();
                    databaseReference2.child(userPhone).child(postKey).removeValue();

                    finish();
                    Intent intent = new Intent(ParticularPostActivity.this, MainActivity.class);
                    intent.putExtra("EXTRA", "openChatFragment");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

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
                                String imageUrl, String videoUrl, String postUserPhone){

        StoreLikedPost storeLikedPost = new StoreLikedPost(userPhone, postText, liked, imageUrl, videoUrl, postUserPhone);
        databaseReference2.child(userPhone).child(targetKey).setValue(storeLikedPost);
    }

    private void storePostData(String targetKey, String postText, String userName, String postUserPhone,
                               int count,  String imageUrl, String videoUrl){
        StorePostInfo storePostInfo = new StorePostInfo(postUserPhone, userName, postText, count, imageUrl, videoUrl);
        databaseReference1.child(postUserPhone).child(targetKey).setValue(storePostInfo);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ParticularPostActivity.this, MainActivity.class);
        intent.putExtra("EXTRA", "openChatFragment");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        super.onBackPressed();
    }
}
