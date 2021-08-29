package com.example.laptopduniya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.laptopduniya.db_helpers.LoginDBHelper;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class startActivity extends AppCompatActivity {
    CircularProgressButton circularProgressButton;
    LoginDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        circularProgressButton = findViewById(R.id.startBtn);
        dbHelper = new LoginDBHelper(this);
    }

    public void startApp(View view) {
        circularProgressButton.startAnimation();
        SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String email = sharedPreferences.getString("login_email","");
        Log.d("login_auth",email);
        if(email.equals("")){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
           if(dbHelper.checkEmail(email) ){
               startActivity(new Intent(this,DashBoardActivity.class));
               finish();
           }else{
              editor.putString("login_email","");
              editor.apply();
               Intent intent = new Intent(this,LoginActivity.class);
               startActivity(intent);
               finish();
           }
        }


    }
}