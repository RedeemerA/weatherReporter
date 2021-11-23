package com.fanshuff.rma.weatherreporter.database;

import static com.fanshuff.rma.weatherreporter.database.DatabaseHelper.TABLE_NAME;
import static com.fanshuff.rma.weatherreporter.database.DatabaseHelper.TABLE_NAME_LIKE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fanshuff.rma.weatherreporter.entity.CityCodeInfo;

import java.util.ArrayList;
import java.util.List;

public class DBLikeDao {

    private DatabaseHelper databaseHelper;
    private  Context context;
    private SQLiteDatabase db ;

    public DBLikeDao(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    //添加数据
    public void addInformation(String chineseName, String adcode){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// insert into Orders(Id, CustomName, OrderPrice, Country) values (7, "Jne", 700, "China");
        ContentValues contentValues = new ContentValues();

        contentValues.put("chinesename", chineseName);
        contentValues.put("adcode", adcode);
        db.insertOrThrow(TABLE_NAME_LIKE, null, contentValues);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //删除数据
    public void deleteInformation(int id){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// delete from Orders where Id = ?
        db.delete(TABLE_NAME_LIKE, "id = ?", new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //修改数据
    public void modifyInformation(int id, String chineseName, int adcode){
        db = databaseHelper.getWritableDatabase();
        db.beginTransaction();

// update Orders set OrderPrice = 800 where Id = 6
        ContentValues cv = new ContentValues();

        cv.put("chineseName", chineseName);
        cv.put("adcode", adcode);

        db.update(TABLE_NAME_LIKE,
                cv,
                "id = ?",
                new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //遍历输出数据库的数列表
    public List<CityCodeInfo> getAllPoints() {
        String sql = "select * from CityLike";
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
                            .getColumnIndex("adcode");

            cityCodeInfo.setId(cursor.getInt(id));
            cityCodeInfo.setChineseName(cursor.getString(chineseName));
            cityCodeInfo.setAdcode(cursor.getString(adcode));
            cityCodeList.add(cityCodeInfo);
        }
        return cityCodeList;
    }

    //查找根据其中一个数据，查找同行的另一个
    public String getAdcode(String chinesename){
        //蠢菜代码如下
        List<CityCodeInfo> cityCodeInfoList = getAllPoints();
        String adcode = null;
        for (int i = 0; i < cityCodeInfoList.size(); i++){
            if (cityCodeInfoList.get(i).getChineseName().equals(chinesename)){
                adcode = cityCodeInfoList.get(i).getAdcode();
            }
        }
        return adcode;
    }

    //查找根据其中一个数据，查找同行的另一个
    public int getId(String chinesename){
        List<CityCodeInfo> cityCodeInfoList = getAllPoints();
        int id = 0;
        for (int i = 0; i < cityCodeInfoList.size(); i++){
            if (cityCodeInfoList.get(i).getChineseName().equals(chinesename)){
                id = cityCodeInfoList.get(i).getId();
            }
        }
        return id;
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
