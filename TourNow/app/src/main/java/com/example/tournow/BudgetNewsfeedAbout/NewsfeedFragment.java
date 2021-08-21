package com.example.tournow.BudgetNewsfeedAbout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tournow.ModelClasses.StorePostInfo;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.NewsfeedCustomAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsfeedFragment extends Fragment implements View.OnClickListener{

    View views;
    EditText writePost;
    TextView publish, discard;
    ProgressBar progressBar;
    String userPhone;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    Parcelable recyclerViewState;
    ArrayList<StorePostInfo> storePostInfoArrayList;
    NewsfeedCustomAdapter newsfeedCustomAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        progressBar = views.findViewById(R.id.postProgressBarId);
        writePost = views.findViewById(R.id.writePostEditTextId);

        publish = views.findViewById(R.id.publishPostId);
        publish.setOnClickListener(this);
        discard = views.findViewById(R.id.discardPostId);
        discard.setOnClickListener(this);

        recyclerView = views.findViewById(R.id.postRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storePostInfoArrayList = new ArrayList<StorePostInfo>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Post Info");

        showPostdata();

        return views;
    }

    @Override
    public void onClick(View v) {
        String postText = writePost.getText().toString();

        if(v.getId()==R.id.discardPostId){
            writePost.setText("");
            progressBar.setVisibility(View.GONE);
        }

        if(v.getId()==R.id.publishPostId){
            progressBar.setVisibility(View.VISIBLE);

            if(postText.equals("")){
                writePost.setError("Write post first");
                progressBar.setVisibility(View.GONE);
            }

            else {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Visitor").child(userPhone).child("name");

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        storePostData(postText, snapshot.getValue().toString());
                        writePost.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

        }
    }

    private void storePostData(String postText, String userName){
        StorePostInfo storePostInfo = new StorePostInfo(userPhone, userName, postText, 0);
        databaseReference.child(userPhone).push().setValue(storePostInfo);
        Toast.makeText(getActivity(), "Post Published", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    private void refresh(int milliSecond){
        final Handler handler = new Handler(Looper.getMainLooper());

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showPostdata();
            }
        };

        handler.postDelayed(runnable, milliSecond);
    }

    private void showPostdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storePostInfoArrayList.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : item.getChildren()) {

                        StorePostInfo storePostInfo = snapshot.getValue(StorePostInfo.class);
                        storePostInfoArrayList.add(storePostInfo);
                    }
                }

                newsfeedCustomAdapter = new NewsfeedCustomAdapter(getActivity(), storePostInfoArrayList);
                recyclerView.setAdapter(newsfeedCustomAdapter);
                newsfeedCustomAdapter.notifyDataSetChanged();
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

        refresh(1000);
    }
}
