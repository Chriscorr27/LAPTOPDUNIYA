package com.example.laptopduniya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptopduniya.db_helpers.ProfileDBHelper;
import com.example.laptopduniya.models.User;
import com.example.laptopduniya.models.UserProfile;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    ProfileDBHelper dbHelper;
    Toolbar toolbar;
    TextView name,email,email_profile,is_student,phone_profile,gender,dob,phone;
    CircleImageView profile_pic;
    LinearLayout idcard_layout;
    AppCompatButton add_student_btn;
    Uri idcard_pic,profile_uri ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        profile_pic=findViewById(R.id.profile_pic);
        email_profile=findViewById(R.id.email_profile);
        is_student=findViewById(R.id.is_student);
        phone_profile=findViewById(R.id.phone_profile);
        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        phone=findViewById(R.id.phone);
        idcard_layout=findViewById(R.id.idCardLayout);
        add_student_btn=findViewById(R.id.add_student_btn);
        setSupportActionBar(toolbar);

        dbHelper=new ProfileDBHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
        String email_text = sharedPreferences.getString("login_email","");
        Log.d("profile_details",email_text);
        User user = dbHelper.getUserData(email_text);
        UserProfile profile = dbHelper.getProfileData(email_text);
        Log.d("profile_details",user.getName());
        name.setText(user.getName());
        email.setText(user.getEmail());
        email_profile.setText(user.getEmail());
        phone_profile.setText(profile.getPhoneNumber());
        gender.setText(profile.getGender());
        dob.setText(profile.getDob());
        phone.setText(profile.getPhoneNumber());
        profile_uri = Uri.parse(profile.getProfilePic());
        profile_pic.setImageURI(profile_uri);
        
        if(profile.getStudent()){
            idcard_layout.setVisibility(View.VISIBLE);
            is_student.setVisibility(View.VISIBLE);
            idcard_pic = Uri.parse(profile.getIdcard_pic());
        }
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

    public void editBtnClick(View view) {
        Intent intent = new Intent(this,SetProfileActivity.class);
        intent.putExtra("update",true);
        startActivity(intent);
        finish();
    }

    public void show_idcard(View v) {
        if(idcard_pic!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.image_dialog_box,null);
            ImageView profile_img,call_icon,chat_icon,info_icon;
            TextView name;
            int width = dialogView.getWidth();
            profile_img = dialogView.findViewById(R.id.profile_img);
            profile_img.setImageURI(idcard_pic);
            builder.setView(dialogView);
            builder.setCancelable(true);
            builder.show();
        }
    }

    public void show_profile_pic(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.image_dialog_box,null);
        ImageView profile_img,call_icon,chat_icon,info_icon;
        TextView name;
        int width = dialogView.getWidth();
        profile_img = dialogView.findViewById(R.id.profile_img);
        profile_img.setImageURI(profile_uri);
        builder.setView(dialogView);
        builder.setCancelable(true);
        builder.show();
    }
}