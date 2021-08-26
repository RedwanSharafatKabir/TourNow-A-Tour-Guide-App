package com.example.tournow.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tournow.BudgetNewsfeedAbout.ParticularPostActivity;
import com.example.tournow.MainActivity;
import com.example.tournow.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class SettingFragment extends Fragment implements View.OnClickListener{

    Switch darkSwitch, lightSwitch;
    View views;
    String language = "", temp;
    TextView changeLangBtn, themeText, darkText, lightText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        views = inflater.inflate(R.layout.fragment_setting, container, false);

        darkSwitch = views.findViewById(R.id.darkSwitchId);
        lightSwitch = views.findViewById(R.id.lightSwitchId);

        try {
            String recievedMessageTc;
            FileInputStream fileInputStreamTc = getActivity().openFileInput("Setting_Theme.txt");
            InputStreamReader inputStreamReaderTc = new InputStreamReader(fileInputStreamTc);
            BufferedReader bufferedReaderTc = new BufferedReader(inputStreamReaderTc);
            StringBuffer stringBufferTc = new StringBuffer();

            while((recievedMessageTc = bufferedReaderTc.readLine())!=null){
                stringBufferTc.append(recievedMessageTc);
            }

            String theme = stringBufferTc.toString();

            if(theme.equals("light")){
                lightSwitch.setChecked(true);
                temp = "light";

                try {
                    FileOutputStream fileOutputStream = getActivity().openFileOutput("Setting_Theme.txt", Context.MODE_PRIVATE);
                    fileOutputStream.write(temp.getBytes());
                    fileOutputStream.close();
                }
                catch (Exception e) {
                    Log.i("Error ", e.getMessage());
                }
            }

            if(theme.equals("dark")){
                darkSwitch.setChecked(true);
                temp = "dark";

                try {
                    FileOutputStream fileOutputStream = getActivity().openFileOutput("Setting_Theme.txt", Context.MODE_PRIVATE);
                    fileOutputStream.write(temp.getBytes());
                    fileOutputStream.close();
                }
                catch (Exception e) {
                    Log.i("Error ", e.getMessage());
                }
            }

        }
        catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }

        changeLangBtn = views.findViewById(R.id.changeLangBtnId);
        changeLangBtn.setOnClickListener(this);

        themeText = views.findViewById(R.id.theme_setting);
        darkText = views.findViewById(R.id.darkId);
        lightText = views.findViewById(R.id.lightId);

        checkLangTheme();
        checkTheme();

        return views;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.changeLangBtnId){
            final String[] itemsName = {"English", "বাংলা"};

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setSingleChoiceItems(itemsName, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        setLocale("en");
                    }

                    else if(which==1){
                        setLocale("bn");
                    }
                }
            });

            alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialogBuilder.create().show();
        }
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());

        try {
            if(lang.equals("en")){
                changeLangBtn.setText(" Change Language");
                themeText.setText("App Theme");
                darkText.setText("Dark Mode");
                lightText.setText("Light Mode");
                ((MainActivity) getActivity()).toolBarText.setText(getResources().getText(R.string.menu_travel_setting));
                setNavigationItemsName();

            } else if(lang.equals("bn")){
                changeLangBtn.setText(" ভাষা পরিবর্তন করুন");
                themeText.setText("থিম পরিবর্তন করুন");
                darkText.setText("ডার্ক মোড");
                lightText.setText("লাইট মোড");
                ((MainActivity) getActivity()).toolBarText.setText(getResources().getText(R.string.menu_travel_setting));
                setNavigationItemsName();
            }

            FileOutputStream fileOutputStream = getActivity().openFileOutput("Settings.txt", Context.MODE_PRIVATE);
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
            FileInputStream fileInputStreamTc = getActivity().openFileInput("Settings.txt");
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
        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(lightSwitch.isChecked()) {
                    darkSwitch.setChecked(false);

                    if(temp.equals("light")){
                        Log.i("Theme ", "Light");

                    } else if(temp.equals("dark")){
                        temp = "light";

                        try {
                            FileOutputStream fileOutputStream = getActivity().openFileOutput("Setting_Theme.txt", Context.MODE_PRIVATE);
                            fileOutputStream.write(temp.getBytes());
                            fileOutputStream.close();

                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("Theme_EXTRA", "openMainActivity");
                            startActivity(intent);
                        }
                        catch (Exception e) {
                            Log.i("Error ", e.getMessage());
                        }
                    }

                }
            }
        });

        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(darkSwitch.isChecked()) {
                    lightSwitch.setChecked(false);

                    if(temp.equals("dark")){
                        Log.i("Theme ", "Dark");

                    } else if(temp.equals("light")){
                        temp = "dark";

                        try {
                            FileOutputStream fileOutputStream = getActivity().openFileOutput("Setting_Theme.txt", Context.MODE_PRIVATE);
                            fileOutputStream.write(temp.getBytes());
                            fileOutputStream.close();

                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("Theme_EXTRA", "openMainActivity");
                            startActivity(intent);
                        }
                        catch (Exception e) {
                            Log.i("Error ", e.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void setNavigationItemsName(){
        Menu menu = ((MainActivity) getActivity()).navigationView.getMenu();
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
    }
}
