package com.example.beesang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    String address;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    private SharedPreferences sp2;
    SharedPreferences.Editor editor2;
    private SharedPreferences sp3;
    SharedPreferences.Editor editor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        Button guide_btn = findViewById(R.id.btn_guide);
        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), Setting_guide.class);
                finish();
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.btn_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), ButtonActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Button contact = findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), ContactActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Button medical = findViewById(R.id.medical);
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), Medical_information.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //볼륨키 이벤트
    private long down_lastTimeBackPressed;
    private long up_lastTimeBackPressed;

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        sp3 = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor3 = sp3.edit();
        sp2 = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor2 = sp2.edit();
        sp = getSharedPreferences("myFile3", Activity.MODE_PRIVATE);
        editor = sp.edit();

        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        address = getCurrentAddress(latitude, longitude);

        if (keycode == KeyEvent.KEYCODE_BACK) {
            //Intent NewActivity = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            //startActivity(NewActivity);
        }

        if (keycode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (System.currentTimeMillis() - down_lastTimeBackPressed < 1500) {
                if (sp.getInt("radio_group1", 0) == R.id.rb_1_1) { //보호자가 아래키일 경우
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        String sms = "[SOS어플 알람]\n" +
                                "위급상황입니다.\n"+
                                "위치 : " + address;
                        if (!sp3.getString("number1", "").equals("")) {
                            smsManager.sendTextMessage(sp3.getString("number1", ""), null, sms, null, null);
                        }
                        if (!sp3.getString("number2", "").equals("")) {
                            smsManager.sendTextMessage(sp3.getString("number2", ""), null, sms, null, null);
                        }
                        if (!sp3.getString("number3", "").equals("")) {
                            smsManager.sendTextMessage(sp3.getString("number3", ""), null, sms, null, null);
                        }
                        Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else if (sp.getInt("radio_group1", 0) == R.id.rb_1_2) { //긴급이 아래키일 경우
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        String gender = "";
                        String blood = "";
                        if (sp2.getInt("Input_Gender", 0) == 1) {
                            gender = "여자";
                        } else if (sp2.getInt("Input_Gender", 0) == 2) {
                            gender = "남자";
                        }
                        switch (sp2.getInt("Input_blood", 0)) {
                            case 1:
                                blood = "A+";
                                break;
                            case 2:
                                blood = "A-";
                                break;
                            case 3:
                                blood = "B+";
                                break;
                            case 4:
                                blood = "B-";
                                break;
                            case 5:
                                blood = "AB+";
                                break;
                            case 6:
                                blood = "AB-";
                                break;
                            case 7:
                                blood = "O+";
                                break;
                            case 9:
                                blood = "O-";
                                break;
                        }
                        String sms1 = "[응급 문자]\n" +
                                "이름 : " + sp2.getString("Input_FirstName", "") + sp2.getString("Input_LastName", "") + "\n" +
                                "생년월일 : " + sp2.getString("Input_Birth", "") + "\n" +
                                "성별 : " + gender + "\n" +
                                "혈액형 : " + blood + "\n";
                        String sms2 = "키 : " + sp2.getString("Input_Tall", "") + "\n" +
                                "몸무게 : " + sp2.getString("Input_Weight", "") + "\n" +
                                "알레르기 : " + sp2.getString("Input_Allergy", "") + "\n" +
                                "복용 중인 약 : " + sp2.getString("Input_Medicine", "") + "\n" +
                                "기타 : " + sp2.getString("Input_Other", "위급 상황 시 가족에게 먼저 연락해주세요.") + "\n"+
                                "위치 : " + address;

                        smsManager.sendTextMessage(sp3.getString("010-3862-5579", ""), null, sms1, null, null);
                        smsManager.sendTextMessage(sp3.getString("010-3862-5579", ""), null, sms2, null, null);
                        Toast.makeText(this, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }
            down_lastTimeBackPressed = System.currentTimeMillis();

        }

        return true; //super.onKeyDown(keycode, event);
    }

    public boolean onKeyUp(int keycode, KeyEvent event) {
        sp3 = getSharedPreferences("myFile", Activity.MODE_PRIVATE);
        editor3 = sp3.edit();
        sp2 = getSharedPreferences("myFile2", Activity.MODE_PRIVATE);
        editor2 = sp2.edit();

        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        address = getCurrentAddress(latitude, longitude);

        if (keycode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (System.currentTimeMillis() - up_lastTimeBackPressed < 1500) {
                if (sp.getInt("radio_group1", 0) == R.id.rb_1_2) { //보호자가 윗키일 경우
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        String sms = "[SOS어플 알람]\n" +
                                "위급상황입니다.\n"+
                                "위치 : " + address;
                        if (!sp3.getString("number1", "").equals("")) {
                            smsManager.sendTextMessage(sp3.getString("number1", ""), null, sms, null, null);
                        }
                        if (!sp3.getString("number2", "").equals("")) {
                            smsManager.sendTextMessage(sp3.getString("number2", ""), null, sms, null, null);
                        }
                        if (!sp3.getString("number3", "").equals("")) {
                            smsManager.sendTextMessage(sp3.getString("number3", ""), null, sms, null, null);
                        }
                        Toast.makeText(this, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else if (sp.getInt("radio_group1", 0) == R.id.rb_1_1) {  //긴급이 윗키일 경우
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        String gender = "";
                        String blood = "";
                        if (sp2.getInt("Input_Gender", 0) == 1) {
                            gender = "여자";
                        } else if (sp2.getInt("Input_Gender", 0) == 2) {
                            gender = "남자";
                        }
                        switch (sp2.getInt("Input_blood", 0)) {
                            case 1:
                                blood = "A+";
                                break;
                            case 2:
                                blood = "A-";
                                break;
                            case 3:
                                blood = "B+";
                                break;
                            case 4:
                                blood = "B-";
                                break;
                            case 5:
                                blood = "AB+";
                                break;
                            case 6:
                                blood = "AB-";
                                break;
                            case 7:
                                blood = "O+";
                                break;
                            case 9:
                                blood = "O-";
                                break;
                        }
                        String sms1 = "[응급 문자]\n" +
                                "이름 : " + sp2.getString("Input_FirstName", "") + sp2.getString("Input_LastName", "") + "\n" +
                                "생년월일 : " + sp2.getString("Input_Birth", "") + "\n" +
                                "성별 : " + gender + "\n" +
                                "혈액형 : " + blood + "\n";
                        String sms2 = "키 : " + sp2.getString("Input_Tall", "") + "\n" +
                                "몸무게 : " + sp2.getString("Input_Weight", "") + "\n" +
                                "알레르기 : " + sp2.getString("Input_Allergy", "") + "\n" +
                                "복용 중인 약 : " + sp2.getString("Input_Medicine", "") + "\n" +
                                "기타 : " + sp2.getString("Input_Other", "위급 상황 시 가족에게 먼저 연락해주세요.") + "\n"+
                                "위치 : " + address;

                        smsManager.sendTextMessage("010-3862-5579", null, sms1, null, null);
                        smsManager.sendTextMessage("010-3862-5579", null, sms2, null, null);
                        Toast.makeText(this, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
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