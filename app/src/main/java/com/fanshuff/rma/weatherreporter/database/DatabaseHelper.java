package com.fanshuff.rma.weatherreporter.database;


import static com.fanshuff.rma.weatherreporter.normal.value.CITY_DATABASE;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * auther: RMA
 * Date: 2021/11/4
 * Time:18:42
 * Des:
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Citys";
    public static final String TABLE_NAME_BUFFER = "CityBuffer";
    public static final String TABLE_NAME_LIKE = "CityLike";

    public static final String CREATE_CITY = "create table Citys (" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "chinesename text," +
            "adcode text," +
            "citycode text)";

    public static final String CREATE_CITY_LIKE = "create table CityLike (" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "chinesename text," +
            "adcode text," +
            "citycode text)";

    public static final String CREATE_CITY_BUFFER = "create table CityBuffer (" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "adcode text," +
            "chinesename text," +
            "province text," +
            "city text," +
            "weather text," +
            "temperature text," +
            "winddirection text," +
            "windpower text," +
            "humidity text)";

    public DatabaseHelper(Context context){
        super(context, CITY_DATABASE, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_CITY_BUFFER);
        db.execSQL(CREATE_CITY_LIKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

