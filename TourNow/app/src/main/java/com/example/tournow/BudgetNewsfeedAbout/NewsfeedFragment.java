package com.example.tournow.BudgetNewsfeedAbout;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tournow.ModelClasses.StorePostInfo;
import com.example.tournow.R;
import com.example.tournow.RecyclerViewAdapters.NewsfeedCustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class NewsfeedFragment extends Fragment implements View.OnClickListener{

    View views;
    EditText writePost;
    TextView publish, discard;
    ProgressBar progressBar;
    String userPhone, imageUrl, videoUrl, imageName, videoName, tempImage, tempVideo, postOwner;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    Parcelable recyclerViewState;
    ArrayList<StorePostInfo> storePostInfoArrayList;
    NewsfeedCustomAdapter newsfeedCustomAdapter;
    ImageView uploadImage, uploadVideo;
    ProgressDialog dialog;
    StorageReference storageReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        dialog = new ProgressDialog(getActivity());
        userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        progressBar = views.findViewById(R.id.postProgressBarId);
        writePost = views.findViewById(R.id.writePostEditTextId);

        publish = views.findViewById(R.id.publishPostId);
        publish.setOnClickListener(this);
        discard = views.findViewById(R.id.discardPostId);
        discard.setOnClickListener(this);
        uploadImage = views.findViewById(R.id.uploadImageId);
        uploadImage.setOnClickListener(this);
        uploadVideo = views.findViewById(R.id.uploadVideoId);
        uploadVideo.setOnClickListener(this);

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
            writePost.setHint(getResources().getText(R.string.write_post_title));
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
                        postOwner = snapshot.getValue().toString();
                        storePostData(postText, postOwner, "No_Image", "No_Video");
                        writePost.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

        }

        if(v.getId()==R.id.uploadVideoId){
            // Upload video from gallery
            someActivityResultLauncher.launch("image/*");
        }

        if(v.getId()==R.id.uploadImageId){
            someActivityResultLauncher.launch("image/*");
        }
    }

    ActivityResultLauncher<String> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        Uri uriProfileImage = result;
                        tempImage = uriProfileImage.toString();
                        Toast.makeText(getActivity(), "Image copied in clipboard", Toast.LENGTH_SHORT).show();
                        writePost.setHint(getResources().getText(R.string.re_write_post_title));

                        publish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String postText = writePost.getText().toString();
                                if(postText.equals("")){
                                    writePost.setError("Write something about post");
                                } else {
                                    uploadImageToFirebase(uriProfileImage, postText);
                                }
                            }
                        });
                    }
                }
            });

    private void uploadImageToFirebase(Uri uriProfileImage, String postText){
        dialog.setMessage("Publishing.....");
        dialog.show();

        imageName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + System.currentTimeMillis();
        storageReference = FirebaseStorage.getInstance()
                .getReference("post images/" + imageName + ".jpg");

        if(uriProfileImage!=null){
            storageReference.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            saveImageInfo(postText);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Could not send", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {}
            });
        }
    }

    private void saveImageInfo(String postText){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null && imageUrl!=null){
            UserProfileChangeRequest profile;
            profile = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(imageUrl)).build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {}
            });

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Visitor").child(userPhone).child("name");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    postOwner = snapshot.getValue().toString();
                    storePostData(postText, postOwner, imageUrl, "No_Video");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

            writePost.setHint(getResources().getText(R.string.write_post_title));
            writePost.setText("");
            dialog.dismiss();

            publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String postText = writePost.getText().toString();

                    if(!postText.equals("") ){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Visitor").child(userPhone).child("name");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                postOwner = snapshot.getValue().toString();
                                storePostData(postText, postOwner, "No_Image", "No_Video");
                                writePost.setText("");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                    } else {
                        writePost.setError("Write something about post");
                    }
                }
            });
        }
    }

    private void storePostData(String postText, String userName, String imageUrl, String videoUrl){
        StorePostInfo storePostInfo = new StorePostInfo(userPhone, userName, postText, 0, imageUrl, videoUrl);
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
