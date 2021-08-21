package com.example.tournow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class TrainTicketActivity extends AppCompatActivity {

    ImageView imageView;
    GridLayout traingridlayout;
    CardView cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_ticket);

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

        traingridlayout = (GridLayout) findViewById(R.id.train_ticket_gridlayout);

        cardview = (CardView) findViewById(R.id.bdtickettrain);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainintent = new Intent(getApplicationContext(), TrainTicketActivityWebview.class);
                trainintent.putExtra("url", "https://www.esheba.cnsbd.com");
                startActivity(trainintent);
            }
        });
    }
}