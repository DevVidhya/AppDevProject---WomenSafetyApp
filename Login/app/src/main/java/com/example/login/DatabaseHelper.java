package com.example.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String col1 = "ID";
    public static final String col2 = "USERNAME";
    public static final String col3 = "EMAIL";
    public static final String col4 = "PASSWORD";

    Activity context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, EMAIL TEXT, PASSWORD TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, name);
        contentValues.put(col3, email);
        contentValues.put(col4, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getPassword(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from "+TABLE_NAME+ " where EMAIL == ? ", new String[] {email} );
        return res;
    }

    public Cursor getData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
       // String name = "Vidhya";
        Cursor res =  db.rawQuery( "select * from "+ TABLE_NAME + " where EMAIL == ? ", new String[] {email} );
        return res;
    }
    public boolean deleteValue(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ TABLE_NAME + " where EMAIL == ? ", new String[] {email} ); // student_table WHERE email='"+email+"'", null);
        if(c.moveToFirst())
        {
            db.execSQL("DELETE FROM student_table WHERE EMAIL='"+email+"'");


            return true;


        }
        else
        {

            return false;

        }

    }


    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id,name,email, password FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(col1)));
            user.put("name",cursor.getString(cursor.getColumnIndex(col2)));
            user.put("email",cursor.getString(cursor.getColumnIndex(col3)));
            user.put("password",cursor.getString(cursor.getColumnIndex(col4)));
            userList.add(user);
        }
        return  userList;
    }
}
