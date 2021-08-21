package com.example.tournow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class BusTicketActivity extends AppCompatActivity {

    ImageView imageView;
    GridLayout busgridlayout;
    CardView cardview1, cardview2, cardview3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);

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

        busgridlayout = (GridLayout) findViewById(R.id.bus_ticket_gridlayout);

        cardview1 = (CardView) findViewById(R.id.bdticket);
        cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busintent = new Intent(getApplicationContext(), BusTicketActivityWebview.class);
                busintent.putExtra("url", "https://bdtickets.com/");
                startActivity(busintent);
            }
        });

        cardview2 = (CardView) findViewById(R.id.bdticket1);
        cardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busintent1 = new Intent(getApplicationContext(), BusTicketActivityWebview.class);
                busintent1.putExtra("url", "https://www.shohoz.com/bus-tickets");
                startActivity(busintent1);
            }
        });

        cardview3 = (CardView) findViewById(R.id.bdticket2);
        cardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busintent2 = new Intent(getApplicationContext(), BusTicketActivityWebview.class);
                busintent2.putExtra("url", "http://www.busbd.com.bd/");
                startActivity(busintent2);
            }
        });
    }
}