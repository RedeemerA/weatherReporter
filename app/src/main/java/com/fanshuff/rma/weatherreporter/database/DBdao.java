package com.fanshuff.rma.weatherreporter.database;

import static com.fanshuff.rma.weatherreporter.database.DatabaseHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fanshuff.rma.weatherreporter.entity.CityCodeInfo;

import java.util.ArrayList;
import java.util.List;

public class DBdao {

    private DatabaseHelper databaseHelper;
    private  Context context;
    private SQLiteDatabase db ;

    public DBdao(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    //添加数据
    public void addInformation(String chineseName, String adcode, String citycode){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// insert into Orders(Id, CustomName, OrderPrice, Country) values (7, "Jne", 700, "China");
        ContentValues contentValues = new ContentValues();

        contentValues.put("chinesename", chineseName);
        contentValues.put("adcode", adcode);
        contentValues.put("citycode", citycode);
        db.insertOrThrow(TABLE_NAME, null, contentValues);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //删除数据
    public void deleteInformation(int id){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// delete from Orders where Id = ?
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //修改数据
    public void modifyInformation(int id, String chineseName, int adcode, int citycode){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// update Orders set OrderPrice = 800 where Id = 6
        ContentValues cv = new ContentValues();

        cv.put("chineseName", chineseName);
        cv.put("adcode", adcode);
        cv.put("citycode", citycode);

        db.update(TABLE_NAME,
                cv,
                "id = ?",
                new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //遍历输出数据库的数列表
    public List<CityCodeInfo> getAllPoints() {
        String sql = "select * from City";
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<CityCodeInfo> cityCodeList = new ArrayList<>();
        CityCodeInfo cityCodeInfo = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            cityCodeInfo = new CityCodeInfo();
            int id = cursor.getColumnIndex("id"),
                    chineseName = cursor
                    .getColumnIndex("chinesename"),
                    adcode = cursor
                            .getColumnIndex("adcode"),
                    citycode = cursor
                            .getColumnIndex("citycode");


            cityCodeInfo.setId(cursor.getInt(id));
            cityCodeInfo.setChineseName(cursor.getString(chineseName));
            cityCodeInfo.setAdcode(cursor.getString(adcode));
            cityCodeInfo.setCitycode(cursor.getString(citycode));
            cityCodeList.add(cityCodeInfo);
        }
        return cityCodeList;
    }

    //查找根据其中一个数据，查找同行的另一个
    public String getAdcode(String chinesename){
        //蠢菜代码如下
        CityCodeInfo cityCodeInfo = new CityCodeInfo();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,"chinesename"+"=?", new String[]{chinesename},null,null,null);
        int adcode = cursor.getColumnIndex("adcode");

        cityCodeInfo.setAdcode(String.valueOf(adcode));

        return cityCodeInfo.getAdcode();


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
