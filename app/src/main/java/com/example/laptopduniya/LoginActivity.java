package com.example.laptopduniya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptopduniya.db_helpers.LoginDBHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    TextView errorMsg;
    LoginDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errorMsg = findViewById(R.id.errorMsg);
        email = findViewById(R.id.email_text);
        pass = findViewById(R.id.password_text);
        dbHelper=new LoginDBHelper(this);
    }

    public void login_click(View view) {
        String email_text   = email.getText().toString();
        String pass_text    = pass.getText().toString();
        if(email_text.equals("")||pass_text.equals("")){

            if(email_text.equals(""))
                email.setError("cannot be empty");
            if(pass_text.equals(""))
                pass.setError("cannot be empty");
        }else {
            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(email_text);
            if (m.find()) {
                if(dbHelper.checkEmailPassword(email_text,pass_text)){
                    SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login_email",email_text);
                    editor.apply();
                    startActivity(new Intent(this,DashBoardActivity.class));
                    finish();
                }else{
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }
            else
                email.setError("email invalid");
        }
    }

    public void changeToRegister(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }
}