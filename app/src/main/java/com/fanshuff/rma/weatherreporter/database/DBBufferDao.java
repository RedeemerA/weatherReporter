package com.fanshuff.rma.weatherreporter.database;

import static com.fanshuff.rma.weatherreporter.database.DatabaseHelper.TABLE_NAME;
import static com.fanshuff.rma.weatherreporter.database.DatabaseHelper.TABLE_NAME_BUFFER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fanshuff.rma.weatherreporter.entity.CityCodeInfo;
import com.fanshuff.rma.weatherreporter.entity.LiveInfo;

import java.util.ArrayList;
import java.util.List;

public class DBBufferDao {

    private DatabaseHelper databaseHelper;
    private  Context context;
    private SQLiteDatabase db ;

    public DBBufferDao(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public void addBuffer(String adcode,String chineseName, String province, String city,String weather, String temperature, String winddirection, String windpower, String humidity){
        List<LiveInfo> liveInfoList = getAllPoints();
        boolean haveModify = false;
        for (int i = 0 ; i < liveInfoList.size(); i++){
            if (liveInfoList.get(i).getCity().equals(chineseName)){
                modifyInformation(liveInfoList.get(0).getId(),adcode, chineseName, province, city, weather, temperature, winddirection, windpower, humidity);
                haveModify = true;
                break;
            }
        }
        //如果缓存区没有 没执行修改 就直接添加
        if (!haveModify){
            addInformation(adcode, chineseName, province, city, weather, temperature, winddirection, windpower, humidity);

        }
    }

    public LiveInfo getBuffer(String citycode) {
        //实际上是adcode
        LiveInfo liveInfo = new LiveInfo();
        List<LiveInfo> liveInfoList = new ArrayList<>();
        liveInfoList = getAllPoints();
        for (int i = 0 ; i < liveInfoList.size(); i++){
            if (liveInfoList.get(i).getAdcode().equals(citycode)){
               liveInfo = liveInfoList.get(i);
                return liveInfo;
            }
        }
        return null;
    }

    //添加数据
    public void addInformation(String adcode,String chineseName, String province, String city,String weather, String temperature, String winddirection, String windpower, String humidity){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();
// insert into Orders(Id, CustomName, OrderPrice, Country) values (7, "Jne", 700, "China");
        ContentValues contentValues = new ContentValues();

        contentValues.put("adcode", adcode);
        contentValues.put("chinesename", chineseName);
        contentValues.put("province", province);
        contentValues.put("city", city);
        contentValues.put("weather", weather);
        contentValues.put("temperature", temperature);
        contentValues.put("winddirection", winddirection);
        contentValues.put("windpower", windpower);
        contentValues.put("humidity", humidity);

        db.insertOrThrow(TABLE_NAME_BUFFER, null, contentValues);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //删除数据
    public void deleteInformation(int id){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// delete from Orders where Id = ?
        db.delete(TABLE_NAME_BUFFER, "id = ?", new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //修改数据
    public void modifyInformation(int id, String adcode ,String chineseName, String province, String city,String weather, String temperature, String winddirection, String windpower, String humidity){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();
// update Orders set OrderPrice = 800 where Id = 6
        ContentValues contentValues = new ContentValues();

        contentValues.put("adcode", adcode);
        contentValues.put("chinesename", chineseName);
        contentValues.put("province", province);
        contentValues.put("city", city);
        contentValues.put("weather", weather);
        contentValues.put("temperature", temperature);
        contentValues.put("winddirection", winddirection);
        contentValues.put("windpower", windpower);
        contentValues.put("humidity", humidity);

        db.update(TABLE_NAME_BUFFER,
                contentValues,
                "id = ?",
                new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //清空表内容
    public void clearTable(){
        db = databaseHelper.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME_BUFFER);
        db.close();
    }


    //遍历数据库 输出对应内容
    public LiveInfo getAllFromAdcode(String inputAdcode ) {
        String sql = "select * from CityBuffer";
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        LiveInfo liveInfo = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            liveInfo = new LiveInfo();

            int id = cursor.getColumnIndex("id"),
                    chineseName = cursor
                    .getColumnIndex("chinesename"),
                    adcode = cursor
                            .getColumnIndex("adcode");
            if (inputAdcode.equals(adcode)){
                int province = cursor
                        .getColumnIndex("adcode"),
                        city = cursor
                                .getColumnIndex("adcode"),
                        weather = cursor
                                .getColumnIndex("adcode"),
                        temperature = cursor
                                .getColumnIndex("adcode"),
                        winddirection = cursor
                                .getColumnIndex("adcode"),
                        windpower = cursor
                                .getColumnIndex("adcode"),
                        humidity = cursor
                                .getColumnIndex("citycode");

                liveInfo.setId(cursor.getInt(id));
                liveInfo.setAdcode(cursor.getString(adcode));
                liveInfo.setChinesename(cursor.getString(chineseName));
                liveInfo.setProvince(cursor.getString(province));
                liveInfo.setCity(cursor.getString(city));
                liveInfo.setWeather(cursor.getString(weather));
                liveInfo.setTemperature(cursor.getString(temperature));
                liveInfo.setWinddirection(cursor.getString(winddirection));
                liveInfo.setWindpower(cursor.getString(windpower));
                liveInfo.setHumidity(cursor.getString(humidity));


            }


        }
        return liveInfo;
    }

    //查找根据其中一个数据，查找同行的另一个数据
    //输入中文名，查找adcode
    public String getAdcode(String chinesename){
        //蠢菜代码如下
        List<LiveInfo> liveInfoList = getAllPoints();
        String adcode = null;
        for (int i = 0; i < liveInfoList.size(); i++){
            if (liveInfoList.get(i).getCity().equals(chinesename)){
                adcode = liveInfoList.get(i).getAdcode();
            }
        }
        return adcode;


    }

    //遍历输出数据库的数列表
    public List<LiveInfo> getAllPoints() {
        String sql = "select * from CityBuffer";
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<LiveInfo> liveInfoList = new ArrayList<>();
        LiveInfo liveInfo = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            liveInfo = new LiveInfo();
            int id = cursor.getColumnIndex("id"),
                    chineseName = cursor
                            .getColumnIndex("chinesename"),
                    adcode = cursor
                            .getColumnIndex("adcode"),
                    province = cursor
                            .getColumnIndex("province"),
                    city = cursor
                            .getColumnIndex("city"),
                    weather = cursor
                            .getColumnIndex("weather"),
                    temperature = cursor
                            .getColumnIndex("temperature"),
                    winddirection = cursor
                            .getColumnIndex("winddirection"),
                    windpower = cursor
                            .getColumnIndex("windpower"),
                    humidity = cursor
                            .getColumnIndex("humidity");

            liveInfo.setId(cursor.getInt(id));
            liveInfo.setAdcode(cursor.getString(adcode));
            liveInfo.setChinesename(cursor.getString(chineseName));
            liveInfo.setProvince(cursor.getString(province));
            liveInfo.setCity(cursor.getString(city));
            liveInfo.setWeather(cursor.getString(weather));
            liveInfo.setTemperature(cursor.getString(temperature));
            liveInfo.setWinddirection(cursor.getString(winddirection));
            liveInfo.setWindpower(cursor.getString(windpower));
            liveInfo.setHumidity(cursor.getString(humidity));
            liveInfoList.add(liveInfo);
        }
        return liveInfoList;
    }

    public boolean tabbleIsExist(String tableName){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        boolean result = false;
        if(tableName == null){
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
}
