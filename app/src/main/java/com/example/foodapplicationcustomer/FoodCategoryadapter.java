package com.example.foodapplicationcustomer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodCategoryadapter extends RecyclerView.Adapter<FoodCategoryadapter.Categoryholder> {

    List<FoodCategory> data;
    Context context;
    int selectedItem = 0;
    OnCategoryclick onCategoryclick;

    public interface OnCategoryclick{
        void onClick(int pos);
    }

    public FoodCategoryadapter(List<FoodCategory> data, Context context, OnCategoryclick onCategoryclick){
        this.data = data;
        this.context = context;
        this.onCategoryclick = onCategoryclick;
    }

    @NonNull
    @Override
    public Categoryholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.categoryholder, parent, false);
        return new Categoryholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Categoryholder holder, int position) {
        holder.image.setImageResource(data.get(position).getRes());
        //holder.image
       // Picasso.
        //Picasso.get().load(data.get(position).getRes()).into(holder.image);
        //holder.image.setImageResource(R.drawable.ic_baseline_fastfood_24);
        //Picasso.with(context).load(data.get(position).getImage()).into(holder.image);
        holder.title.setText(data.get(position).getName());
        if(position == selectedItem){
           //holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.colorRed));
            //holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.colorRed));
            //holder.cardView.setStrokeWidth(2);
            //holder.title.setTextColor(context.getColor(R.color.colorRed));
            holder.cardView.animate().scaleX(1.1f);
            holder.cardView.animate().scaleY(1.1f);
            holder.title.setTextColor(Color.parseColor("#FF6857"));
          //  holder.linearLayout.setBackgroundResource(R.color.colorRed);
            holder.image.setColorFilter(ContextCompat.getColor(context,R.color.colorOrange), PorterDuff.Mode.SRC_IN);
        }else{
            //holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.colorGrey));
            //holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.colorGrey));
            //holder.title.setTextColor(context.getColor(R.color.colorGrey));

            holder.cardView.animate().scaleX(1);
            holder.cardView.animate().scaleY(1);
            holder.title.setTextColor(Color.parseColor("#191C2C"));

            //holder.linearLayout.setBackgroundResource(R.color.colorWhite);

            holder.image.setColorFilter(ContextCompat.getColor(context,R.color.colordarken), PorterDuff.Mode.SRC_IN);
        }
//"#F30000"

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Categoryholder extends RecyclerView.ViewHolder{
        //public View linearLayout;
        TextView title;
        ImageView image;
        CardView cardView;
        public Categoryholder(View holder){
            super(holder);
            title = holder.findViewById(R.id.foodtitle);
            image = holder.findViewById(R.id.foodimage);
            cardView = holder.findViewById(R.id.card);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem = getAdapterPosition();

                if(onCategoryclick != null){
                    onCategoryclick.onClick(getAdapterPosition());
                }

                notifyDataSetChanged();
            }
        });

        }

    }

 }

