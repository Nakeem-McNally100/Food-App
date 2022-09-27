package com.example.foodapplicationcustomer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder>{

    List<FoodItem> data;
    int selectedItem = 0;
    Context context;

    OnFoodclick onfoodclick;

    public interface OnFoodclick{
        void onClick(int pos);
    }


    public FoodAdapter(List<FoodItem>data, Context context, OnFoodclick onfoodclick){
        this.context = context;
        this.onfoodclick = onfoodclick;
        this.data = data;
    }


    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.food_holder,parent, false);

        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.price.setText("$"+ data.get(position).getPrice());
        holder.title.setText(data.get(position).getName());
        //holder.ratingBar.setRating(data.get(position).getRating());
        //holder.image.setImageResource(data.get(position).getImage());
        //holder.image.setImageResource(data.get(position).getRes());
        if(data.get(position).getImage()!="") {

            try {
                Picasso.with(context).load(data.get(position).getImage()).into(holder.image);
            }catch(Exception e){
                holder.image.setImageResource(data.get(position).getRes());
            }
        }else{

            holder.image.setImageResource(data.get(position).getRes());
        }


/*
        if(selectedItem == position){
            holder.cardView.animate().scaleX(1.1f);
            holder.cardView.animate().scaleY(1.1f);
            holder.title.setTextColor(Color.WHITE);
            holder.price.setTextColor(Color.WHITE);
            holder.linearLayout.setBackgroundResource(R.color.colorRed);
        }else{
            holder.cardView.animate().scaleX(1);
            holder.cardView.animate().scaleY(1);
            holder.title.setTextColor(Color.BLACK);
            holder.price.setTextColor(Color.BLACK);
            holder.linearLayout.setBackgroundResource(R.color.colorWhite);
        }
*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBar;
        ImageView image;
        TextView title;
        TextView price;
        CardView cardView;
        LinearLayout linearLayout;

        public FoodHolder(View holder){
            super(holder);
           // ratingBar = holder.findViewById(R.id.rating);
            image = holder.findViewById(R.id.ItemImage);
            title = holder.findViewById(R.id.Itemtitle);
            price  = holder.findViewById(R.id.Itemprice);
            cardView = holder.findViewById(R.id.foodCard);
            linearLayout = holder.findViewById(R.id.linearlayoutback);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    if(onfoodclick != null){
                        onfoodclick.onClick(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }
            });

        }


    }


}
