package com.example.laptopduniya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptopduniya.db_helpers.ProfileDBHelper;
import com.example.laptopduniya.models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,pass,repass;
    ProfileDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name_text);
        email = findViewById(R.id.email_text);
        pass = findViewById(R.id.password1_text);
        repass = findViewById(R.id.password2_text);
        dbHelper = new ProfileDBHelper(this);
    }
    public void register_click(View view) {
        String name_text    = name.getText().toString();
        String email_text   = email.getText().toString();
        String pass_text    = pass.getText().toString();
        String repass_text  = repass.getText().toString();
        if(name_text.equals("") || email_text.equals("")||pass_text.equals("")||repass_text.equals("")){
            if(name_text.equals(""))
                name.setError("cannot be empty");
            if(email_text.equals(""))
                email.setError("cannot be empty");
            if(pass_text.equals(""))
                pass.setError("cannot be empty");
            if(repass_text.equals(""))
                repass.setError("cannot be empty");
        }else {
            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(email_text);
//            Log.d("validity", m.toString());
            if (m.find()) {
                if(pass_text.equals(repass_text)){
                    if(dbHelper.checkEmail(email_text)){
                        email.setError("email already taken");
                        Toast.makeText(this, "email already taken", Toast.LENGTH_SHORT).show();
                    }else{
                        if(dbHelper.create_user(email_text,name_text,pass_text)){
                            SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("login_email",email_text);
                            editor.apply();
                            User user = dbHelper.getUserData(email_text);
                            if(user.getProfile_created()){
                                startActivity(new Intent(this,DashBoardActivity.class));
                                finish();
                            }else{
                                startActivity(new Intent(this,SetProfileActivity.class));
                                finish();
                            }

                        }else{
                            Toast.makeText(this, "error while adding user", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    repass.setError("password should match");
                }
            }
            else
                email.setError("email invalid");
        }
    }

    public void changeToLogin(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}