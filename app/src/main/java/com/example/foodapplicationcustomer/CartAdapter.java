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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartholder>{



    List<OrderModel> data;
    Context context;


    OnCartclick oncartclick;

    public interface OnCartclick{
        void onClick(int pos,View view);
    }



    public CartAdapter(List<OrderModel> data, Context context, OnCartclick oncartclick) {
        this.context = context;
        this.oncartclick = oncartclick;
        this.data = data;
    }



    @NonNull
    @Override
    public CartAdapter.cartholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cartholder,parent, false);

        return new CartAdapter.cartholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CartAdapter.cartholder holder, int position) {

    holder.itemname.setText(data.get(position).getQuantity()+" x "+data.get(position).getProductname());
    holder.itemprice.setText(data.get(position).getPrice());
    try {
            Picasso.with(context).load(data.get(position).getImageLink()).into(holder.countImg);

    }catch(Exception e){
        holder.countImg.setImageResource(R.drawable.ic_baseline_fastfood_24);

        }

    //





    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class cartholder extends RecyclerView.ViewHolder {


        TextView itemname, itemprice;
        ImageView countImg;
        CardView Cardcart;
        LinearLayout linearLayout;


        public cartholder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemName);
            itemprice = itemView.findViewById(R.id.itemprice);
            countImg = itemView.findViewById(R.id.itemcount);
            Cardcart = itemView.findViewById(R.id.cartcard);



            Cardcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(oncartclick != null){
                        oncartclick.onClick(getAdapterPosition(),Cardcart);
                    }

                    notifyDataSetChanged();

                }
            });

        }


    }



}





