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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {
    EditText et_1, et_2, et_3;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    private SharedPreferences sp2;
    SharedPreferences.Editor editor2;
    private SharedPreferences sp3;
    SharedPreferences.Editor editor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final ImageButton btn_edit1 = findViewById(R.id.btn_edit1);
        final ImageButton btn_edit2 = findViewById(R.id.btn_edit2);
        final ImageButton btn_edit3 = findViewById(R.id.btn_edit3);
        final ImageButton btn_save1 = findViewById(R.id.btn_save1);
        final ImageButton btn_save2 = findViewById(R.id.btn_save2);
        final ImageButton btn_save3 = findViewById(R.id.btn_save3);
        Button btn_contact1 = findViewById(R.id.btn_contact1);
        Button btn_contact2 = findViewById(R.id.btn_contact2);
        Button btn_contact3 = findViewById(R.id.btn_contact3);
        final TextView tv_name1 = findViewById(R.id.tv_name1);
        final TextView tv_name2 = findViewById(R.id.tv_name2);
        final TextView tv_name3 = findViewById(R.id.tv_name3);
        final EditText et_name1  = findViewById(R.id.et_name1);
        final EditText et_name2  = findViewById(R.id.et_name2);
        final EditText et_name3  = findViewById(R.id.et_name3);
        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        et_3 = findViewById(R.id.et_3);
        Button btn_save_real = findViewById(R.id.btn_save);

        btn_edit1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tv_name1.setVisibility(view.INVISIBLE);
                et_name1.setVisibility(view.VISIBLE);
                btn_edit1.setVisibility(view.INVISIBLE);
                btn_save1.setVisibility(view.VISIBLE);
            }
        });
        btn_edit2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tv_name2.setVisibility(view.INVISIBLE);
                et_name2.setVisibility(view.VISIBLE);
                btn_edit2.setVisibility(view.INVISIBLE);
                btn_save2.setVisibility(view.VISIBLE);
            }
        });
        btn_edit3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tv_name3.setVisibility(view.INVISIBLE);
                et_name3.setVisibility(view.VISIBLE);
                btn_edit3.setVisibility(view.INVISIBLE);
                btn_save3.setVisibility(view.VISIBLE);
            }
        });
        btn_save1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String strText = et_name1.getText().toString();
                tv_name1.setText(strText);
                tv_name1.setVisibility(view.VISIBLE);
                et_name1.setVisibility(view.INVISIBLE);
                btn_save1.setVisibility(view.INVISIBLE);
                btn_edit1.setVisibility(view.VISIBLE);
            }
        });
        btn_save2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String strText = et_name2.getText().toString();
                tv_name2.setText(strText);
                tv_name2.setVisibility(view.VISIBLE);
                et_name2.setVisibility(view.INVISIBLE);
                btn_save2.setVisibility(view.INVISIBLE);
                btn_edit2.setVisibility(view.VISIBLE);
            }
        });
        btn_save3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String strText = et_name3.getText().toString();
                tv_name3.setText(strText);
                tv_name3.setVisibility(view.VISIBLE);
                et_name3.setVisibility(view.INVISIBLE);
                btn_save3.setVisibility(view.INVISIBLE);
                btn_edit3.setVisibility(view.VISIBLE);
            }
        });
        btn_contact1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        btn_contact2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        btn_contact3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        btn_save_real.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editor.putString("name1", tv_name1.getText().toString());
                editor.putString("name2", tv_name2.getText().toString());
                editor.putString("name3", tv_name3.getText().toString());
                editor.putString("number1", et_1.getText().toString());
                editor.putString("number2", et_2.getText().toString());
                editor.putString("number3", et_3.getText().toString());
                editor.apply();

                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        sp3 = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor = sp3.edit();

        tv_name1.setText(sp3.getString("name1", "보호자1"));
        tv_name2.setText(sp3.getString("name2", "보호자2"));
        tv_name3.setText(sp3.getString("name3", "보호자3"));
        et_1.setText(sp3.getString("number1", ""));
        et_2.setText(sp3.getString("number2", ""));
        et_3.setText(sp3.getString("number3", ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Cursor cursor = null;

            if (data != null) {
                cursor = getContentResolver().query(data.getData(),
                        new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null); }
            if (cursor != null) {
                cursor.moveToFirst();
                if(requestCode==0)
                    et_1.setText(cursor.getString(1));
                else if(requestCode==1)
                    et_2.setText(cursor.getString(1));
                else if(requestCode==2)
                    et_3.setText(cursor.getString(1));
                cursor.close();
            }
        }
        super.onActivityResult(requestCode, resultCode, data); }

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