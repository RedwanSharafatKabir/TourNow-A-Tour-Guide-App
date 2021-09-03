package com.example.tournow.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.MainActivity;
import com.example.tournow.NetworkErrorActivity;
import com.example.tournow.R;

public class DistrictListActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    String divisionName="", districtName="";
    TextView divisionNameText;
    GridLayout dhakaL, chtgL, barslL, sylhtL, khlnL, rjshL, rngprL, mnsnghL;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8;
    ConnectivityManager cm;
    NetworkInfo netInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        imageView = (ImageView) findViewById(R.id.backimageDiv);
        imageView.setOnClickListener(this);
        divisionNameText = findViewById(R.id.divisionNameId);

        dhakaL = (GridLayout) findViewById(R.id.division_dhaka_gridlayout);
        chtgL = (GridLayout) findViewById(R.id.division_chtgrm_gridlayout);
        barslL = (GridLayout) findViewById(R.id.division_barisal_gridlayout);
        sylhtL = (GridLayout) findViewById(R.id.division_sylhet_gridlayout);
        khlnL = (GridLayout) findViewById(R.id.division_khulna_gridlayout);
        rjshL = (GridLayout) findViewById(R.id.division_rajshahi_gridlayout);
        rngprL = (GridLayout) findViewById(R.id.division_rangpur_gridlayout);
        mnsnghL = (GridLayout) findViewById(R.id.division_mymnsngh_gridlayout);

        Intent it = getIntent();
        divisionName = it.getStringExtra("division_key");

        if(divisionName.equals("Dhaka")){
            divisionNameText.setText(getResources().getText(R.string.dhaka_division));
            dhakaL.setVisibility(View.VISIBLE);
            gridLayoutevent1(dhakaL);
        }

        if(divisionName.equals("Chittagong")){
            divisionNameText.setText(getResources().getText(R.string.division_chottogram));
            chtgL.setVisibility(View.VISIBLE);
            gridLayoutevent2(chtgL);
        }

        if(divisionName.equals("Khulna")){
            divisionNameText.setText(getResources().getText(R.string.division_khulna));
            khlnL.setVisibility(View.VISIBLE);
            gridLayoutevent3(khlnL);
        }

        if(divisionName.equals("Barisal")){
            divisionNameText.setText(getResources().getText(R.string.division_barishal));
            barslL.setVisibility(View.VISIBLE);
            gridLayoutevent4(barslL);
        }

        if(divisionName.equals("Rajshahi")){
            divisionNameText.setText(getResources().getText(R.string.division_rajshahi));
            rjshL.setVisibility(View.VISIBLE);
            gridLayoutevent5(rjshL);
        }

        if(divisionName.equals("Sylhet")){
            divisionNameText.setText(getResources().getText(R.string.division_sylhet));
            sylhtL.setVisibility(View.VISIBLE);
            gridLayoutevent6(sylhtL);
        }

        if(divisionName.equals("Rangpur")){
            divisionNameText.setText(getResources().getText(R.string.division_rangpur));
            rngprL.setVisibility(View.VISIBLE);
            gridLayoutevent7(rngprL);
        }

        if(divisionName.equals("Mymensingh")){
            divisionNameText.setText(getResources().getText(R.string.division_mymenshingh));
            mnsnghL.setVisibility(View.VISIBLE);
            gridLayoutevent8(mnsnghL);
        }
    }

    private void gridLayoutevent1(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView1 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Kishoreganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Gazipur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Gopalganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Tangail";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            districtName = "Dhaka";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            districtName = "Narsingdi";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 6) {
                            districtName = "Narayanganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 7) {
                            districtName = "Faridpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 8) {
                            districtName = "Madaripur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 9) {
                            districtName = "Manikganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 10) {
                            districtName = "Munshiganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 11) {
                            districtName = "Rajbari";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 12) {
                            districtName = "Shariatpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent2(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView2 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Bandarban";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Brahmanbaria";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Chandpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Chittagong";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            districtName = "Coxs Bazar";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            districtName = "Comilla";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 6) {
                            districtName = "Feni";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 7) {
                            districtName = "Khagrachhari";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 8) {
                            districtName = "Lakshmipur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 9) {
                            districtName = "Noakhali";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 10) {
                            districtName = "Rangamati";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent3(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView3 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Bagerhat";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Chuadanga";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Jessore";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Jhenaidah";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            districtName = "Khulna";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            districtName = "Kushtia";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 6) {
                            districtName = "Magura";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 7) {
                            districtName = "Meherpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 8) {
                            districtName = "Narail";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 9) {
                            districtName = "Satkhira";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent4(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView4 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Barguna";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Barisal";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Bhola";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Jhalokati";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            districtName = "Patuakhali";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            districtName = "Pirojpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent5(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView5 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Bogra";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Joypurhat";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Naogaon";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Natore";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            districtName = "Chapainawabganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            districtName = "Pabna";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 6) {
                            districtName = "Rajshahi";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 7) {
                            districtName = "Sirajganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent6(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView6 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Habiganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Moulvibazar";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Sunamganj";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Sylhet";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent7(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView7 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == 0) {
                            districtName = "Dinajpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Gaibandha";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Kurigram";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Lalmonirhat";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 4) {
                            districtName = "Nilphamari";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 5) {
                            districtName = "Panchagarh";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 6) {
                            districtName = "Rangpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 7) {
                            districtName = "Thakurgaon";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void gridLayoutevent8(GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++) {
            cardView8 = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                cardView8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), String.valueOf(finalI), Toast.LENGTH_SHORT).show();
                        if (finalI == 0) {
                            districtName = "Jamalpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 1) {
                            districtName = "Mymensingh";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 2) {
                            districtName = "Netrakona";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }

                        if (finalI == 3) {
                            districtName = "Sherpur";
                            finish();
                            Intent intent = new Intent(DistrictListActivity.this, PlaceListByDistrict.class);
                            intent.putExtra("division_key", divisionName);
                            intent.putExtra("district_key", districtName);
                            startActivity(intent);
                        }
                    }
                });

            }

            else {
                Toast.makeText(DistrictListActivity.this, "Turn on internet connection", Toast.LENGTH_SHORT).show();
            }

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
