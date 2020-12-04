package com.example.beesang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
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
    private SharedPreferences sp2;
    SharedPreferences.Editor editor2;

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
        sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor = sp.edit();
        sp2 = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor = sp2.edit();

        if(keycode == KeyEvent.KEYCODE_VOLUME_DOWN){
            if(System.currentTimeMillis() - down_lastTimeBackPressed < 1500) {
                if(guardian == 0) { //보호자가 아래키일 경우
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        String sms = "[SOS어플 알람]\n" +
                                "위급상황입니다.";
                        smsManager.sendTextMessage(sp.getString("number1", ""), null, sms, null, null);
                        smsManager.sendTextMessage(sp.getString("number2", ""), null, sms, null, null);
                        smsManager.sendTextMessage(sp.getString("number3", ""), null, sms, null, null);
                        Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else if(emergency == 0) { //긴급이 아래키일 경우
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        String gender = "";
                        String blood = "";
                        if (sp2.getInt("Input_Gender", 0) == 1){
                            gender = "여자";
                        }else if(sp2.getInt("Input_Gender", 0) == 2){
                            gender = "남자";
                        }
                        switch (sp2.getInt("Input_blood", 0)){
                            case 1: blood = "A+"; break;
                            case 2: blood = "A-"; break;
                            case 3: blood = "B+"; break;
                            case 4: blood = "B-"; break;
                            case 5: blood = "AB+"; break;
                            case 6: blood = "AB-"; break;
                            case 7: blood = "O+"; break;
                            case 9: blood = "O-"; break;
                        }
                        String sms = "[응급 문자]\n" +
                                "이름 : "+sp2.getString("Input_FirstName", "")+sp2.getString("Input_LastName", "")+"\n" +
                                "생년월일 : "+sp2.getString("Input_Birth", "")+"\n" +
                                "성별 : "+gender+"\n" +
                                "혈액형 : "+blood+"\n" +
                                "키 : "+sp2.getString("Input_Tall", "")+"\n" +
                                "몸무게 : "+sp2.getString("Input_Weight", "")+"\n" +
                                "알레르기 : "+sp2.getString("Input_Allergy", "")+"\n" +
                                "복용 중인 약 : "+sp2.getString("Input_Medicine", "")+"\n" +
                                "기타 : "+sp2.getString("Input_Other", "위급 상황 시 가족에게 먼저 연락해주세요.");
                        smsManager.sendTextMessage(sp.getString("119", ""), null, sms, null, null);
                        Toast.makeText(this, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

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
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        String sms = "[SOS어플 알람]\n" +
                                "위급상황입니다.";
                        smsManager.sendTextMessage(sp.getString("number1", ""), null, sms, null, null);
                        Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else if (emergency == 1) {  //긴급이 윗키일 경우
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        String gender = "";
                        String blood = "";
                        if (sp2.getInt("Input_Gender", 0) == 1){
                            gender = "여자";
                        }else if(sp2.getInt("Input_Gender", 0) == 2){
                            gender = "남자";
                        }
                        switch (sp2.getInt("Input_blood", 0)){
                            case 1: blood = "A+"; break;
                            case 2: blood = "A-"; break;
                            case 3: blood = "B+"; break;
                            case 4: blood = "B-"; break;
                            case 5: blood = "AB+"; break;
                            case 6: blood = "AB-"; break;
                            case 7: blood = "O+"; break;
                            case 9: blood = "O-"; break;
                        }
                        String sms = "[응급 문자]\n" +
                                "이름 : "+sp2.getString("Input_FirstName", "")+sp2.getString("Input_LastName", "")+"\n" +
                                "생년월일 : "+sp2.getString("Input_Birth", "")+"\n" +
                                "성별 : "+gender+"\n" +
                                "혈액형 : "+blood+"\n" +
                                "키 : "+sp2.getString("Input_Tall", "")+"\n" +
                                "몸무게 : "+sp2.getString("Input_Weight", "")+"\n" +
                                "알레르기 : "+sp2.getString("Input_Allergy", "")+"\n" +
                                "복용 중인 약 : "+sp2.getString("Input_Medicine", "")+"\n" +
                                "기타 : "+sp2.getString("Input_Other", "위급 상황 시 가족에게 먼저 연락해주세요.");
                        smsManager.sendTextMessage(sp.getString("number1", ""), null, sms, null, null);
                        Toast.makeText(this, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
            up_lastTimeBackPressed = System.currentTimeMillis();
        }
        return true; //super.onKeyUp(keycode, event);
    }


}