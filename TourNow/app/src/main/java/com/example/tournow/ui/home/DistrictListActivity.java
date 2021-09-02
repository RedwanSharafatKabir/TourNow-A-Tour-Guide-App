package com.example.tournow.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tournow.MainActivity;
import com.example.tournow.R;

public class DistrictListActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    String message;
    TextView divisionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting() && activeNetwork.isAvailable();

        imageView = (ImageView) findViewById(R.id.backimageDiv);
        imageView.setOnClickListener(this);

        divisionName = findViewById(R.id.divisionNameId);

        Intent it = getIntent();
        message = it.getStringExtra("division_key");

        if(message.equals("Dhaka")){
            divisionName.setText(getResources().getText(R.string.dhaka_division));
        }

        if(message.equals("Chittagong")){
            divisionName.setText(getResources().getText(R.string.division_chottogram));
        }

        if(message.equals("Khulna")){
            divisionName.setText(getResources().getText(R.string.division_khulna));
        }

        if(message.equals("Barisal")){
            divisionName.setText(getResources().getText(R.string.division_barishal));
        }

        if(message.equals("Rajshahi")){
            divisionName.setText(getResources().getText(R.string.division_rajshahi));
        }

        if(message.equals("Sylhet")){
            divisionName.setText(getResources().getText(R.string.division_sylhet));
        }

        if(message.equals("Rangpur")){
            divisionName.setText(getResources().getText(R.string.division_rangpur));
        }

        if(message.equals("Mymensingh")){
            divisionName.setText(getResources().getText(R.string.division_mymenshingh));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backimageDiv){
            finish();
            Intent intent = new Intent(DistrictListActivity.this, MainActivity.class);
            intent.putExtra("EXTRA", "openDivisionFragment");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(DistrictListActivity.this, MainActivity.class);
        intent.putExtra("EXTRA", "openDivisionFragment");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}