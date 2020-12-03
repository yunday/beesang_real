package com.example.beesang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
    String number_tmp = "";

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
        final EditText et_1 = findViewById(R.id.et_1);
        final EditText et_2 = findViewById(R.id.et_2);
        final EditText et_3 = findViewById(R.id.et_3);

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
                et_1.setText(number_tmp);
            }
        });
        btn_contact2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
                et_2.setText(number_tmp);
            }
        });
        btn_contact3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
                et_3.setText(number_tmp);
            }
        });
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
                number_tmp = cursor.getString(1);
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