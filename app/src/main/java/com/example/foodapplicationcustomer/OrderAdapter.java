package com.example.foodapplicationcustomer;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapplicationcustomer.support.OrderModel;
import com.example.foodapplicationcustomer.support.Request;

import java.util.List;
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderholder> {


    List<Request> data;
    Context context;
    OrderAdapter.OnOrderclick onorderclick;

    public interface OnOrderclick {
        void onClick(int pos, View view);
    }


    public OrderAdapter(List<Request> data, Context context, OrderAdapter.OnOrderclick onorderclick) {
        this.context = context;
        this.onorderclick = onorderclick;
        this.data = data;
    }


    @NonNull
    @Override
    public OrderAdapter.orderholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orderholder, parent, false);

        return new OrderAdapter.orderholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.orderholder holder, int position) {


        holder.orderImg.setImageResource(R.drawable.ic_baseline_fastfood_24);
        holder.orderaddress.setText(data.get(position).getAddress());
        holder.orderstatus.setText(data.get(position).getStatus());
        holder.orderprice.setText(data.get(position).getTotal());
        holder.ordercellnum.setText(data.get(position).getCellNumber());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class orderholder extends RecyclerView.ViewHolder {


        TextView orderId, orderprice, orderaddress, orderstatus,ordercellnum;
        ImageView orderImg;
        CardView Cardorder;
        LinearLayout linearLayout;


        public orderholder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderID);
            orderprice = itemView.findViewById(R.id.orderprice);
            orderImg = itemView.findViewById(R.id.orderimg);
            Cardorder = itemView.findViewById(R.id.ordercard);
            ordercellnum = itemView.findViewById(R.id.ordercellnumber);
            orderaddress = itemView.findViewById(R.id.orderaddress);
            orderstatus = itemView.findViewById(R.id.orderstatus);


            Cardorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onorderclick != null) {
                        onorderclick.onClick(getAdapterPosition(), Cardorder);
                    }

                    notifyDataSetChanged();

                }
            });

        }


    }
}
