package com.example.beesang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ButtonActivity extends AppCompatActivity {
    private SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final RadioButton rb_1_1 = findViewById(R.id.rb_1_1);
        final RadioButton rb_1_2 = findViewById(R.id.rb_1_2);
        final RadioButton rb_2_1 = findViewById(R.id.rb_2_1);
        final RadioButton rb_2_2 = findViewById(R.id.rb_2_2);
        final RadioGroup radio_group1 = findViewById(R.id.radio_group1);
        final RadioGroup radio_group2 = findViewById(R.id.radio_group2);

        rb_1_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rb_2_2.setChecked(true);
            }
        });
        rb_1_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rb_2_1.setChecked(true);
            }
        });
        rb_2_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rb_1_2.setChecked(true);
            }
        });
        rb_2_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rb_1_1.setChecked(true);
            }
        });

        Button btn_save = findViewById(R.id.button);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editor.putInt("radio_group1", radio_group1.getCheckedRadioButtonId());
                editor.putInt("radio_group2", radio_group2.getCheckedRadioButtonId());
                editor.apply();
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                //버튼 조절 상황
                if(sp.getInt("radio_group1", 0) == R.id.rb_1_1){
                    guardian = 0;   //보호자가 아래키
                    emergency = 1;  //긴급이 윗키
                } else if (sp.getInt("radio_group1", 0)==R.id.rb_1_2){
                    guardian = 1;   //보호자가 윗키
                    emergency = 0;  //긴급 전화가 아래키
                }
            }
        });

        sp = getSharedPreferences("myFile3", Activity.MODE_PRIVATE);
        editor = sp.edit();

//        RadioButton savedCheckedRadioButton1 = (RadioButton)radio_group1.getChildAt(sp.getInt("radio_group1", 0));
//        savedCheckedRadioButton1.setChecked(true);
//        RadioButton savedCheckedRadioButton2 = (RadioButton)radio_group1.getChildAt(sp.getInt("radio_group2", 0));
//        savedCheckedRadioButton2.setChecked(true);

        if (sp.getInt("radio_group1", 0)==R.id.rb_1_1){
            rb_1_1.setChecked(true);
            rb_2_2.setChecked(true);
        }
        else if (sp.getInt("radio_group1", 0)==R.id.rb_1_2){
            rb_1_2.setChecked(true);
            rb_2_1.setChecked(true);
        }


    }
    int guardian = 2;   //초기상태는 2
    int emergency = 2;

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


    //볼륨키 이벤트
    private long down_lastTimeBackPressed;
    private long up_lastTimeBackPressed;
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_VOLUME_DOWN){
            if(System.currentTimeMillis() - down_lastTimeBackPressed < 1500) {
                if(guardian == 0) { //보호자가 아래키일 경우
                    /// 여기 문자 넣을 코드 넣으면 되삼 ~~
                    Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                }
                else if(emergency == 0) { //긴급이 아래키일 경우
                    /// 여기 문자 넣을 코드 넣으면 되삼 ~~
                    Toast.makeText(this, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                }
            }
            down_lastTimeBackPressed = System.currentTimeMillis();
        }
        return true; //super.onKeyDown(keycode, event);
    }

    public boolean onKeyUp(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_VOLUME_UP) {
            if(System.currentTimeMillis() - up_lastTimeBackPressed < 1500){
                if(guardian == 1) { //보호자가 윗키일 경우
                    /// 여기 문자 넣을 코드 넣으면 되삼 ~~
                    Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                }
                else if (emergency == 1) {  //긴급이 윗키일 경우
                    /// 여기 문자 넣을 코드 넣으면 되삼 ~~
                    Toast.makeText(this, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                }
            }
            up_lastTimeBackPressed = System.currentTimeMillis();
        }
        return true; //super.onKeyUp(keycode, event);
    }


}