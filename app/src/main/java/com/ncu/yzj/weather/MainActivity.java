package com.ncu.yzj.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ncu.yzj.weather.databinding.ActivityMainBinding;
import com.ncu.yzj.weather.model.Weather;
import com.ncu.yzj.weather.model.WeatherNow;
import com.ncu.yzj.weather.net.Manage;
import com.ncu.yzj.weather.net.RequestBuilder;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    public static final int ACTIVITY_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        StringRequest now = new RequestBuilder()
                .url(Constant.WEATHER_BASE_URL)
                .param(Constant.VERSION_KEY, Constant.VERSION_VALUE_NOW)
                .listener(response -> {
                    Log.i("now", response);
                    WeatherNow weatherNow = new Gson().fromJson(response, WeatherNow.class);
                    Log.i("weatherNow", weatherNow.toString());
                    mBinding.setData(weatherNow);
                })
                .build();

        Manage.instance().add(now);

        initRecyclerView();

        initAlarm();

    }

    private void initRecyclerView() {
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        CommonRecyclerAdapter<Weather.DataBean> adapter = new CommonRecyclerAdapter<>();
        mBinding.rvList.setAdapter(adapter);
        adapter.setLayoutId(R.layout.weather_item);
        adapter.setVariableId(BR.data);
        StringRequest seven = new RequestBuilder()
                .url(Constant.WEATHER_BASE_URL)
                .param(Constant.VERSION_KEY, Constant.VERSION_VALUE_SEVEN)
                .listener(response -> {
                    Log.i("seven", response);
                    Weather weather = new Gson().fromJson(response, Weather.class);
                    Log.i("weather", weather.toString());
                    adapter.setList(weather.getData());
                })
                .build();
        Manage.instance().add(seven);
    }

    private void initAlarm() {
        Intent intent = new Intent();
        intent.putExtra("flag",1);
        intent.setClass(getApplicationContext(), MyService.class);
        startForegroundService(intent);
//        AlarmManager alarmManager = getSystemService(AlarmManager.class);
//        Intent intent = new Intent();
//        intent.setClass(getApplicationContext(),MyService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),ACTIVITY_CODE,intent,0);
//        alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() + 5000,
//                1000*5,
//                pendingIntent
//                );

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
