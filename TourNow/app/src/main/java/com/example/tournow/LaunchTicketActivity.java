package com.example.tournow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class LaunchTicketActivity extends AppCompatActivity {

    ImageView imageView;
    GridLayout busgridlayout;
    CardView cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_ticket);

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

        busgridlayout = (GridLayout) findViewById(R.id.launch_ticket_gridlayout);

        cardview = (CardView) findViewById(R.id.bdticketlaunch);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchintent = new Intent(getApplicationContext(), LaunchTicketActivityWebview.class);
                launchintent.putExtra("url", "https://bdtickets.com/launch");
                startActivity(launchintent);
            }
        });
    }
}