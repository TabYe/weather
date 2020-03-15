package com.ncu.yzj.weather.net;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.ncu.yzj.weather.Constant.WEATHER_HOST;

/**
 * Created by 叶长建
 * on 2020/3/15 14:55
 */
public class RequestBuilder {
    private Map<String,String> mParams;
    private String mUrl = WEATHER_HOST;
    private Response.Listener<String> mListener;
    private Response.ErrorListener mErrListener;
    private int mMethod = StringRequest.Method.GET;
    public RequestBuilder url(String url){
        this.mUrl = url;
        return this;
    }
    public RequestBuilder method(String method){
        if (method.toUpperCase().equals("POST")){
            mMethod = StringRequest.Method.POST;
        }
        return this;
    }
    public RequestBuilder param(String key,String value){
        if (mParams == null){
            mParams = new HashMap<>();
        }
        mParams.put(key, value);
        return this;
    }
    public RequestBuilder params(Map<String,String> params){
        if (mParams == null){
            mParams = new HashMap<>();
        }
        mParams.putAll(params);
        return this;
    }
    public RequestBuilder listener(Response.Listener<String> listener){
        this.mListener = listener;
        return this;
    }
    public RequestBuilder error(Response.ErrorListener listener){
        this.mErrListener = listener;
        return this;
    }
    public StringRequest build(){
        if ("".equals(mUrl)){
            mUrl = WEATHER_HOST;
        }
        if (mParams != null && mParams.size() >0){
            StringBuilder stringBuilder =  new StringBuilder(mUrl);
            if (!mUrl.contains("?")){
                stringBuilder.append("?");
            }
            if (mUrl.contains("&")|| mUrl.charAt(mUrl.length()-1)!='&'){
                stringBuilder.append("&");
            }
            for ( Map.Entry<String,String> entry:mParams.entrySet()
                 ) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            mUrl = stringBuilder.toString();
        }
        if (mListener == null){
            mListener = response -> Log.i(mUrl,response);
        }
        if (mErrListener == null){
            mErrListener = Throwable::printStackTrace;
        }
        return new StringRequest(mMethod, mUrl, mListener,mErrListener);
    }
}
