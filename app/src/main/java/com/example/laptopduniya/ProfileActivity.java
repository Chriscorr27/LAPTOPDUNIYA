package com.example.laptopduniya;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laptopduniya.api.APIService;
import com.example.laptopduniya.db_helpers.ProfileDBHelper;
import com.example.laptopduniya.models.StudentVerify;
import com.example.laptopduniya.models.User;
import com.example.laptopduniya.models.UserProfile;
import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    ProfileDBHelper dbHelper;
    Toolbar toolbar;
    EditText id_number;
    ImageView id_add_view;
    TextView name,email,email_profile,is_student,phone_profile,gender,dob,phone,idcard_number;
    CircleImageView profile_pic;
    LinearLayout idcard_layout;
    AppCompatButton add_student_btn;
    Uri profile_uri,add_idcard_pic;
    String idcard_pic;
    LinearLayout main_layout,id_layout;
    ProgressBar pb;
    ConstraintLayout id_main_layout;
    Retrofit retrofit;
    APIService retrofitAPI;
    String url = "https://chris2001.pythonanywhere.com/api/";
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
        main_layout=findViewById(R.id.main_layout);
        id_layout=findViewById(R.id.id_layout);
        id_number=findViewById(R.id.id_number);
        id_add_view=findViewById(R.id.id_add_view);
        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        phone=findViewById(R.id.phone);
        idcard_layout=findViewById(R.id.idCardLayout);
        add_student_btn=findViewById(R.id.add_student_btn);
        idcard_number = findViewById(R.id.idcard_number);
        pb = findViewById(R.id.pb);
        id_main_layout = findViewById(R.id.add_id_box);
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

//         retrofit = new Retrofit.Builder()
//                 .baseUrl(url)
//                 .addConverterFactory(GsonConverterFactory.create())
//                 .build();
//         retrofitAPI = retrofit.create(APIService.class);
// //        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),user.getEmail());

//         Call<StudentVerify> call =  retrofitAPI.getStudent(user.getEmail());;
//         call.enqueue(new Callback<StudentVerify>() {
//             @Override
//             public void onResponse(Call<StudentVerify> call, Response<StudentVerify> response) {
//                 StudentVerify r = response.body();
//                 Log.d("img",Boolean.toString(r.getIs_verified()));
//                 Log.d("img",Boolean.toString(r.getEmail().equals("")));
//                 Log.d("img","email : "+r.getEmail());
//                 if(!r.getEmail().equals("")){
//                     idcard_layout.setVisibility(View.VISIBLE);
//                     idcard_pic = r.getId_pic();
//                     idcard_number.setText(r.getId_number());
//                         if(r.getIs_verified()){
//                             is_student.setVisibility(View.VISIBLE);
//                             findViewById(R.id.not_verified).setVisibility(View.GONE);
//                             findViewById(R.id.add_id).setVisibility(View.GONE);
//                         }
//                     }
//                 findViewById(R.id.pb1).setVisibility(View.GONE);
//                 main_layout.setVisibility(View.VISIBLE);
//             }

//             @Override
//             public void onFailure(Call<StudentVerify> call, Throwable t) {
//                 Log.d("img",t.getLocalizedMessage());
//                 pb.setVisibility(View.GONE);
//                 id_layout.setVisibility(View.VISIBLE);
//             }
//         });



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
            case R.id.order_menu:
                startActivity(new Intent(this,OrdersActivity.class));
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
            Glide.with(this).load(idcard_pic).into(profile_img);
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

    public void open_add_id(View view) {
        id_main_layout.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.GONE);
    }

    public void cancel_id(View view) {
        id_main_layout.setVisibility(View.GONE);
        main_layout.setVisibility(View.VISIBLE);
    }

    public void add_id(View view) {
        String id_number_text=id_number.getText().toString();
        if(id_number_text!=""&&add_idcard_pic!=null){
            try {
                File file = new File(add_idcard_pic.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), add_idcard_pic);
                File f = new File(this.getCacheDir(), file.getName());
                f.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                upload_data(f,id_number_text);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "invalid", Toast.LENGTH_SHORT).show();
        }
    }
    private void upload_data(final File file,final String id_number_text)  {
        pb.setVisibility(View.VISIBLE);
        id_layout.setVisibility(View.GONE);



        long t = System.currentTimeMillis();
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("id_pic", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),email_profile.getText().toString());
        RequestBody id_number = RequestBody.create(MediaType.parse("text/plain"),id_number_text);
        Call<StudentVerify> call = retrofitAPI.createStudent(filePart,email,id_number);
        call.enqueue(new Callback<StudentVerify>() {
            @Override
            public void onResponse(Call<StudentVerify> call, Response<StudentVerify> response) {
                StudentVerify r = response.body();
                Log.d("img",response.toString());
                pb.setVisibility(View.GONE);
                id_layout.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<StudentVerify> call, Throwable t) {
                Log.d("img",t.getLocalizedMessage());
                pb.setVisibility(View.GONE);
                id_layout.setVisibility(View.VISIBLE);
            }
        });

    }
    ActivityResultLauncher<Intent> idcard_img_launcher =
    registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (
    ActivityResult result) -> {

        if (result.getResultCode() == RESULT_OK) {
            add_idcard_pic = result.getData().getData();
            id_add_view.setImageURI(add_idcard_pic);

            // Use the uri to load the image
        } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
            // Use ImagePicker.Companion.getError(result.getData()) to show an error
        }
    });

    public void add_img(View view) {
        ImagePicker.Companion.with(ProfileActivity.this)
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
}
