package com.example.tournow;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.BudgetNewsfeedAbout.AboutFragment;
import com.example.tournow.BudgetNewsfeedAbout.BudgetCalculateFragment;
import com.example.tournow.BudgetNewsfeedAbout.NewsfeedFragment;
import com.example.tournow.ui.home.AdviceFragment;
import com.example.tournow.ui.home.BlogFragment;
import com.example.tournow.ui.home.BukingFragment;
import com.example.tournow.ui.home.DivisionFragment;
import com.example.tournow.ui.home.HomeFragment;
import com.example.tournow.ui.home.OflineFragment;
import com.example.tournow.ui.home.ProfilePopup;
import com.example.tournow.ui.home.SettingFragment;
import com.example.tournow.ui.home.TravelFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String language = "", userPhone;
    DrawerLayout drawerLayout;
    public NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View hView;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    public TextView toolBarText;
    public Menu menu;
    DatabaseReference databaseReference;
    public CircleImageView fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
        setContentView(R.layout.activity_main);

        checkTheme();
        checkLangTheme();

        toolBarText = findViewById(R.id.setToolBarTextId);
        toolBarText.setText(getResources().getText(R.string.menu_home));

        drawerLayout = findViewById(R.id.drawerID);
        toolbar = findViewById(R.id.toolBarID);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference("User Avatar");

        navigationView = findViewById(R.id.navigationViewID);
        navigationView.setItemIconTintList(null);
        navigationView.bringToFront();

        menu = navigationView.getMenu();
        MenuItem home = menu.findItem(R.id.nav_home);
        MenuItem place = menu.findItem(R.id.nav_travel);
        MenuItem division = menu.findItem(R.id.nav_travel_division);
        MenuItem bookHotel = menu.findItem(R.id.nav_travel_buking);
        MenuItem budget = menu.findItem(R.id.nav_budget_calculate);
        MenuItem newsfeed = menu.findItem(R.id.nav_newsfeed);
        MenuItem offData = menu.findItem(R.id.nav_ofline_data);
        MenuItem blog = menu.findItem(R.id.nav_travel_blog);
        MenuItem advice = menu.findItem(R.id.nav_travel_advice);
        MenuItem share = menu.findItem(R.id.nav_tarvel_share);
        MenuItem setting = menu.findItem(R.id.nav_tarvel_setting);
        MenuItem about = menu.findItem(R.id.nav_about);

        home.setTitle(getResources().getText(R.string.menu_home));
        place.setTitle(getResources().getText(R.string.menu_travel));
        division.setTitle(getResources().getText(R.string.menu_travel_division));
        bookHotel.setTitle(getResources().getText(R.string.menu_travel_buking));
        budget.setTitle(getResources().getText(R.string.menu_budget_calculate));
        newsfeed.setTitle(getResources().getText(R.string.menu_newsfeed));
        offData.setTitle(getResources().getText(R.string.menu_offline_data));
        blog.setTitle(getResources().getText(R.string.menu_travel_blog));
        advice.setTitle(getResources().getText(R.string.menu_travel_advice));
        share.setTitle(getResources().getText(R.string.menu_travel_share));
        setting.setTitle(getResources().getText(R.string.menu_travel_setting));
        about.setTitle(getResources().getText(R.string.menu_about));

        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        navigationView.setCheckedItem(R.id.nav_home);
        hView = navigationView.getHeaderView(0);

        //profile view with fab
        fab = findViewById(R.id.fab);

        try{
            databaseReference.child(userPhone).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try{
                        Picasso.get().load(snapshot.getValue().toString()).into(fab);
                    } catch (Exception e){
                        Log.i("Error ", "Image not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

        } catch (Exception e){
            Log.i("Error ", "Image not found");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfilePopup profilePopup = new ProfilePopup();
                profilePopup.show(getSupportFragmentManager(), "Sample dialog");
            }
        });

        } catch (Exception e){
            Log.i("Error ", e.getMessage());
        }

        try {
            switch (getIntent().getStringExtra("EXTRA")) {
                case "openDivisionFragment":
                    toolBarText.setText(getResources().getText(R.string.menu_travel_division));
                    fragment = new DivisionFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentID, fragment, "MY_FRAGMENT");
                    fragmentTransaction.commit();
                    break;
            }
        } catch (Exception e){
            Log.i("Error ", e.getMessage());
        }

        try {
            switch (getIntent().getStringExtra("EXTRA")) {
                case "openPlaceTypeFragment":
                    toolBarText.setText(getResources().getText(R.string.menu_travel));
                    fragment = new TravelFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentID, fragment, "MY_FRAGMENT");
                    fragmentTransaction.commit();
                    break;
            }
        } catch (Exception e){
            Log.i("Error ", e.getMessage());
        }

        try {
            switch (getIntent().getStringExtra("EXTRA")) {
                case "openNewsfeedFragment":
                    toolBarText.setText(getResources().getText(R.string.menu_newsfeed));
                    fragment = new NewsfeedFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentID, fragment);
                    fragmentTransaction.commit();
                    break;
            }
        } catch (Exception e){
            Log.i("Error ", e.getMessage());
        }

        try {
            switch (getIntent().getStringExtra("Theme_EXTRA")) {
                case "openMainActivity":
                    toolBarText.setText(getResources().getText(R.string.menu_travel_setting));
                    fragment = new SettingFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentID, fragment);
                    fragmentTransaction.commit();
                    break;
            }
        } catch (Exception e){
            Log.i("Error ", e.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START,true);
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                toolBarText.setText(getResources().getText(R.string.menu_home));
                fragment = new HomeFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_travel:
                toolBarText.setText(getResources().getText(R.string.menu_travel));
                fragment = new TravelFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_travel_division:
                toolBarText.setText(getResources().getText(R.string.menu_travel_division));
                fragment = new DivisionFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_travel_buking:
                toolBarText.setText(getResources().getText(R.string.menu_travel_buking));
                fragment = new BukingFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_budget_calculate:
                toolBarText.setText(getResources().getText(R.string.menu_budget_calculate));
                fragment = new BudgetCalculateFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_newsfeed:
                toolBarText.setText(getResources().getText(R.string.menu_newsfeed));
                fragment = new NewsfeedFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_ofline_data:
                toolBarText.setText(getResources().getText(R.string.menu_offline_data));
                fragment = new OflineFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_travel_blog:
                toolBarText.setText(getResources().getText(R.string.menu_travel_blog));
                fragment = new BlogFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_travel_advice:
                toolBarText.setText(getResources().getText(R.string.menu_travel_advice));
                fragment = new AdviceFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_tarvel_share:
                shareApp();
                break;

            case R.id.nav_tarvel_setting:
                toolBarText.setText(getResources().getText(R.string.menu_travel_setting));
                fragment = new SettingFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_about:
                toolBarText.setText(getResources().getText(R.string.menu_about));
                fragment = new AboutFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentID, fragment);
                fragmentTransaction.commit();
                break;
        }

        return true;
    }

    private void shareApp(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareLinkText = "Download 'Tour Now' app from https://tournow.com.bd/";
        intent.putExtra(Intent.EXTRA_TEXT, shareLinkText);
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        try {
            FileOutputStream fileOutputStream = openFileOutput("Settings.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(lang.getBytes());
            fileOutputStream.close();
        }
        catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }

    private void checkLangTheme(){
        try {
            String recievedMessageTc;
            FileInputStream fileInputStreamTc = openFileInput("Settings.txt");
            InputStreamReader inputStreamReaderTc = new InputStreamReader(fileInputStreamTc);
            BufferedReader bufferedReaderTc = new BufferedReader(inputStreamReaderTc);
            StringBuffer stringBufferTc = new StringBuffer();

            while((recievedMessageTc = bufferedReaderTc.readLine())!=null){
                stringBufferTc.append(recievedMessageTc);
            }

            language = stringBufferTc.toString();
            setLocale(language);
        }
        catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }

    private void checkTheme(){
        try {
            String recievedMessageTc;
            FileInputStream fileInputStreamTc = openFileInput("Setting_Theme.txt");
            InputStreamReader inputStreamReaderTc = new InputStreamReader(fileInputStreamTc);
            BufferedReader bufferedReaderTc = new BufferedReader(inputStreamReaderTc);
            StringBuffer stringBufferTc = new StringBuffer();

            while((recievedMessageTc = bufferedReaderTc.readLine())!=null){
                stringBufferTc.append(recievedMessageTc);
            }

            String theme = stringBufferTc.toString();

            if(theme.equals("light")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            if(theme.equals("dark")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

        }
        catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }
}
