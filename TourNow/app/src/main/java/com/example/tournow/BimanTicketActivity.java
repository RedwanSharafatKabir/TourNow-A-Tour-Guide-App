package com.example.tournow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class BimanTicketActivity extends AppCompatActivity {

    ImageView imageView;
    GridLayout bimangridlayout;
    CardView cardViewbiman1, cardViewbiman2, cardViewbiman3, cardViewbiman4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biman_ticket);

        imageView = (ImageView) findViewById(R.id.backimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayoutbiman, new BukingFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                onBackPressed();
            }
        });

        bimangridlayout = (GridLayout) findViewById(R.id.biman_ticket_gridlayout);

        cardViewbiman1 = (CardView) findViewById(R.id.bimanbangladesh);
        cardViewbiman1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bimanintent1 = new Intent(getApplicationContext(), BimanTicketActivityWebview.class);
                bimanintent1.putExtra("url", "https://www.biman-airlines.com/");
                startActivity(bimanintent1);
            }
        });

        cardViewbiman2 = (CardView) findViewById(R.id.bimanbangladesh1);
        cardViewbiman2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bimanintent2 = new Intent(getApplicationContext(), BimanTicketActivityWebview.class);
                bimanintent2.putExtra("url", "https://bdtickets.com/airlines");
                startActivity(bimanintent2);
            }
        });

        cardViewbiman3 = (CardView) findViewById(R.id.bimanbangladesh2);
        cardViewbiman3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bimanintent3 = new Intent(getApplicationContext(), BimanTicketActivityWebview.class);
                bimanintent3.putExtra("url", "https://www.gozayaan.com/?search=flight");
                startActivity(bimanintent3);
            }
        });

        cardViewbiman4 = (CardView) findViewById(R.id.bimanbangladesh3);
        cardViewbiman4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bimanintent4 = new Intent(getApplicationContext(), BimanTicketActivityWebview.class);
                bimanintent4.putExtra("url", "https://www.flightexpert.com/#tab_tab-1");
                startActivity(bimanintent4);
            }
        });
    }
}
