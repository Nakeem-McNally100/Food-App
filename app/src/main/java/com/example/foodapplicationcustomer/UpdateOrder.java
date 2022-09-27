package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateOrder extends AppCompatActivity {


    int countval;
    float totalprice;
    FoodItem foodItem;
    DatabaseReference fooddatabase;
    String foodkey;
    TextView heading, name, description, price, total, count;
    Button minus, add, addToCart;
    ImageView backArrow, shoppingbag, foodimage;
    DatabaseHelper databasehelper;
    ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);


        heading = (TextView) findViewById(R.id.updatefoodheading);
        name = (TextView) findViewById(R.id.updatefoodnaming);
        description = (TextView) findViewById(R.id.updatedescription);
        price = (TextView) findViewById(R.id.updatefoodpricing);
        total = (TextView) findViewById(R.id.updatefoodtotal);
        count = (TextView) findViewById(R.id.updatecounter);
        minus = (Button)findViewById(R.id.updateminus);
        add = (Button)findViewById(R.id.updateadd);
        addToCart = (Button)findViewById(R.id.updateaddtocartbtn);
        backArrow = (ImageView) findViewById(R.id.updatebackArrow);
        shoppingbag = (ImageView) findViewById(R.id.updateshoppingbag);
        foodimage = (ImageView) findViewById(R.id.menuImageUpdate);
        databasehelper =  new DatabaseHelper(UpdateOrder.this);




        if (getIntent() != null) {
            foodkey = getIntent().getStringExtra("FoodName");
            //Toast.makeText(Menu.this, "The food key: "+foodkey, Toast.LENGTH_SHORT).show();
             myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
            fooddatabase = FirebaseDatabase.getInstance().getReference("Food");
            fooddatabase.orderByChild("Name").equalTo(foodkey).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot datasnapshot: snapshot.getChildren()){
                        foodItem = datasnapshot.getValue(FoodItem.class);

                    }

                    /*
                    * myList.add(cart.get(pos).getPrice());
                                myList.add(cart.get(pos).getQuantity());
                                myList.add(cart.get(pos).getProductname());
                                myList.add(cart.get(pos).getProductID());
                    *
                    *
                    * */


                    heading.setText(foodItem.getName());
                    name.setText(foodItem.getName());
                    description.setText(foodItem.getDescription());
                    price.setText( "$"+foodItem.getPrice() +".00");
                    total.setText("Total $"+myList.get(0) +"");

                    try {
                        Picasso.with(UpdateOrder.this).load(foodItem.getImage()).into(foodimage);
                    }catch(Exception e) {
                        foodimage.setImageResource(foodItem.getRes());

                    }

                        int val = Integer.valueOf(myList.get(1));

                    if(val < 10){
                        count.setText("0"+String.valueOf(val));

                    }else{
                        count.setText(String.valueOf(val));
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


                //List<OrderModel> cart = new ArrayList<OrderModel>();
                //cart = new DatabaseHelper(Menu.this).getCart();

                //Toast.makeText(UpdateOrder.this, "Food clicked!- "+ ordermodel.getPrice(), Toast.LENGTH_SHORT).show();
                databasehelper.deleteOrder(myList.get(3));

                databasehelper.addOrder(ordermodel);
                Intent opencart =  new Intent(UpdateOrder.this,Cart.class);
                startActivity(opencart);

            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openhome =  new Intent(UpdateOrder.this,MainActivity.class);
                startActivity(openhome);

            }
        });

        shoppingbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent opencart =  new Intent(UpdateOrder.this,Cart.class);
                startActivity(opencart);

            }
        });

    }


    @Override
public void onBackPressed() {
    super.onBackPressed();
    Intent openhome =  new Intent(UpdateOrder.this,MainActivity.class);
    startActivity(openhome);


}
}