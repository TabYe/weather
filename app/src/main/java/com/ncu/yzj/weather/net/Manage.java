package com.ncu.yzj.weather.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by 叶长建
 * on 2020/3/15 14:35
 */
public class Manage {
    private static class ManageHolder{
        private static Manage INSTANCE = new Manage();
    }
    public static Manage instance(){
        return ManageHolder.INSTANCE;
    }
    private RequestQueue mQueue;
    private Map<String,String> mParams;
    private String mBaseUrl;
    public void init(Context context){
        mQueue = Volley.newRequestQueue(context);
    }
    public StringRequest add(StringRequest request){
        return (StringRequest) mQueue.add(request);
    }
    public void add(StringRequest... requests){
        if (requests.length > 0){
            for (StringRequest request:requests
                 ) {
                mQueue.add(request);
            }
        }
    }
}
