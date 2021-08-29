package com.example.laptopduniya.db_helpers;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LoginDBHelper extends SQLiteOpenHelper {
    public LoginDBHelper(Context context) {
        super(context, "LaptopDuniya.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE users( email TEXT PRIMARY KEY,name TEXT,password TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public Boolean create_user(String email,String name,String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        long result = database.insert("users",null,contentValues);
        return result != -1;
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor =database.rawQuery("select * from users where email = ?",new String[]{email});
        return cursor.getCount()!=0;
    }
    public Boolean checkEmailPassword(String email,String password){
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor =database.rawQuery("select * from users where email = ? and password = ?",new String[]{email,password});
        return cursor.getCount()>0;
    }

}
