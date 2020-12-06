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
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private final String ACTION_BTN = "ButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        Intent intent = new Intent(context, AppWidget.class).setAction(ACTION_BTN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.button2, pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    //private SharedPreferences sp3;
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(action.equals(ACTION_BTN)){
            //버튼 클릭 결과를 로그로 확인합니다.
            Log.d("이벤트클릭 테스트 ","클릭!");
            //버튼 클릭 결과를 위젯 위의 텍스트뷰를 변경함으로 확인합니다.
            //
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            ComponentName componentName = new ComponentName(context, AppWidget.class);

            SharedPreferences sp3 = context.getSharedPreferences("myFile", Activity.MODE_PRIVATE);

            //remoteViews.setTextViewText(R.id.widget,"문자 전송 완료!");
            appWidgetManager.updateAppWidget(componentName, remoteViews);
            //

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
                Toast.makeText(context, "보호자에게 메시지를 전송하였습니다. ", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(context, "메세지 전송에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }

    }


}