package com.ncu.yzj.weather;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ncu.yzj.weather.model.Weather;
import com.ncu.yzj.weather.model.WeatherNow;
import com.ncu.yzj.weather.net.Manage;
import com.ncu.yzj.weather.net.RequestBuilder;

import java.io.InputStream;

/**
 * Created by 叶长建
 * on 2020/3/15 18:30
 */
public class MyService extends Service {

    public static final int CODE = 101;
    public static final int NOTIFICATION_ID = 1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService","onStartCommand");
        int ret = super.onStartCommand(intent, flags, startId);
        int flag = 0;
        if (intent != null){
            flag = intent.getIntExtra("flag",0);
            if ( flag > 0){
                initAlarm();
                new Thread(()->{
                    Manage.instance().init(getApplicationContext());
                    StringRequest request = new RequestBuilder()
                            .url(Constant.WEATHER_BASE_URL)
                            .param(Constant.VERSION_KEY, Constant.VERSION_VALUE_SEVEN)
                            .listener(response -> {
                                Weather weather = new Gson().fromJson(response,Weather.class);
                                startForeground(NOTIFICATION_ID,getNotification(getWeatherInfo(weather)));
                            })
                            .build();
                    Manage.instance().add(request);
                }).start();
                return ret;
            }
        }
        new Thread(()->{
            Manage.instance().init(getApplicationContext());
            StringRequest request = new RequestBuilder()
                    .url(Constant.WEATHER_BASE_URL)
                    .param(Constant.VERSION_KEY, Constant.VERSION_VALUE_SEVEN)
                    .listener(response -> {
                        Weather weather = new Gson().fromJson(response,Weather.class);
                        sendNotification(weather);
                    })
                    .build();
            Manage.instance().add(request);
        }).start();
        return ret;
    }
    private void sendNotification(Weather weather){
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager == null){
            Log.e("MyService", "NotificationManager is null");
            return;
        }
        manager.notify(NOTIFICATION_ID,getNotification(getWeatherInfo(weather)));
    }
    private Notification getNotification(String content){

        String channelId = "1";
        String channelName = "天气通知";
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager == null){
            Log.e("MyService", "NotificationManager is null");
            return null;
        }
        NotificationChannel channel =
                new NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_HIGH );
        manager.createNotificationChannel(channel);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplication(),CODE,intent,0);
        Notification.Builder nb = new Notification.Builder(getApplicationContext(),channelId)
                //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //设置通知标题
                .setContentTitle("天气提示")
                //设置通知内容
                .setContentText(content)
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                .setStyle(new Notification.BigTextStyle())
                //设置通知右侧的大图标
                //.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_notifiation_big))
                //设置点击通知时的响应事件
                .setContentIntent(pi);
        //设置删除通知时的响应事件
        //.setDeleteIntent(deletePendingIntent);
        Notification notification = nb.build();
        return notification;
    }
    private String getWeatherInfo(Weather weather){
        if (weather == null){
            return "暂无天气情况";
        }
        if (weather.getData() == null){
            return weather.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("当前城市:")
                .append(weather.getCity())
                .append(",\n");
        for (int i = 0; i < weather.getData().size(); i++) {
            Weather.DataBean data = weather.getData().get(i);
            sb.append(data.getDay())
                    .append(":")
                    .append(data.getWea())
                    .append(",最高温度:")
                    .append(data.getTem1())
                    .append(",最低温度:")
                    .append(data.getTem2())
                    .append("\n");
        }
        return sb.toString();
    }
    private void initAlarm(){
        AlarmManager alarmManager = getSystemService(AlarmManager.class);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),CODE,intent,0);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 5000,
                AlarmManager.INTERVAL_HALF_DAY,
                pendingIntent
        );
    }
}
