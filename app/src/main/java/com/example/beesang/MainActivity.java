package com.example.beesang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int SMS_SEND_PERMISSON=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(
                getApplicationContext(), AuthorityActivity.class);
        startActivity(intent);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}