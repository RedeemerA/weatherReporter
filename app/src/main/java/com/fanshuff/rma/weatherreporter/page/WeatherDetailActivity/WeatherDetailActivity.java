package com.fanshuff.rma.weatherreporter.page.WeatherDetailActivity;

import static com.fanshuff.rma.weatherreporter.normal.value.SEARCH_CONTENT;
import static com.fanshuff.rma.weatherreporter.normal.value.UPDATE_INFO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.fanshuff.rma.weatherreporter.R;
import com.fanshuff.rma.weatherreporter.database.DBBufferDao;
import com.fanshuff.rma.weatherreporter.database.DBLikeDao;
import com.fanshuff.rma.weatherreporter.entity.LiveInfo;
import com.fanshuff.rma.weatherreporter.entity.WeatherDetailInfo;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherDetailActivity extends AppCompatActivity {

    private TextView mTextViewBack, mTextViewProvince, mTextViewCity, mTextViewReportTime,
            mTextViewWeather, mTextViewTemp, mTextViewWindDirection, mTextViewWindPower,
            mTextViewHumidity, mTextViewRefresh,mTextViewLike, mTextViewHaveLike;

    private WeatherDetailInfo weatherDetailInfo = new WeatherDetailInfo();
    private LiveInfo liveInfo;

    private   String cityCode;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_INFO) {
                setInformation();
                DBLikeDao dbLikeDao = new DBLikeDao(WeatherDetailActivity.this);
                int id = dbLikeDao.getId(weatherDetailInfo.getLives().get(0).getCity());

                if (id > 0){
                    mTextViewLike.setVisibility(View.GONE);
                    mTextViewHaveLike.setVisibility(View.VISIBLE);
                }else{
                    mTextViewLike.setVisibility(View.VISIBLE);
                    mTextViewHaveLike.setVisibility(View.GONE);
                }

                DBBufferDao dbBufferDao = new DBBufferDao(WeatherDetailActivity.this);
                dbBufferDao.addBuffer(weatherDetailInfo.getLives().get(0).getAdcode(),
                        weatherDetailInfo.getLives().get(0).getChinesename(),
                        weatherDetailInfo.getLives().get(0).getProvince(),
                        weatherDetailInfo.getLives().get(0).getCity(),
                        weatherDetailInfo.getLives().get(0).getWeather(),
                        weatherDetailInfo.getLives().get(0).getTemperature(),
                        weatherDetailInfo.getLives().get(0).getWinddirection(),
                        weatherDetailInfo.getLives().get(0).getWindpower(),
                        weatherDetailInfo.getLives().get(0).getHumidity());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        initView();

        initData();

        initListener();


    }

    private void initListener() {
        mTextViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBLikeDao dbLikeDao = new DBLikeDao(WeatherDetailActivity.this);
                dbLikeDao.addInformation(weatherDetailInfo.getLives().get(0).getCity(), weatherDetailInfo.getLives().get(0).getAdcode());
                mTextViewLike.setVisibility(View.GONE);
                mTextViewHaveLike.setVisibility(View.VISIBLE);
            }
        });

        mTextViewHaveLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBLikeDao dbLikeDao = new DBLikeDao(WeatherDetailActivity.this);
                dbLikeDao.deleteInformation(dbLikeDao.getId(weatherDetailInfo.getLives().get(0).getCity()));
                mTextViewLike.setVisibility(View.VISIBLE);
                mTextViewHaveLike.setVisibility(View.GONE);
            }
        });

        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTextViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
    }

    private void initData() {



        Intent intent = getIntent();
        cityCode = intent.getStringExtra(SEARCH_CONTENT);

        DBBufferDao dbBufferDao = new DBBufferDao(WeatherDetailActivity.this);
        liveInfo = dbBufferDao.getBuffer(cityCode);
        if (liveInfo != null){
            liveInfo.setReporttime("上次缓存");
            List<LiveInfo> liveInfoList  = new ArrayList<>();
            liveInfoList.add(liveInfo);
            weatherDetailInfo.setLives(liveInfoList);
            callChangeUi();
        }else{
            refreshData();
        }

    }

private void parseJSONWithFastjson(String jsonData) {
        try {

            weatherDetailInfo = JSONObject.parseObject(jsonData, WeatherDetailInfo.class);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void callChangeUi() {
        try {

            Message msg = new Message();
            msg.what = UPDATE_INFO;
            handler.sendMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private void refreshData(){
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
                        callChangeUi();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }

                    }
                }
            }).start();

        };

        private void setInformation(){

            mTextViewCity.setText("城市(区)名称："+weatherDetailInfo.getLives().get(0).getCity());
            mTextViewReportTime.setText("发布时间："+weatherDetailInfo.getLives().get(0).getReporttime());
            mTextViewTemp.setText("实时温度："+weatherDetailInfo.getLives().get(0).getTemperature());
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

        mTextViewRefresh = findViewById(R.id.tv_weather_detail_refresh);
        mTextViewLike = findViewById(R.id.tv_weather_detail_like);
        mTextViewHaveLike = findViewById(R.id.tv_weather_detail_have_like);
    }
}