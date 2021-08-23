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
import com.example.tournow.ModelClasses.StoreCommentData;
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

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.MyViewHolder> {

    Context context;
    ArrayList<StoreCommentData> storeCommentData;

    public PostCommentAdapter(Context c, ArrayList<StoreCommentData> p) {
        context = c;
        storeCommentData = p;
    }

    @NonNull
    @Override
    public PostCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostCommentAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.comments_custom_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostCommentAdapter.MyViewHolder holder, int position) {
        String commenterName = storeCommentData.get(position).getCommenterName();
        String postDescription = storeCommentData.get(position).getCommentText();

        holder.nametext.setText(commenterName);
        holder.postText.setText(postDescription);
    }

    @Override
    public int getItemCount() {
        return storeCommentData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nametext, postText;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            nametext = itemView.findViewById(R.id.commenterNameId);
            postText = itemView.findViewById(R.id.commentTextId);
        }
    }
}
