package com.example.laptopduniya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutUsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    GoogleMap map;
    LinearLayout contact_info_btn;
    ConstraintLayout contact_info_box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar = findViewById(R.id.toolbar);
        contact_info_box = findViewById(R.id.contact_info_box);
        contact_info_btn = findViewById(R.id.contact_info_btn);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng companyLocation = new LatLng(19.2434364,72.8548608);
        map.addMarker(new MarkerOptions()
                .position(companyLocation)
                .title("Laptop Duniya"));
        map.moveCamera(CameraUpdateFactory.newLatLng(companyLocation));

    }

    public void showContactInfo(View view) {
        contact_info_btn.setVisibility(View.GONE);
        contact_info_box.setVisibility(View.VISIBLE);
    }

    public void showContactInfoBtn(View view) {
        contact_info_btn.setVisibility(View.VISIBLE);
        contact_info_box.setVisibility(View.GONE);
    }
}