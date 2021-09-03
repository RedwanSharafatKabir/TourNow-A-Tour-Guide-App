package com.example.tournow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NetworkErrorActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);

        textView = (TextView) findViewById(R.id.bar_text);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            textView.setText(bundle.getString("name"));
        }

        AlertDialog alertDialog = new AlertDialog
                .Builder(this)
                .setIcon(R.drawable.ic_alert)
                .setTitle("Internet Connection Alert")
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        onBackPressed();
                    }
                })
                .show();

    }
}
