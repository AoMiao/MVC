package com.vince.mvc.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vince.mvc.Model.Forecast;
import com.vince.mvc.Model.Weather;
import com.vince.mvc.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2017/3/29.
 */

public class WeatherView {
    private Context context;

    private TextView title_city;
    private TextView update_time;
    private TextView tmp_text;
    private TextView weather_info_text;
    private LinearLayout forecast_layout;
    private TextView aqi_text;
    private TextView pm25_text;
    private TextView comfort_text;
    private TextView carwash_text;
    private TextView sport_text;
    private TextView dress_text;
    private ImageView now_png;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public interface Listener{
        public void notifly(String address);
        //public void update(Weather weather);
    }


    public WeatherView(Activity ac,Listener listener){
        context = ac;
        title_city = (TextView) ac.findViewById(R.id.title_city);
        update_time = (TextView) ac.findViewById(R.id.update_time);
        tmp_text = (TextView) ac.findViewById(R.id.tmp_text);
        weather_info_text = (TextView) ac.findViewById(R.id.weather_info_text);
        forecast_layout = (LinearLayout) ac.findViewById(R.id.forecast_layout);
        aqi_text = (TextView) ac.findViewById(R.id.aqi_text);
        pm25_text = (TextView) ac.findViewById(R.id.pm25_text);
        comfort_text = (TextView) ac.findViewById(R.id.comfort_text);
        carwash_text = (TextView) ac.findViewById(R.id.carwash_text);
        sport_text = (TextView) ac.findViewById(R.id.sport_text);
        dress_text = (TextView) ac.findViewById(R.id.dress_text);
        now_png = (ImageView) ac.findViewById(R.id.now_png);
        listener.notifly("https://free-api.heweather.com/v5/weather?city=广州&key=bc0418b57b2d4918819d3974ac1285d9");


    }


    public void update(Weather weather){
        title_city.setText(weather.basic.cityName);
        update_time.setText(weather.basic.update.updateTime.split(" ")[1] + "发布");
        tmp_text.setText(weather.now.temperature + "°C");
        weather_info_text.setText(weather.now.weatherMessage.info);
        Glide.with(context).load("http://files.heweather.com/cond_icon/" + weather.now.weatherMessage.code + ".png").into(now_png);
        if (weather.aqi != null) {//有的城市天气没有AQI指数
            aqi_text.setText(weather.aqi.city.aqi);
            pm25_text.setText(weather.aqi.city.pm25);
        }

        comfort_text.setText("舒适度：" + weather.suggestion.comfort.ComfortSuggestion);
        carwash_text.setText("洗车建议：" + weather.suggestion.carWash.CarWashSuggestion);
        sport_text.setText("运动建议：" + weather.suggestion.sport.SportSuggestion);
        dress_text.setText("穿衣建议：" + weather.suggestion.dress.DressSuggestion);

        forecast_layout.removeAllViews();//先把所有的子项清除
        for (Forecast forecast : weather.forecastList) {
            //这里的forecast_layout是指forecast.xml文件里的LinearLayout布局
            View view = LayoutInflater.from(context).inflate(R.layout.forecast_item, forecast_layout, false);//加载子项布局
            TextView forecast_date_text = (TextView) view.findViewById(R.id.date_text);
            TextView forecast_info_text = (TextView) view.findViewById(R.id.info_text);
            TextView forecast_max_text = (TextView) view.findViewById(R.id.max_text);
            TextView forecast_min_text = (TextView) view.findViewById(R.id.min_text);
            ImageView png = (ImageView) view.findViewById(R.id.png);
            Glide.with(context).load("http://files.heweather.com/cond_icon/" + forecast.weatherMessage.png + ".png").into(png);
            String today = sdf.format(new Date());
            if (forecast.date.equals(today)) {
                forecast_date_text.setText("今天");
            } else {
                forecast_date_text.setText(forecast.date.split("-")[1] + "月" + forecast.date.split("-")[2] + "日");
            }
            forecast_info_text.setText(forecast.weatherMessage.info);
            forecast_max_text.setText(forecast.temperature.max + "°C");
            forecast_min_text.setText(forecast.temperature.min + "°C");
            //Log.d("ne",forecast.weatherMessage.png);
            forecast_layout.addView(view);
        }
    }
}
