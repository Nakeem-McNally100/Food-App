package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapplicationcustomer.Interface.ItemClickListener;
import com.example.foodapplicationcustomer.ViewHolder.MenueViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Mainmenu extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;

    DatabaseReference category;

    TextView fullName;

    RecyclerView menuRecycler;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);


        firebaseDatabase = FirebaseDatabase.getInstance();
        category =  firebaseDatabase.getReference("Category");
        menuRecycler = (RecyclerView) findViewById(R.id.navrecycler);
        menuRecycler.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(Mainmenu.this,RecyclerView.HORIZONTAL, false);
        menuRecycler.setLayoutManager(layoutManager);
        loadmenu();



    }

    public void loadmenu(){

        FirebaseRecyclerOptions<Category> config = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category,Category.class)
                .build();

        FirebaseRecyclerAdapter<Category, MenueViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenueViewHolder>(config){
            @NonNull
            @Override
            public MenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.categoryholder,parent,false);
                return   new MenueViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenueViewHolder holder, int position,@NonNull Category model) {
                holder.textMenueName.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imageViewMenue);
                Category clickitem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, Boolean isLongClick) {
                        Toast.makeText(Mainmenu.this, ""+clickitem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        menuRecycler.setAdapter(adapter);

    }


}