package com.example.laptopduniya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptopduniya.adapter.LaptopAdapter;
import com.example.laptopduniya.adapter.OrderAdapter;
import com.example.laptopduniya.models.Laptop;
import com.example.laptopduniya.models.Order;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrdersActivity extends AppCompatActivity {
    String email="";
    ProgressBar pb;
    Toolbar toolbar;
    RecyclerView order_rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        pb = findViewById(R.id.pb);
        order_rv = findViewById(R.id.order_rv);
        SharedPreferences sharedPreferences = getSharedPreferences("auth",MODE_PRIVATE);
        email = sharedPreferences.getString("login_email","");
        pb.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject data= new JSONObject();
        try {
            data.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://chris2001.pythonanywhere.com/api/get_orders/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, data, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray orders = response.getJSONArray("data");
                            ArrayList<Order> order_list = new ArrayList<>();
                            for(int i=0;i<orders.length();i++){
                                JSONObject order_json = orders.getJSONObject(i);
                                Log.d("orders",order_json.toString());
                                JSONObject laptop_json = order_json.getJSONObject("laptop");
                                int id = laptop_json.getInt("id");
                                String title = laptop_json.getString("title");
                                String brand = laptop_json.getString("brand");
                                String ram_type = laptop_json.getString("ram_type");
                                String ram_capacity = laptop_json.getString("ram_capacity");
                                Boolean ssd_present = laptop_json.getBoolean("ssd_present");
                                String ssd_capacity = laptop_json.getString("ssd_capacity");
                                Boolean hdd_present = laptop_json.getBoolean("hdd_present");
                                String hdd_capacity = laptop_json.getString("hdd_capacity");
                                String size = laptop_json.getString("size");
                                String weight = laptop_json.getString("weight");
                                int price = laptop_json.getInt("price");
                                JSONArray img_array = laptop_json.getJSONArray("imgs");
                                int total_price = 0;
                                String status = order_json.getString("status");
                                String start_date = order_json.getString("start_date");
                                String end_date = order_json.getString("end_date");
                                String total_amount = order_json.getString("total_price");
                                String total_amount_dis = order_json.getString("total_price_dis");
                                Boolean is_student = order_json.getBoolean("is_student");
                                String address = order_json.getString("address");
                                String lat = order_json.getString("lat");
                                String lon = order_json.getString("lon");
                                ArrayList<String> imgs = new ArrayList<>();
                                for(int j=0;j<img_array.length();j++){
                                    imgs.add(img_array.getString(j));
                                }
                                Laptop laptop = new Laptop(id,title,imgs,brand,ram_type,ram_capacity,ssd_capacity,hdd_capacity,size,weight,ssd_present,hdd_present,price,total_price);
                                Order order = new Order(start_date,end_date,total_amount,status,lat,lon,laptop,total_amount_dis,is_student);
                                order_list.add(order);
                            }
                            Collections.reverse(order_list);
                            OrderAdapter orderAdapter = new OrderAdapter(order_list,getApplicationContext());
                            order_rv.setAdapter(orderAdapter);
                            order_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pb.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("date_range",error.networkResponse.toString());
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
}