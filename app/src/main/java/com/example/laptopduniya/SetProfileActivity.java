package com.example.laptopduniya;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptopduniya.db_helpers.ProfileDBHelper;
import com.example.laptopduniya.models.User;
import com.example.laptopduniya.models.UserProfile;
import com.github.drjacky.imagepicker.ImagePicker;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class SetProfileActivity extends AppCompatActivity implements DatePickerFragment.DateSelectedListener {
    TextView dob,idcard_err;
    Spinner gender_spinner;
    CircleImageView profile_img;
    ImageButton idcard_viewBtn,backBtn,add_profile;
    Uri profile_uri,idcard_uri;
    EditText name,email,phone_edit,idcard_number;
    RadioButton yesBtn,noBtn;
    CountryCodePicker ccp;
    LinearLayout idcard_layout;
    Boolean isStudent = false,is_update;
    String email_regex ="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    ProfileDBHelper profileDBHelper;
    DatePickerFragment datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        is_update = getIntent().getBooleanExtra("update",false);
        profileDBHelper = new ProfileDBHelper(this);
        dob = findViewById(R.id.dob);
        profile_img = findViewById(R.id.profile_pic);
        idcard_viewBtn = findViewById(R.id.idcard_viewBtn);
        backBtn = findViewById(R.id.backBtn);
        add_profile = findViewById(R.id.add_profile);
        gender_spinner = findViewById(R.id.gender_spinner);
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        phone_edit = findViewById(R.id.phone_edit);
        idcard_number = findViewById(R.id.edit_idcardnumber);
        ccp = findViewById(R.id.ccp);
        yesBtn = findViewById(R.id.yesBtn);
        noBtn = findViewById(R.id.noBtn);
        idcard_err = findViewById(R.id.idCardError);
        idcard_layout = findViewById(R.id.idCardLayout);
        ccp.registerCarrierNumberEditText(phone_edit);
        String[] gender_array = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,gender_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
        String email_text = sharedPreferences.getString("login_email","");
        User user = profileDBHelper.getUserData(email_text);
        email.setText(user.getEmail());
        name.setText(user.getName());
        Log.d("profile_details",user.getEmail());

        if(is_update){
            UserProfile profile = profileDBHelper.getProfileData(email_text);
            ccp.setFullNumber(profile.getPhoneNumber());
            Log.d("profile_details",profile.getGender());
            int index = profile.getGender().equals("Male")?0:(profile.getGender().equals("Female")?1:2);
            gender_spinner.setSelection(index);
            dob.setText(profile.getDob());
            String[] fields = profile.getDob().split("-");
            datePicker = new DatePickerFragment(Integer.parseInt(fields[0]),Integer.parseInt(fields[1])-1,Integer.parseInt(fields[2]));
            profile_uri = Uri.parse(profile.getProfilePic());
            profile_img.setImageURI(profile_uri);
            if(profile.getStudent()){
               yesBtn.setChecked(true);
               noBtn.setChecked(false);
               idcard_layout.setVisibility(View.VISIBLE);
               idcard_number.setText(profile.getIdcard_number());
               idcard_uri = Uri.parse(profile.getIdcard_pic());
            }
        }else{
            datePicker = new DatePickerFragment();
        }
        idcard_viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.image_dialog_box,null);
                ImageView profile_img,call_icon,chat_icon,info_icon;
                TextView name;
                int width = dialogView.getWidth();
                profile_img = dialogView.findViewById(R.id.profile_img);
                profile_img.setImageURI(idcard_uri);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idcard_layout.setVisibility(View.VISIBLE);
                isStudent=true;
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idcard_layout.setVisibility(View.GONE);
                isStudent=false;
            }
        });
    }



    public void showDatePickerDialog(View view) {
        datePicker.display(
                getSupportFragmentManager(),
                "datePicker", this);
    }

    @Override
    public void onDateSelected(DatePicker view, int year, int month, int day) {
        String month_text = (month+1)<10?"0"+Integer.toString(month+1):Integer.toString(month+1);
        String day_text = (day)<10?"0"+Integer.toString(day):Integer.toString(day);
        String date = Integer.toString(year)+"-"+month_text+"-"+day_text;
        dob.setText(date);
    }
    ActivityResultLauncher<Intent> profile_img_launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {

                if (result.getResultCode() == RESULT_OK) {
                    profile_uri = result.getData().getData();
                    profile_img.setImageURI(profile_uri);

                    // Use the uri to load the image
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });
    ActivityResultLauncher<Intent> idcard_img_launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {

                if (result.getResultCode() == RESULT_OK) {
                    idcard_uri = result.getData().getData();
                    idcard_viewBtn.setVisibility(View.VISIBLE);
                    idcard_err.setVisibility(View.GONE);
                    // Use the uri to load the image
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    idcard_err.setVisibility(View.VISIBLE);
                }
            });

    public void setProfile(View view) {
        ImagePicker.Companion.with(SetProfileActivity.this)
                .crop()
                .cropOval()
                .maxResultSize(512, 512, true)
                .createIntentFromDialog((Function1) new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        profile_img_launcher.launch(it);
                    }
                });
    }
    public void idCardImg(View view) {
        ImagePicker.Companion.with(SetProfileActivity.this)
                .crop()
                .maxResultSize(512, 512, true)
                .createIntentFromDialog((Function1) new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        idcard_img_launcher.launch(it);
                    }
                });
    }

    public void saveProfile(View view) {
        Pattern p = Pattern.compile(email_regex, Pattern.CASE_INSENSITIVE);
        String email_text,name_text,gender_text,idcard_number_text,dob_text;
        email_text = email.getText().toString();
        dob_text = dob.getText().toString();
        name_text = name.getText().toString();
        gender_text = gender_spinner.getItemAtPosition(gender_spinner.getSelectedItemPosition()).toString();
        idcard_number_text = idcard_number.getText().toString();

        Matcher matcher = p.matcher(email_text);
        if(name_text.trim().length()!=0&&matcher.matches()&&ccp.isValidFullNumber()&&gender_text!=""&&dob_text!=""&&profile_uri!=null&&(!isStudent||(idcard_number_text!=""&&idcard_uri!=null))){
//            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            String id_pic = "";
            if(isStudent){
                id_pic = idcard_uri.toString();
            }
            UserProfile profile = new UserProfile(ccp.getFullNumberWithPlus(),profile_uri.toString(),
                    gender_text,email_text,dob_text,idcard_number_text,id_pic,isStudent,false);
            User user = new User(email_text,name_text,true);
             profileDBHelper.save_profile(profile);
            profileDBHelper.update_user(user);
            if(is_update){
                startActivity(new Intent(this,ProfileActivity.class));
            }else{
                startActivity(new Intent(this,DashBoardActivity.class));
            }
            finish();
        }
        if(name_text.trim().length()==0){
            name.setError("empty not allowed");
        }
        if(!matcher.matches()){
            email.setError("invalid email");
        }
        if(!ccp.isValidFullNumber()){
            phone_edit.setError("invalid phone number");
        }
        if(isStudent&&idcard_number_text==""){
            idcard_number.setError("empty not allowed");
        }
        if(profile_uri==null){
            Toast.makeText(this, "Profile image not set", Toast.LENGTH_SHORT).show();
        }
        if(idcard_uri==null){
            idcard_err.setVisibility(View.VISIBLE);
        }
        if(dob_text==""){
            dob.setError("select dob");
        }
    }
}