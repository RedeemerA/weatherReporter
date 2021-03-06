package com.fanshuff.rma.weatherreporter.page.MainActivity;

import static com.fanshuff.rma.weatherreporter.database.DatabaseHelper.TABLE_NAME;
import static com.fanshuff.rma.weatherreporter.normal.value.ABCODE;
import static com.fanshuff.rma.weatherreporter.normal.value.SEARCH_CONTENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanshuff.rma.weatherreporter.R;
import com.fanshuff.rma.weatherreporter.database.DBLikeDao;
import com.fanshuff.rma.weatherreporter.database.DBdao;
import com.fanshuff.rma.weatherreporter.database.DatabaseHelper;
import com.fanshuff.rma.weatherreporter.entity.CityCodeInfo;
import com.fanshuff.rma.weatherreporter.page.WeatherDetailActivity.WeatherDetailActivity;

import org.apache.log4j.chainsaw.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewLike, mListViewCityAndProvince;

    private TextView mTextViewSearchButton, mTextViewChangeButton, mTextViewListTitle, mTextViewRefresh;

    private EditText mEditTextSearch;

    private MainCodeListAdapter mainCityAdapter, mainProvinceAdapter, mainLikeAdapter;

    private List<CityCodeInfo> cityListInfo = new ArrayList<>(),
            provinceListInfo = new ArrayList<>(), LikeListInfo = new ArrayList<>(),
            currentList = new ArrayList<>();

    private boolean isProvince = true;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initListener();
    }


    private void initListener() {

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
                intent.putExtra(SEARCH_CONTENT, currentList.get(position).getAdcode());
                startActivity(intent);
            }
        };
        mListViewCityAndProvince.setOnItemClickListener(listener);

        AdapterView.OnItemClickListener listener2 = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
                intent.putExtra(SEARCH_CONTENT, LikeListInfo.get(position).getAdcode());
                startActivity(intent);
            }
        };
        mListViewLike.setOnItemClickListener(listener2);


        mTextViewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextSearch.getText() == null||mEditTextSearch.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
//                    DBdao dBdao = new DBdao(MainActivity.this);
//                    String adcode = dBdao.getAdcode(mEditTextSearch.getText().toString() );
//                    intent.putExtra(SEARCH_CONTENT, adcode);
                    intent.putExtra(SEARCH_CONTENT, mEditTextSearch.getText().toString() );
                    startActivity(intent);
                }
            }
        });

        mTextViewChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProvince){
                    mListViewCityAndProvince.setAdapter(mainCityAdapter);
                    isProvince = false;
                    mTextViewListTitle.setText("?????????????????????");
                    currentList = cityListInfo;

                }else{
                    mListViewCityAndProvince.setAdapter(mainProvinceAdapter);
                    isProvince = true;
                    mTextViewListTitle.setText("??????????????????");
                    currentList = provinceListInfo;
                }
            }
        });

        mTextViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBLikeDao dbLikeDao = new DBLikeDao(MainActivity.this);
                LikeListInfo = dbLikeDao.getAllPoints();
                mainLikeAdapter = new MainCodeListAdapter(LikeListInfo, MainActivity.this);
                mListViewLike.setAdapter(mainLikeAdapter);

            }
        });

    }

    private void initData() {

        importSheet();
        //????????????????????????
        mainCityAdapter = new MainCodeListAdapter(cityListInfo, MainActivity.this);
        mainProvinceAdapter = new MainCodeListAdapter(provinceListInfo, MainActivity.this);
        mListViewCityAndProvince.setAdapter(mainProvinceAdapter);
        isProvince = true;
        currentList = provinceListInfo;

        DBLikeDao dbLikeDao = new DBLikeDao(MainActivity.this);
        LikeListInfo = dbLikeDao.getAllPoints();
        mainLikeAdapter = new MainCodeListAdapter(LikeListInfo, MainActivity.this);
        mListViewLike.setAdapter(mainLikeAdapter);
    }

    private void initView() {
        mTextViewChangeButton = findViewById(R.id.tv_main_list_change_button);
        mTextViewSearchButton = findViewById(R.id.tv_main_search);
        mTextViewListTitle = findViewById(R.id.tv_main_city_or_province_list);
        mTextViewRefresh = findViewById(R.id.tv_main_like_refresh);

        mListViewLike = findViewById(R.id.lv_main_like_list);
        mListViewCityAndProvince = findViewById(R.id.lv_main_city_or_province_list);

        mEditTextSearch = findViewById(R.id.et_main_search);

        dbHelper = new DatabaseHelper(MainActivity.this);
    }

    //???????????????????????????SQLite?????????
    private void importSheet() {
        try {
            // 1
            InputStream is = getResources().getAssets().open("amap.xls");
            // 2
            Workbook book = Workbook.getWorkbook(is);
            // 3
            Sheet sheet = book.getSheet(0);
            // 4
            DBdao dBdao = new DBdao(MainActivity.this);
//???????????? ????????????
//            if (dBdao.tabbleIsExist(TABLE_NAME)){
//                for (int j = 1; j < sheet.getRows(); j++){
//                    dBdao.addInformation(sheet.getCell(0,j).getContents(), sheet.getCell(1,j).getContents(), sheet.getCell(2,j).getContents());
//                }
//            }else{
//                Toast.makeText(MainActivity.this, "???????????????ERROR", Toast.LENGTH_SHORT).show();
//
//            }
            for (int j = 1; j < sheet.getRows(); j++){
                CityCodeInfo cityCodeInfo = new CityCodeInfo();
                cityCodeInfo.setChineseName(sheet.getCell(0,j).getContents());
                cityCodeInfo.setAdcode(sheet.getCell(1,j).getContents());
                cityCodeInfo.setCitycode(sheet.getCell(2,j).getContents());
                int i = Integer.valueOf(cityCodeInfo.getAdcode());
                if (cityCodeInfo.getCitycode().equals("") || i % 10000 == 0){
                    if (i != 100000 && i != 900000){
                        provinceListInfo.add(cityCodeInfo);
                    }
                }
                if (i != 100000 && i != 900000){
                    cityListInfo.add(cityCodeInfo);
                }
            }
            book.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

}