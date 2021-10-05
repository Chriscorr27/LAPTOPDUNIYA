package com.example.laptopduniya.db_helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.laptopduniya.models.User;
import com.example.laptopduniya.models.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class ProfileDBHelper extends SQLiteOpenHelper {
    public ProfileDBHelper(@Nullable Context context){
        super(context, "LaptopDuniya_server.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE users( email TEXT PRIMARY KEY,name TEXT,password TEXT,profile_created BIT(1))";
        String CREATE_PROFILE_TABLE = "CREATE TABLE user_profiles( " +
                "email TEXT PRIMARY KEY,phone TEXT,gender TEXT,profile_pic TEXT,dob TEXT," +
                "isStudent BIT(1),isStudentVerified BIT(1),idcard_number Text,idcard_pic Text)";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS user_profiles");
        onCreate(db);
    }
    public Boolean create_user(String email,String name,String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("profile_created",false);
        long result = database.insert("users",null,contentValues);
        return result != -1;
    }
    public Boolean update_user(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",user.getEmail());
        contentValues.put("name",user.getName());
        contentValues.put("profile_created",user.getProfile_created()?1:0);
        Cursor cursor =database.rawQuery("select * from users where email = ?",new String[]{user.getEmail()});
        if(cursor.getCount()!=0) {
            long result = database.update("users", contentValues, "email=?", new String[]{user.getEmail()});
            return result != -1;
        }else{
            return false;
        }
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

    public User getUserData(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor =database.rawQuery("select * from users where email = ?",new String[]{email});
        User user = new User();

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            user.setEmail(cursor.getString(0));
            user.setName(cursor.getString(1));
            user.setProfile_created(cursor.getInt(3)==1);
//            Log.d("profile_details",Boolean.toString(cursor.getInt(3)==1));
        }
        return user;
    }


    public Boolean save_profile(UserProfile profile){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",profile.getEmail());
        contentValues.put("phone",profile.getPhoneNumber());
        contentValues.put("gender",profile.getGender());
        contentValues.put("dob",profile.getDob());
        contentValues.put("profile_pic",profile.getProfilePic());
        contentValues.put("idcard_number",profile.getIdcard_number());
        contentValues.put("idcard_pic",profile.getIdcard_pic());
        contentValues.put("isStudent",profile.getStudent());
        contentValues.put("isStudentVerified",profile.getStudentVerified());
        Log.d("add",contentValues.toString());
        long result = -1;
        if(checkProfileEmail(profile.getEmail())){
            result = database.update("user_profiles", contentValues, "email=?", new String[]{profile.getEmail()});
        }else {
            result = database.insert("user_profiles", null, contentValues);
        }
        Log.d("add",Long.toString(result));
        return result != -1;
    }

    public Boolean checkProfileEmail(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor =database.rawQuery("select * from user_profiles where email = ?",new String[]{email});
        return cursor.getCount()!=0;
    }

    public UserProfile getProfileData(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor =database.rawQuery("select * from user_profiles where email = ?",new String[]{email});
        UserProfile profile = new UserProfile();

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            profile.setEmail(cursor.getString(0));
            profile.setPhoneNumber(cursor.getString(1));
            profile.setGender(cursor.getString(2));
            profile.setProfilePic(cursor.getString(3));
            profile.setDob(cursor.getString(4));
            profile.setStudent(cursor.getInt(5)==1);
            profile.setStudentVerified(cursor.getInt(6)==1);
            profile.setIdcard_number(cursor.getString(7));
            profile.setIdcard_pic(cursor.getString(8));

        }
        return profile;
    }

}
