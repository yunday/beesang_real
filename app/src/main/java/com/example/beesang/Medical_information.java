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
import android.telephony.SmsManager;
import android.view.KeyEvent;
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
    private SharedPreferences sp2;
    SharedPreferences.Editor editor2;
    private SharedPreferences sp3;
    SharedPreferences.Editor editor3;

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

        sp2 = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor = sp2.edit();

        Input_FirstName.setText(sp2.getString("Input_FirstName", ""));
        Input_LastName.setText(sp2.getString("Input_LastName", ""));
        Input_Birth.setText(sp2.getString("Input_Birth", ""));
        Input_Gender.setSelection(sp2.getInt("Input_Gender", 0));
        Input_blood.setSelection(sp2.getInt("Input_blood", 0));
        Input_Tall.setText(sp2.getString("Input_Tall", ""));
        Input_Weight.setText(sp2.getString("Input_Weight", ""));
        Input_Allergy.setText(sp2.getString("Input_Allergy", ""));
        Input_Medicine.setText(sp2.getString("Input_Medicine", ""));
        Input_Other.setText(sp2.getString("Input_Other", ""));

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
                finish();
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
        sp3 = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor3 = sp3.edit();
        sp2 = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor2 = sp2.edit();
        sp = getSharedPreferences("myFile3", Activity.MODE_PRIVATE);
        editor = sp.edit();

        if (keycode == KeyEvent.KEYCODE_BACK) {
            Intent NewActivity = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(NewActivity);
        }

        if(keycode == KeyEvent.KEYCODE_VOLUME_DOWN){
            if(System.currentTimeMillis() - down_lastTimeBackPressed < 1500) {
                if(sp.getInt("radio_group1", 0) == R.id.rb_1_1) { //보호자가 아래키일 경우
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        String sms = "[SOS어플 알람]\n" +
                                "위급상황입니다.";
                        if(!sp3.getString("number1", "").equals("")){
                            smsManager.sendTextMessage(sp3.getString("number1", ""), null, sms, null, null);
                        }
                        if(!sp3.getString("number2", "").equals("")){
                            smsManager.sendTextMessage(sp3.getString("number2", ""), null, sms, null, null);
                        }
                        if(!sp3.getString("number3", "").equals("")){
                            smsManager.sendTextMessage(sp3.getString("number3", ""), null, sms, null, null);
                        }
                        Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else if(sp.getInt("radio_group1", 0)==R.id.rb_1_2) { //긴급이 아래키일 경우
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
                        smsManager.sendTextMessage(sp3.getString("number1", ""), null, sms, null, null);
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
        sp3 = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor3 = sp3.edit();
        sp2 = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor2 = sp2.edit();

        if(keycode == KeyEvent.KEYCODE_VOLUME_UP) {
            if(System.currentTimeMillis() - up_lastTimeBackPressed < 1500){
                if(sp.getInt("radio_group1", 0)==R.id.rb_1_2) { //보호자가 윗키일 경우
                    try{
                        SmsManager smsManager = SmsManager.getDefault();
                        String sms = "[SOS어플 알람]\n" +
                                "위급상황입니다.";
                        smsManager.sendTextMessage(sp3.getString("number1", ""), null, sms, null, null);
                        Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else if (sp.getInt("radio_group1", 0) == R.id.rb_1_1) {  //긴급이 윗키일 경우
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
                        smsManager.sendTextMessage("123456789", null, sms, null, null);
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
