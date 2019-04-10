package com.example.huyentran.huyentrancuoiky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.huyentran.huyentrancuoiky.model.CatalogModel;
import com.example.huyentran.huyentrancuoiky.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MySQLite extends SQLiteOpenHelper {
    private SQLiteDatabase mDB;
    private static final int VERSION = 1;
    private static final String DBNAME = "MANGE_MONEY";
    private static final  String ID = "ID";

    private static final String TABLE_CATALOG = "CATAELOG";
    private static final String CATALOG_NAME = "NAME";

    private static final String TABLE_USER = "USER";
    private static final String USER_NAME = "USERNAME";
    private static final String USER_PASSWORD = "PASSWORD";
    private static final String USER_MONEY = "MONEY";

    private static final String TABLE_SPENDING = "SPENDING";
    private static final String SPENDING_MONEY = "MONEY";
    private static final String SPENDING_CATALOG_ID = "CATALOGID";
    private static final String SPENDING_DATETIME = "DATETIME";
    private static final String SPENDING_DESCRIPTION = "DESCRIPTION";
    private static final String SPENDING_USERID = "USERID";

    public MySQLite(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String mUserQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,%s TEXT, %s INTEGER,%s TEXT)",TABLE_USER,ID,USER_NAME,USER_MONEY,USER_PASSWORD);
        String mCatalogQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,%s TEXT)",TABLE_CATALOG,ID,CATALOG_NAME);
        String mSpendingQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,%s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s INTEGER)"
                                        ,TABLE_SPENDING,ID,SPENDING_MONEY,SPENDING_CATALOG_ID,SPENDING_DATETIME,SPENDING_DESCRIPTION,SPENDING_USERID);
        sqLiteDatabase.execSQL(mUserQuery);
        sqLiteDatabase.execSQL(mCatalogQuery);
        sqLiteDatabase.execSQL(mSpendingQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void OpenDB(){
        mDB = getWritableDatabase();
    }
    public void CloseDB(){
        if (mDB != null){
            mDB.close();
        }
    }

    public long addUser(String mName,String mPass){
        ContentValues values = new ContentValues();
        values.put(USER_NAME,mName);
        values.put(USER_PASSWORD,mPass);
        values.put(USER_MONEY,0);
        long i = mDB.insert(TABLE_USER,null,values);
        return i;
    }

    public long updateUserMoney(int Id,int Money){
        String mQuery = String.format("%s = %d",ID,Id);
        ContentValues values = new ContentValues();
        values.put(USER_MONEY,Money);
        long i = mDB.update(TABLE_USER,values,mQuery,null);
        return i;
    }

    public UserModel validateLogin(String UserName, String Pass){
        String mQuery = "SELECT * FROM "+TABLE_USER+" WHERE "+USER_NAME+" like '"+UserName+"' AND "+USER_PASSWORD+" like '"+Pass+"' ";
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToFirst()){
            UserModel model = new UserModel();
            model.setId(cursor.getInt(0));
            model.setUserName(cursor.getString(1));
            model.setMoney(cursor.getInt(2));
            return model;
        }
        else{
            return  null;
        }
    }

    public int countUser(){
        String mQuery = String.format("SELECT count(*) FROM %s",TABLE_USER);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToNext()){
            int i = cursor.getInt(0);
            return i;
        }
        return 0;
    }

    public int getMoneyByUserIḍ̣(int id){
        String mQuery = String.format("SELECT %s FROM %s WHERE %s = %d",USER_MONEY,TABLE_USER,ID,id);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToNext()){
            int i = cursor.getInt(0);
            return i;
        }
        return 0;
    }

    public List<CatalogModel> getAllCatalog(){
        List<CatalogModel> models = new ArrayList<>();
        String mQuery = "SELECT * FROM " + TABLE_CATALOG;
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToFirst()){
            for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                CatalogModel model = new CatalogModel();
                model.setId(cursor.getInt(0));
                model.setName(cursor.getString(1));
                models.add(model);
            }
            cursor.close();
            return models;
        }
        return null;
    }

    public List<String> getCatalogNamẹ(){
        List<String> models = new ArrayList<>();
        String mQuery = String.format("SELECT %s FROM %s",CATALOG_NAME,TABLE_CATALOG);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToFirst()){
            for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                models.add(cursor.getString(0));
            }
            cursor.close();
            return models;
        }
        return  null;
    }

    public int countCatalog(){
        String mQuery = String.format("SELECT count(*) FROM %s",TABLE_CATALOG);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToNext()){
            int i = cursor.getInt(0);
            return i;
        }
        return 0;
    }

    public long addCatalog(String mName){
        ContentValues values = new ContentValues();
        values.put(CATALOG_NAME,mName);
        return mDB.insert(TABLE_CATALOG,null,values);
    }

    public int getCatalogIdByName(String mName){
        String mQuery = String.format("SELECT %s FROM %s WHERE %s like '%s'",ID,TABLE_CATALOG,CATALOG_NAME,mName);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return 0;
    }

    public String getCatalogNameById(int Id){
        String mQuery = String.format("SELECT %s FROM %s WHERE %s like '%s'",CATALOG_NAME,TABLE_CATALOG,ID,Id);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToFirst()){
            return cursor.getString(0);
        }
        return "";
    }

    public List<SpendingModel> getSpendingByType(int type,String datetime,int userid){
        List<SpendingModel> models = new ArrayList<>();
        String mQuery = "";
        if (type == 0)
            mQuery = String.format("SELECT * FROM %s WHERE %s < '%s' AND %s = %d",TABLE_SPENDING,SPENDING_DATETIME,datetime,SPENDING_USERID,userid);
        else
            mQuery = String.format("SELECT * FROM %s WHERE (%s > '%s' OR %s = '%s') AND %s = %d",TABLE_SPENDING,SPENDING_DATETIME,datetime,SPENDING_DATETIME,datetime,SPENDING_USERID,userid);
        Cursor cursor = mDB.rawQuery(mQuery,null);
        if (cursor.moveToFirst()){
            for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                SpendingModel model = new SpendingModel();
                model.setId(cursor.getInt(0));
                model.setMoney(cursor.getInt(1));
                model.setCatalogId(cursor.getInt(2));
                model.setDate(cursor.getString(3));
                model.setDescription(cursor.getString(4));
                model.setUserId(cursor.getInt(5));
                models.add(model);
            }
            cursor.close();
            return models;
        }
        return  new ArrayList<>();
    }

    public long addSpending(SpendingModel model){
        ContentValues values = new ContentValues();
        values.put(SPENDING_MONEY,model.getMoney());
        values.put(SPENDING_CATALOG_ID,model.getCatalogId());
        values.put(SPENDING_DATETIME,model.getDate());
        values.put(SPENDING_DESCRIPTION,model.getDescription());
        values.put(SPENDING_USERID,model.getUserId());
        long i = mDB.insert(TABLE_SPENDING,null,values);
        return i;
    }
}
