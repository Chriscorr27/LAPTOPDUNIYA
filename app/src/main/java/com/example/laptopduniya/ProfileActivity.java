package com.example.laptopduniya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.laptopduniya.db_helpers.LoginDBHelper;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    LoginDBHelper dbHelper;
    Toolbar toolbar;
    TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        setSupportActionBar(toolbar);

        dbHelper=new LoginDBHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
        String email_text = sharedPreferences.getString("login_email","");
        Log.d("profile_details",email_text);
        Map<String,String> profileDetails = dbHelper.getUserData(email_text);
        Log.d("profile_details",profileDetails.get("name"));
        name.setText(profileDetails.get("name"));
        email.setText(profileDetails.get("email"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appmenu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile_menu:
                startActivity(new Intent(this,ProfileActivity.class));
                finish();
                break;
            case R.id.home:
                startActivity(new Intent(this,DashBoardActivity.class));
                finish();
                break;
            case R.id.about_menu:
                startActivity(new Intent(this,AboutUsActivity.class));
                finish();
                break;
            case R.id.logout_menu:
                SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login_email","");
                editor.apply();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
        return true;
    }
}