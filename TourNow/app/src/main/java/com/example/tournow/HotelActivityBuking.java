package com.example.tournow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.tournow.ui.home.BukingFragment;

public class HotelActivityBuking extends AppCompatActivity {

    ImageView imageView;
    GridLayout hotelgridlayout;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_buking);

        imageView = (ImageView) findViewById(R.id.backimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayouthotel, new BukingFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                onBackPressed();
            }
        });

        hotelgridlayout = (GridLayout) findViewById(R.id.buking_Hotel_gridlayout);
        cardView = (CardView) findViewById(R.id.bukingdotcom);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukingintent = new Intent(getApplicationContext(), HotelBukingActivityWebview.class);
                bukingintent.putExtra("url", "https://www.booking.com/");
                startActivity(bukingintent);
            }
        });
    }
}