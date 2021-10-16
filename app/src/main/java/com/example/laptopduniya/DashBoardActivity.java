package com.example.laptopduniya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.laptopduniya.adapter.LaptopAdapter;
import com.example.laptopduniya.api.MySingleton;
import com.example.laptopduniya.models.Filter_data;
import com.example.laptopduniya.models.Laptop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<Laptop> list;
    RecyclerView laptop_rv;
    ConstraintLayout filter_box;
    Spinner brand_spinner,size_spinner,weight_spinner,ram_type_spinner,ram_capacity_spinner,ssd_capacity_spinner,hdd_capacity_spinner;
    CheckBox ssd_check,hdd_check;
    LaptopAdapter laptopAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        toolbar = findViewById(R.id.toolbar);
        laptop_rv = findViewById(R.id.laptop_rv);
        filter_box = findViewById(R.id.filter_box);
        brand_spinner = findViewById(R.id.brand_spinner);
        size_spinner = findViewById(R.id.size_spinner);
        weight_spinner = findViewById(R.id.weight_spinner);
        ram_type_spinner = findViewById(R.id.ram_type_spinner);
        ram_capacity_spinner = findViewById(R.id.ram_capacity_spinner);
        ssd_capacity_spinner = findViewById(R.id.ssd_capacity_spinner);
        hdd_capacity_spinner = findViewById(R.id.hdd_capacity_spinner);
        ssd_check = findViewById(R.id.ssd_check);
        hdd_check = findViewById(R.id.hdd_check);

        String[] brand_array = getResources().getStringArray(R.array.brand_array);
        ArrayAdapter<CharSequence> brand_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,brand_array);
        brand_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand_spinner.setAdapter(brand_adapter);

        String[] size_array = getResources().getStringArray(R.array.size_array);
        ArrayAdapter<CharSequence> size_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,size_array);
        size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner.setAdapter(size_adapter);

        String[] weight_array = getResources().getStringArray(R.array.weight_array);
        ArrayAdapter<CharSequence> weight_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,weight_array);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_spinner.setAdapter(weight_adapter);

        String[] ram_type_array = getResources().getStringArray(R.array.ram_type_array);
        ArrayAdapter<CharSequence> ram_type_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,ram_type_array);
        ram_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ram_type_spinner.setAdapter(ram_type_adapter);

        String[] ram_capacity_array = getResources().getStringArray(R.array.ram_capacity_array);
        ArrayAdapter<CharSequence> ram_capacity_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,ram_capacity_array);
        ram_capacity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ram_capacity_spinner.setAdapter(ram_capacity_adapter);

        String[] ssd_capacity_array = getResources().getStringArray(R.array.ssd_capacity_array);
        ArrayAdapter<CharSequence> ssd_capacity_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,ssd_capacity_array);
        ssd_capacity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ssd_capacity_spinner.setAdapter(ssd_capacity_adapter);

        String[] hdd_capacity_array = getResources().getStringArray(R.array.hdd_capacity_array);
        ArrayAdapter<CharSequence> hdd_capacity_adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,hdd_capacity_array);
        hdd_capacity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hdd_capacity_spinner.setAdapter(hdd_capacity_adapter);

        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        //Api call
        String url = "https://chris2001.pythonanywhere.com/api/get_laptops/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = (JSONArray) response.get("data");

                            for(int i=0;i<data.length();i++){
                                JSONObject jsonObject = data.getJSONObject(i);
//                                Log.d("Response",jsonObject.toString());
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String brand = jsonObject.getString("brand");
                                String ram_type = jsonObject.getString("ram_type");
                                String ram_capacity = jsonObject.getString("ram_capacity");
                                Boolean ssd_present = jsonObject.getBoolean("ssd_present");
                                String ssd_capacity = jsonObject.getString("ssd_capacity");
                                Boolean hdd_present = jsonObject.getBoolean("hdd_present");
                                String hdd_capacity = jsonObject.getString("hdd_capacity");
                                String size = jsonObject.getString("size");
                                String weight = jsonObject.getString("weight");
                                int price = jsonObject.getInt("price");
                                int total_price = 0;
                                ArrayList<String> images = new ArrayList<>();
                                JSONArray img_array = jsonObject.getJSONArray("imgs");
//                                Log.d("Response",img_array.toString());
                                for(int j=0;j<img_array.length();j++){
                                    images.add(img_array.getString(j));
//                                    Log.d("Response",img_array.getString(i));
                                }
                                Laptop laptop = new Laptop(id,title,images,brand,ram_type,ram_capacity,ssd_capacity,hdd_capacity,size,weight,ssd_present,hdd_present,total_price,price);
                                list.add(laptop);
                            }
                            laptopAdapter = new LaptopAdapter(list,getApplicationContext());
                            laptop_rv.setAdapter(laptopAdapter);
                            laptop_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            laptop_rv.setVisibility(View.VISIBLE);
                            findViewById(R.id.pb).setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            findViewById(R.id.pb).setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);



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

    public void filter_click(View view) {
        laptop_rv.setVisibility(View.GONE);
        filter_box.setVisibility(View.VISIBLE);
    }

    public void cancel_filter(View view) {
        laptop_rv.setVisibility(View.VISIBLE);
        filter_box.setVisibility(View.GONE);
    }

    public void filter_apply(View view) {
        String brand_text = brand_spinner.getItemAtPosition(brand_spinner.getSelectedItemPosition()).toString();
        String size_text = size_spinner.getItemAtPosition(size_spinner.getSelectedItemPosition()).toString();
        String weight_text = weight_spinner.getItemAtPosition(weight_spinner.getSelectedItemPosition()).toString();
        String ram_type = ram_type_spinner.getItemAtPosition(ram_type_spinner.getSelectedItemPosition()).toString();
        String ram_capacity = ram_capacity_spinner.getItemAtPosition(ram_capacity_spinner.getSelectedItemPosition()).toString();
        String ssd_caapcity = ssd_capacity_spinner.getItemAtPosition(ssd_capacity_spinner.getSelectedItemPosition()).toString();
        String hdd_capacity = hdd_capacity_spinner.getItemAtPosition(hdd_capacity_spinner.getSelectedItemPosition()).toString();
        boolean is_ssd = ssd_check.isChecked();
        boolean is_hdd = hdd_check.isChecked();
        Filter_data filterData = new Filter_data(brand_text,size_text,ram_type,ram_capacity,ssd_caapcity,hdd_capacity,weight_text,is_ssd,is_hdd);
        laptopAdapter.filter(filterData);
        laptop_rv.setVisibility(View.VISIBLE);
        filter_box.setVisibility(View.GONE);
    }

    public void clear_filter(View view) {
        laptopAdapter.clear_filter();
        laptop_rv.setVisibility(View.VISIBLE);
        filter_box.setVisibility(View.GONE);
    }
}