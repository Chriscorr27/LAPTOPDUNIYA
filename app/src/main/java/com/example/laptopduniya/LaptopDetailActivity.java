package com.example.laptopduniya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptopduniya.adapter.ImageViewAdapter;
import com.example.laptopduniya.adapter.LaptopAdapter;
import com.example.laptopduniya.api.MySingleton;
import com.example.laptopduniya.models.Laptop;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.schibstedspain.leku.LocationPickerActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LaptopDetailActivity extends AppCompatActivity {
    private static final int ADDRESS_PICKER_REQUEST = 1234;
    ViewPager image_pager;
    ScrollView details;
    LinearLayout rent_btn;
    ConstraintLayout order_page;
    ImageViewAdapter imageViewAdapter;
    String title,brand,size,weight,ram_type,ram_capacity,ssd_capacity,hdd_capacity;
    Boolean is_ssd,is_hdd;
    int id,price_day,total_price;
    TextView title_view,price_view,brand_view,size_view,weight_view,ram_type_view,ram_capacity_view,ssd_view,hdd_view;
    TextView address_text;
    TextView start_date_text,end_date_text;
    String address_string="",lat="",lon="";
    LinearLayout ssd_layout,hdd_layout;
    MaterialDatePicker materialDatePicker;
    String start_date="",end_date="",email="";
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_detail);
        image_pager = (ViewPager)findViewById(R.id.imagepager_main);
        title_view = findViewById(R.id.title);
        price_view = findViewById(R.id.price_day);
        brand_view = findViewById(R.id.brand);
        size_view = findViewById(R.id.size);
        weight_view = findViewById(R.id.weight);
        ram_type_view = findViewById(R.id.ram_type);
        ram_capacity_view = findViewById(R.id.ram_capacity);
        ssd_view = findViewById(R.id.ssd_capacity);
        hdd_view = findViewById(R.id.hdd_capacity);
        ssd_layout = findViewById(R.id.ssd_layout);
        hdd_layout = findViewById(R.id.hdd_layout);
        details = findViewById(R.id.detail);
        rent_btn = findViewById(R.id.rent_layout);
        order_page = findViewById(R.id.order_page);
        address_text = findViewById(R.id.address_text);
        start_date_text = findViewById(R.id.start_date);
        end_date_text = findViewById(R.id.end_date);
        pb = findViewById(R.id.pb);
        ArrayList<String> images;
        images = getIntent().getStringArrayListExtra("images");
        title = getIntent().getStringExtra("title");
        brand = getIntent().getStringExtra("brand");
        size = getIntent().getStringExtra("size");
        weight = getIntent().getStringExtra("weight");
        ram_type = getIntent().getStringExtra("ram_type");
        ram_capacity = getIntent().getStringExtra("ram_capacity");
        ssd_capacity = getIntent().getStringExtra("ssd_capacity");
        hdd_capacity = getIntent().getStringExtra("hdd_capacity");
        id = getIntent().getIntExtra("id",-1);
        price_day = getIntent().getIntExtra("price_day",-1);
        total_price = getIntent().getIntExtra("total_price",-1);
        is_hdd = getIntent().getBooleanExtra("is_hdd",false);
        is_ssd = getIntent().getBooleanExtra("is_ssd",false);
        SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
        email = sharedPreferences.getString("login_email","");
        title_view.setText(title);
        price_view.setText("₹ "+Integer.toString(price_day)+" / day");
        brand_view.setText(brand);
        size_view.setText(size);
        weight_view.setText(weight);
        ram_type_view.setText(ram_type);
        ram_capacity_view.setText(ram_capacity);
        if(is_hdd){
            hdd_view.setText(hdd_capacity);
            hdd_layout.setVisibility(View.VISIBLE);
        }
        if(is_ssd){
            ssd_view.setText(ssd_capacity);
            ssd_layout.setVisibility(View.VISIBLE);
        }

        imageViewAdapter = new ImageViewAdapter(this, images);
        image_pager.setAdapter(imageViewAdapter);
        CalendarConstraints.Builder contrainBuilder = new CalendarConstraints.Builder();
        contrainBuilder.setValidator(DateValidatorPointForward.now());
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("select date range");
        builder.setCalendarConstraints(contrainBuilder.build());
        materialDatePicker = builder.build();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.d("date_range",materialDatePicker.getHeaderText());
                Log.d("date_range",Long.toString(((Pair<Long, Long>)selection).first));
                Log.d("date_range",Long.toString(((Pair<Long, Long>)selection).second));
                calendar.setTimeInMillis(((Pair<Long, Long>)selection).first);
                start_date = formatter.format(calendar.getTime());
                calendar.setTimeInMillis(((Pair<Long, Long>)selection).second);
                end_date = formatter.format(calendar.getTime());
                start_date_text.setText("start-date :\n"+start_date);
                end_date_text.setText("end-date :\n"+end_date);
            }
        });




    }

    public void show_order_page(View view) {
        order_page.setVisibility(View.VISIBLE);
        rent_btn.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
    }

    public void cancel_order(View view) {
        order_page.setVisibility(View.GONE);
        rent_btn.setVisibility(View.VISIBLE);
        details.setVisibility(View.VISIBLE);
    }

    public void show_map(View view)  {
        Intent i = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(i, ADDRESS_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Address address = data.getParcelableExtra("address");
                address_string = address.getAddressLine(0);
                lat = Double.toString(address.getLatitude());
                lon = Double.toString(address.getLongitude());
                Log.d("address","address");
                Log.d("address",address.getAddressLine(0));
                Log.d("address",Double.toString(address.getLatitude()));
                Log.d("address",Double.toString(address.getLongitude()));
                address_text.setText(address.getAddressLine(0));
            }
        }
    }

    public void select_data(View view) {
        materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
    }

    public void rent_order(View view) {
        if(address_string!="" && start_date!=""&&end_date!=""){
            order_page.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            apicall();
        }else if(address_string==""){
            Toast.makeText(this, "select address", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "select date range", Toast.LENGTH_SHORT).show();
        }
    }

    public void apicall(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Api call
        JSONObject data = new JSONObject();
        try {
            data.put("email",email);
            data.put("laptop_id",id);
            data.put("start_date",start_date);
            data.put("end_date",end_date);
            data.put("address",address_string);
            data.put("lat",lat);
            data.put("lon",lon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("date_range",data.toString());
        String url = "https://chris2001.pythonanywhere.com/api/create_orders/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, data, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("date_range",response.toString());
                        order_page.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(),OrdersActivity.class));
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("date_range",error.networkResponse.toString());
                        order_page.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };


        requestQueue.add(jsonObjectRequest);

    }
}

