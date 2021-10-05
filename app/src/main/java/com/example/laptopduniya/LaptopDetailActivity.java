package com.example.laptopduniya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laptopduniya.adapter.ImageViewAdapter;

import java.util.ArrayList;

public class LaptopDetailActivity extends AppCompatActivity {
    ViewPager image_pager;
    ImageViewAdapter imageViewAdapter;
    String title,brand,size,weight,ram_type,ram_capacity,ssd_capacity,hdd_capacity;
    Boolean is_ssd,is_hdd;
    int id,price_day,total_price;
    TextView title_view,price_view,brand_view,size_view,weight_view,ram_type_view,ram_capacity_view,ssd_view,hdd_view;

    LinearLayout ssd_layout,hdd_layout;
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
    }
}