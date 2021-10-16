package com.example.laptopduniya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laptopduniya.R;
import com.example.laptopduniya.models.Laptop;
import com.example.laptopduniya.models.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    ArrayList<Order> list;
    ArrayList<Order> all_list;
    Context context;

    public OrderAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
        all_list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View laptopView = inflater.inflate(R.layout.order_row_design, parent, false);

        // Return a new holder instance
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(laptopView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = list.get(position);
        Laptop laptop = order.getLaptop();
        holder.title.setText(laptop.getTitle());
        holder.total_amount.setText("₹ "+order.getTotal_amount());
        holder.total_amount_dis.setText("₹ "+order.getTotal_amount_dis());
        if(order.getIs_student()){
            holder.total_amount.setVisibility(View.VISIBLE);
            holder.dis.setVisibility(View.VISIBLE);
        }
        holder.status.setText(order.getStatus());
        holder.dates.setText(order.getStart_date()+" to "+order.getEnd_date());
        if(laptop.getImgs().size()>0)
            Glide.with(context).load(laptop.getImgs().get(0)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        ImageView img;
        TextView title,total_amount,dates,status,total_amount_dis,dis;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.laptop_card);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            total_amount = itemView.findViewById(R.id.total_amount);
            total_amount_dis = itemView.findViewById(R.id.total_amount_dis);
            dis = itemView.findViewById(R.id.dis);
            dates = itemView.findViewById(R.id.dates);
            status = itemView.findViewById(R.id.status);
        }
    }
}
