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
import android.widget.ImageButton;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    EditText et_1, et_2, et_3;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    String name1, name2, name3;
    String number1, number2, number3;

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
            }
        });

        sp = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor = sp.edit();

        tv_name1.setText(sp.getString("name1", "보호자1"));
        tv_name2.setText(sp.getString("name2", "보호자2"));
        tv_name3.setText(sp.getString("name3", "보호자3"));
        et_1.setText(sp.getString("number1", ""));
        et_2.setText(sp.getString("number2", ""));
        et_3.setText(sp.getString("number3", ""));
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
                startActivity(NewActivity);
                break;
        }
        return true;
    }


}