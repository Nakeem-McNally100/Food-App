package com.example.foodapplicationcustomer.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplicationcustomer.Interface.ItemClickListener;
import com.example.foodapplicationcustomer.R;


public class MenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textMenueName;
    public ImageView imageViewMenue;

    private ItemClickListener itemClickListener;

    public MenueViewHolder(@NonNull View itemView) {
        super(itemView);
        textMenueName = (TextView) itemView.findViewById(R.id.foodtitle);
        imageViewMenue = (ImageView) itemView.findViewById(R.id.foodimage);

        itemView.setOnClickListener(this);

    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
