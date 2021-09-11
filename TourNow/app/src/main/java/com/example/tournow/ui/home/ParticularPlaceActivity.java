package com.example.tournow.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tournow.R;
import com.squareup.picasso.Picasso;

public class ParticularPlaceActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    ImageView placeImage;
    String placeName, division, placeType, description, guideInfo, imageUrl, district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_place);

        placeImage = findViewById(R.id.placesImageId2);

        textView1 = findViewById(R.id.placeNameId1);
        textView2 = findViewById(R.id.divisionId1);
        textView3 = findViewById(R.id.districtId1);
        textView4 = findViewById(R.id.placeTypeId1);
        textView5 = findViewById(R.id.placeDetailsId1);
        textView6 = findViewById(R.id.placeGuideId1);

        Intent it = getIntent();
        placeName = it.getStringExtra("placeName_key");
        division = it.getStringExtra("division_key");
        district = it.getStringExtra("district_key");
        placeType = it.getStringExtra("placeType_key");
        description = it.getStringExtra("description_key");
        guideInfo = it.getStringExtra("guideInfo_key");
        imageUrl = it.getStringExtra("imageUrl_key");

        textView1.setText(placeName);
        textView2.setText(division);
        textView3.setText(district);
        textView4.setText(placeType);
        textView5.setText(description);
        textView6.setText(guideInfo);

        Picasso.get().load(imageUrl).into(placeImage);
    }
}
