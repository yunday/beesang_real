package com.example.beesang;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class emergency_widget extends AppWidgetProvider {

    private final String ACTION_BTN = "ButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.emergency_widget);

        Intent intent = new Intent(context, emergency_widget.class).setAction(ACTION_BTN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.button2, pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (action.equals(ACTION_BTN)) {
            //버튼 클릭 결과를 로그로 확인합니다.
            Log.d("이벤트클릭 테스트 ", "클릭!");
            //버튼 클릭 결과를 위젯 위의 텍스트뷰를 변경함으로 확인합니다.
            //
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.emergency_widget);
            ComponentName componentName = new ComponentName(context, emergency_widget.class);

            SharedPreferences sp2 = context.getSharedPreferences("myFile", Activity.MODE_PRIVATE);

            //remoteViews.setTextViewText(R.id.widget,"문자 전송 완료!");
            appWidgetManager.updateAppWidget(componentName, remoteViews);
            //

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
                smsManager.sendTextMessage("01038625579", null, sms, null, null);
                Toast.makeText(context, "긴급 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(context, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }
}