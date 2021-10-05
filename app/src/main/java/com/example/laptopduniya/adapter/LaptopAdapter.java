package com.example.laptopduniya.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laptopduniya.DashBoardActivity;
import com.example.laptopduniya.LaptopDetailActivity;
import com.example.laptopduniya.R;
import com.example.laptopduniya.models.Filter_data;
import com.example.laptopduniya.models.Laptop;

import java.util.ArrayList;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolder> {
    ArrayList<Laptop> list;
    ArrayList<Laptop> all_list;
    Context context;

    public LaptopAdapter(ArrayList<Laptop> list, Context context) {
        this.list = list;
        this.all_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View laptopView = inflater.inflate(R.layout.laptop_row_design, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(laptopView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laptop laptop = list.get(position);
        holder.title.setText(laptop.getTitle());
        holder.price_day.setText("₹ "+Integer.toString(laptop.getPrice_per_day())+" / day");
        holder.ram_text.setText(laptop.getRam_capacity());
        String storage_text = laptop.getIs_hdd()?"Hdd "+laptop.getHdd_capacity():"SSd "+laptop.getSsd_capacity();
        holder.storage_text.setText(storage_text);
        holder.size_text.setText(laptop.getSize());
        holder.weight_text.setText(laptop.getWeight());
        if(laptop.getImgs().size()>0)
            Glide.with(context).load(laptop.getImgs().get(0)).into(holder.img);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LaptopDetailActivity.class);
                intent.putExtra("id",laptop.getId());
                intent.putExtra("brand",laptop.getBrand());
                intent.putExtra("title",laptop.getTitle());
                intent.putExtra("size",laptop.getSize());
                intent.putExtra("weight",laptop.getWeight());
                intent.putExtra("ram_type",laptop.getRam_type());
                intent.putExtra("ram_capacity",laptop.getRam_capacity());
                intent.putExtra("is_ssd",laptop.getIs_ssd());
                intent.putExtra("is_hdd",laptop.getIs_hdd());
                intent.putExtra("ssd_capacity",laptop.getSsd_capacity());
                intent.putExtra("hdd_capacity",laptop.getHdd_capacity());
                intent.putExtra("price_day",laptop.getPrice_per_day());
                intent.putExtra("total_price",laptop.getTotal_price());
                intent.putExtra("images",laptop.getImgs());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        ImageView img;
        TextView title,price_day,ram_text,storage_text,size_text,weight_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.laptop_card);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            price_day = itemView.findViewById(R.id.price_day);
            ram_text = itemView.findViewById(R.id.ram_text);
            storage_text = itemView.findViewById(R.id.storage_text);
            size_text = itemView.findViewById(R.id.size_text);
            weight_text = itemView.findViewById(R.id.weight_text);
        }
    }

    public void filter(Filter_data data){
        ArrayList<Laptop> filter=new ArrayList<>();
        for(int i=0;i<all_list.size();i++){
            Laptop laptop = all_list.get(i);
            if(laptop.getBrand().equals(data.getBrand())||data.getBrand().equals("any")){

                if(laptop.getRam_type().equals(data.getRam_type()) || data.getRam_type().equals("any")){

                    if(laptop.getRam_capacity().equals(data.getRam_capacity()) || data.getRam_capacity().equals("any")){
                        if(laptop.getSize().equals(data.getSize())||data.getSize().equals("any")){
                            String[] w = new String[2];
                            w[0]="";
                            w[1]="";
                            if(!data.getWeight().equals("any")) {
                                w[0] = data.getWeight().substring(0, 5);
                                w[1] = data.getWeight().substring(6);
                            }
                            if((laptop.getWeight().compareTo(w[1])<=0 && w[0].equals("below"))||(laptop.getWeight().compareTo(w[1])>=0 &&w[0].equals("above"))||data.getWeight().equals("any")){

                                if((data.getIs_hdd()&&(laptop.getHdd_capacity().equals(data.getHdd_capacity())||data.getHdd_capacity().equals("any"))) || (data.getIs_ssd() && (laptop.getSsd_capacity().equals(data.getSsd_capacity())||data.getSsd_capacity().equals("any")))||(!data.getIs_hdd()&& !data.getIs_ssd())){
                                    filter.add(laptop);
                                }
                            }
                        }

                    }
                }
            }
        }
        list=filter;
        notifyDataSetChanged();
    }

    public void clear_filter(){
        list = all_list;
        notifyDataSetChanged();
    }
}
