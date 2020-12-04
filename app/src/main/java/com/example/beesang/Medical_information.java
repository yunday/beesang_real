package com.example.beesang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Medical_information extends AppCompatActivity {
    private SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final EditText Input_FirstName = findViewById(R.id.Input_FirstName);
        final EditText Input_LastName = findViewById(R.id.Input_LastName);
        final EditText Input_Birth = findViewById(R.id.Input_Birth);
        final Spinner Input_Gender = findViewById(R.id.Input_Gender);
        final Spinner Input_blood = findViewById(R.id.Input_blood);
        final EditText Input_Tall = findViewById(R.id.Input_Tall);
        final EditText Input_Weight = findViewById(R.id.Input_Weight);
        final EditText Input_Allergy = findViewById(R.id.Input_Allergy);
        final EditText Input_Medicine = findViewById(R.id.Input_Medicine);
        final EditText Input_Other = findViewById(R.id.Input_Other);
        Button btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editor.putString("Input_FirstName", Input_FirstName.getText().toString());
                editor.putString("Input_LastName", Input_LastName.getText().toString());
                editor.putString("Input_Birth", Input_Birth.getText().toString());
                editor.putInt("Input_Gender", Input_Gender.getSelectedItemPosition());
                editor.putInt("Input_blood", Input_blood.getSelectedItemPosition());
                editor.putString("Input_Tall", Input_Tall.getText().toString());
                editor.putString("Input_Weight", Input_Weight.getText().toString());
                editor.putString("Input_Allergy", Input_Allergy.getText().toString());
                editor.putString("Input_Medicine", Input_Medicine.getText().toString());
                editor.putString("Input_Other", Input_Other.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        sp = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor = sp.edit();

        Input_FirstName.setText(sp.getString("Input_FirstName", ""));
        Input_LastName.setText(sp.getString("Input_LastName", ""));
        Input_Birth.setText(sp.getString("Input_Birth", ""));
        Input_Gender.setSelection(sp.getInt("Input_Gender", 0));
        Input_blood.setSelection(sp.getInt("Input_blood", 0));
        Input_Tall.setText(sp.getString("Input_Tall", ""));
        Input_Weight.setText(sp.getString("Input_Weight", ""));
        Input_Allergy.setText(sp.getString("Input_Allergy", ""));
        Input_Medicine.setText(sp.getString("Input_Medicine", ""));
        Input_Other.setText(sp.getString("Input_Other", ""));

    }

    // 밑에는 툴바에 홈버튼 만드는 함수들
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu:
                Intent NewActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(NewActivity);
                break;
        }
        return true;
    }
}
