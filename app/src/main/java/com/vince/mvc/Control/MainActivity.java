package com.vince.mvc.Control;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vince.mvc.Model.HttpUtil;
import com.vince.mvc.Model.Utility;
import com.vince.mvc.Model.Weather;
import com.vince.mvc.R;
import com.vince.mvc.View.WeatherView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements WeatherView.Listener{
    //ViewGroup

    private Weather weather;
    private WeatherView weatherView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherView = new WeatherView(this,this);
    }


    @Override
    public void notifly(String address) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                weather = Utility.handleWeatherResponse(response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        weatherView.update(weather);
                    }
                });
            }
        });
    }

    @Override
    public void notiflyBack(String address) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String imageCode = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("me",imageCode);
                        weatherView.updateBackGround(imageCode);
                    }
                });
            }
        });
    }

}

