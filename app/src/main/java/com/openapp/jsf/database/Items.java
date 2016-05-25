package com.openapp.jsf.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.openapp.jsf.domain.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Items extends SQLiteOpenHelper implements Serializable{
    public static final String TABLE_ITEMS = "items";
    public static final String DATABASE_NAME = "items.db";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_READ = "read";
    public static final String COLUMN_TIMESTAMP = "TIMESTAMP";
    private final String DEFAULT_TIMESTAMP="2014-2-2";


    public static final int READ = 1; //sqlite doesnt have a boolean type, instead it stores it as integer.
    public static final int NOT_READ = 0;

    public static final int TYPE_JOB= 0, TYPE_EVENT = 1,TYPE_NEWS = 2;


    private SQLiteDatabase db;
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ITEMS + "(" + COLUMN_CONTENT +" text, " + COLUMN_TYPE +" numeric, "+ COLUMN_READ + " BOOLEAN, "+COLUMN_TIMESTAMP+" DATETIME);";

    public Items(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void setRead(Item item){
        checkDB();
        System.out.println("update "+TABLE_ITEMS+ "set "+COLUMN_READ+" = "+READ+" where "+COLUMN_TIMESTAMP+"= '"+item.getTimestamp()+"' and "+COLUMN_TYPE+"="+item.getType());
        db.execSQL("update "+TABLE_ITEMS+ " set "+COLUMN_READ+" = "+READ+" where "+COLUMN_TIMESTAMP+"= '"+item.getTimestamp()+"' and "+COLUMN_TYPE+"="+item.getType());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addItem(String content, int type, String timestamp){
        checkDB();

        SQLiteStatement stmt = db.compileStatement("insert into " + TABLE_ITEMS + " values ( ? , " + type + ","+NOT_READ+ ", ?)"
        );
        stmt.bindString(1, content);
        stmt.bindString(2, timestamp);
        stmt.execute();
    }

    public void setDB(SQLiteDatabase db){
        this.db = db;
    }

    public void clearDB(){
        checkDB();
        db.execSQL("delete from " + TABLE_ITEMS);
    }

    public ArrayList[] getItems(){
        ArrayList[] items = new ArrayList[3];
        items[Items.TYPE_JOB] = new ArrayList<JobPost>();
        items[Items.TYPE_NEWS] = new ArrayList<News>();
        items[Items.TYPE_EVENT] = new ArrayList<Event>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ITEMS +" order by "+COLUMN_TIMESTAMP+" desc", null);
        cursor.moveToFirst();
        int contentColumnIndex = cursor.getColumnIndex(Items.COLUMN_CONTENT);
        int readColumnIndex = cursor.getColumnIndex(Items.COLUMN_READ);
        int typeColumnIndex = cursor.getColumnIndex(Items.COLUMN_TYPE);
        int timestampColumnIndex = cursor.getColumnIndex(Items.COLUMN_TIMESTAMP);
        while(!cursor.isAfterLast()){
            try {
                switch(cursor.getInt(typeColumnIndex)){
                    case Items.TYPE_JOB:
                        items[Items.TYPE_JOB].add(new JobPost(new JSONObject(cursor.getString(contentColumnIndex)), cursor.getInt(readColumnIndex) == READ, cursor.getString(timestampColumnIndex)));
                        break;
                    case Items.TYPE_EVENT:
                        items[Items.TYPE_EVENT].add(new Event(new JSONObject(cursor.getString(contentColumnIndex)), cursor.getInt(readColumnIndex) == READ, cursor.getString(timestampColumnIndex)));
                        break;
                    case Items.TYPE_NEWS:
                        items[Items.TYPE_NEWS].add(new News(new JSONObject(cursor.getString(contentColumnIndex)), cursor.getInt(readColumnIndex) == READ, cursor.getString(timestampColumnIndex)));
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    private void checkDB() {
        if(db == null){
            Log.d("db is null", "db is null");
            throw new NullPointerException("db should have been set before adding a jobpost");
        }
    }

    public String getLatestTimestamp() {
        Cursor cursor = db.rawQuery("select max("+COLUMN_TIMESTAMP+") from "+TABLE_ITEMS,null );
        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return DEFAULT_TIMESTAMP;
    }
}
