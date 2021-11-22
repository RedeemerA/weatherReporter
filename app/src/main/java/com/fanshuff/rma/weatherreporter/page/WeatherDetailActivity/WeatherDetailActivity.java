package com.fanshuff.rma.weatherreporter.page.WeatherDetailActivity;

import static com.fanshuff.rma.weatherreporter.normal.value.SEARCH_CONTENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.fanshuff.rma.weatherreporter.R;
import com.fanshuff.rma.weatherreporter.entity.WeatherDetailInfo;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherDetailActivity extends AppCompatActivity {

    private TextView mTextViewBack, mTextViewProvince, mTextViewCity, mTextViewReportTime,
            mTextViewWeather, mTextViewTemp, mTextViewWindDirection, mTextViewWindPower,
            mTextViewHumidity;

    private WeatherDetailInfo weatherDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        initView();

        initData();

        initListener();
    }

    private void initListener() {
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        String cityCode ;
        Intent intent = getIntent();
        cityCode = intent.getStringExtra(SEARCH_CONTENT);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL("https://restapi.amap.com/v3/weather/weatherInfo?city="+cityCode+"&key=34380617adaf2c584aeaac7d17f5c0b8&extensions=base");
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in = connection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        int length = -1;
                        while((length=in.read(buf))!=-1)
                            baos.write(buf, 0, length);
                        parseJSONWithFastjson(new String(baos.toByteArray(), "UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
            }).start();



    }

private void parseJSONWithFastjson(String jsonData) {
        try {

            weatherDetailInfo = JSONObject.parseObject(jsonData, WeatherDetailInfo.class);
            setInformation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setInformation(){
            mTextViewCity.setText("城市(区)名称："+weatherDetailInfo.getLives().get(0).getCity());
            mTextViewReportTime.setText("发布时间："+weatherDetailInfo.getLives().get(0).getReporttime());
            mTextViewProvince.setText("省份名称："+weatherDetailInfo.getLives().get(0).getProvince());
            mTextViewWeather.setText("天气现象："+weatherDetailInfo.getLives().get(0).getWeather());
            mTextViewWindDirection.setText("风向描述："+weatherDetailInfo.getLives().get(0).getWinddirection());
            mTextViewWindPower.setText("风力级别："+weatherDetailInfo.getLives().get(0).getWindpower());
            mTextViewHumidity.setText("空气湿度："+weatherDetailInfo.getLives().get(0).getHumidity());

        }


private void initView() {
        mTextViewBack = findViewById(R.id.tv_weather_detail_back);
        mTextViewProvince = findViewById(R.id.tv_weather_detail_province);
        mTextViewCity = findViewById(R.id.tv_weather_detail_city);
        mTextViewReportTime = findViewById(R.id.tv_weather_detail_report_time);
        mTextViewWeather = findViewById(R.id.tv_weather_detail_weather);
        mTextViewTemp = findViewById(R.id.tv_weather_detail_temperature);
        mTextViewWindDirection = findViewById(R.id.tv_weather_detail_wind_direction);
        mTextViewWindPower = findViewById(R.id.tv_weather_detail_wind_power);
        mTextViewHumidity = findViewById(R.id.tv_weather_detail_humidity);

    }
}