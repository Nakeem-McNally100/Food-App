package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapplicationcustomer.support.DatabaseHelper;
import com.example.foodapplicationcustomer.support.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    int countval;
    float totalprice;
    FoodItem foodItem;
    DatabaseReference fooddatabase;
    String foodkey;
    TextView heading, name, description, price, total, count;
    Button minus, add, addToCart;
    ImageView backArrow, shoppingbag, foodimage;
    DatabaseHelper databasehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        heading = (TextView) findViewById(R.id.foodheading);
        name = (TextView) findViewById(R.id.foodnaming);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.foodpricing);
        total = (TextView) findViewById(R.id.foodtotal);
        count = (TextView) findViewById(R.id.counter);
        minus = (Button)findViewById(R.id.minus);
        add = (Button)findViewById(R.id.add);
        addToCart = (Button)findViewById(R.id.addtocartbtn);
        backArrow = (ImageView) findViewById(R.id.backArrow);
        shoppingbag = (ImageView) findViewById(R.id.shoppingbag);
        foodimage = (ImageView) findViewById(R.id.menuImage);
        databasehelper =  new DatabaseHelper(Menu.this);




        if (getIntent() != null) {
            foodkey = getIntent().getStringExtra("FoodName");
            //Toast.makeText(Menu.this, "The food key: "+foodkey, Toast.LENGTH_SHORT).show();

            fooddatabase = FirebaseDatabase.getInstance().getReference("Food");
            fooddatabase.orderByChild("Name").equalTo(foodkey).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot datasnapshot: snapshot.getChildren()){
                        foodItem = datasnapshot.getValue(FoodItem.class);

                    }


                    heading.setText(foodItem.getName());
                    name.setText(foodItem.getName());
                    description.setText(foodItem.getDescription());
                    price.setText( "$"+foodItem.getPrice() +".00");
                    total.setText("Total $"+foodItem.getPrice() +".00");
                    try {
                        Picasso.with(Menu.this).load(foodItem.getImage()).into(foodimage);
                    }catch(Exception e){
                        foodimage.setImageResource(foodItem.getRes());

                    }

                    //Toast.makeText(Menu.this, "The food info: "+foodItem.getName()+"-"+foodItem.getDescription(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });

        }

        if (!foodkey.isEmpty() && foodkey != null) {

        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countval = Integer.valueOf(count.getText().toString());
                countval = countval + 1;

                totalprice = countval*Integer.valueOf(foodItem.getPrice());
                if(countval < 10){
                    count.setText("0"+String.valueOf(countval));

                }else{
                    count.setText(String.valueOf(countval));
                }
                total.setText("Total $"+String.valueOf(totalprice));

            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.valueOf(count.getText().toString()) > 1){
                    countval = Integer.valueOf(count.getText().toString());
                    countval = countval - 1;

                    totalprice = countval*Integer.valueOf(foodItem.getPrice());

                    if(countval < 10){
                        count.setText("0"+String.valueOf(countval));

                    }else{
                        count.setText(String.valueOf(countval));
                    }
                    total.setText("Total $"+String.valueOf(totalprice));

                }


            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countval = Integer.valueOf(count.getText().toString());
                totalprice = countval*Integer.valueOf(foodItem.getPrice());
                OrderModel ordermodel = new OrderModel(foodkey,foodItem.getName(),count.getText().toString(),String.valueOf(totalprice),foodItem.getImage());

                databasehelper.addOrder(ordermodel);
                //List<OrderModel> cart = new ArrayList<OrderModel>();
                //cart = new DatabaseHelper(Menu.this).getCart();

                //Toast.makeText(Menu.this, "Food clicked!- "+ ordermodel.getPrice(), Toast.LENGTH_SHORT).show();

                Intent opencart =  new Intent(Menu.this,MainActivity.class);
                startActivity(opencart);

            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openhome =  new Intent(Menu.this,MainActivity.class);
                startActivity(openhome);

            }
        });

        shoppingbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent opencart =  new Intent(Menu.this,Cart.class);
                startActivity(opencart);
            }
        });
    }
}