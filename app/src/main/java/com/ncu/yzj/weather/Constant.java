package com.ncu.yzj.weather;

/**
 * Created by 叶长建
 * on 2020/3/15 14:44
 */
public interface Constant {
    String WEATHER_HOST = "https://www.tianqiapi.com/api";
    String APP_SECRET_KEY = "appsecret";
    String APP_SECRET_VALUE = "KSDKhY7O";
    String APP_ID_KEY = "appid";
    String APP_ID_VALUE = "87237962";
    String VERSION_KEY = "version";
    String VERSION_VALUE_NOW = "v6";
    String VERSION_VALUE_SEVEN = "v1";
    String WEATHER_BASE_URL =
            WEATHER_HOST
                    + "?"
                    + APP_ID_KEY
                    + "="
                    +APP_ID_VALUE
                    + "&"
                    + APP_SECRET_KEY
                    + "="
                    +APP_SECRET_VALUE ;
}
