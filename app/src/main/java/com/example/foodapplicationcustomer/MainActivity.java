package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodapplicationcustomer.support.CurrentUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference database, fooddatabase;
    List<FoodItem> foodItems;
    RecyclerView recyclerCategories;
    RecyclerView recyclerfood;
    ImageView imageCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageCart = (ImageView) findViewById(R.id.cartbtn);
        recyclerCategories = findViewById(R.id.recycler);
        recyclerfood = findViewById(R.id.foodrecycler);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set news feed button selected
        bottomNavigationView.setSelectedItemId(R.id.menubotton);

        imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent( getApplicationContext(),Cart.class));
                overridePendingTransition(0,0);
            }
        });

        // perform itemSelectedListener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.profilebtn:
                        startActivity(new Intent( getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.trackerbtn:
                        startActivity(new Intent( getApplicationContext(),Tracking.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.menubotton:
                        return true;

                    case R.id.paymentbtn:
                        startActivity(new Intent( getApplicationContext(),Order.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });



        database = FirebaseDatabase.getInstance().getReference("Category");
        recyclerCategories.setHasFixedSize(true);

        recyclerCategories.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
        List<FoodCategory> data = new ArrayList<>();



        FoodCategoryadapter foodCategoryadapter = new FoodCategoryadapter(data, MainActivity.this, new FoodCategoryadapter.OnCategoryclick() {
            @Override
            public void onClick(int pos) {
                foodItems  = new ArrayList<>();
                database.orderByChild("Name").equalTo(data.get(pos).getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String foodkey = "";
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            foodkey = childSnapshot.getKey();

                        }

                        fooddatabase = FirebaseDatabase.getInstance().getReference("Food");
                        fooddatabase.orderByChild("MenuID").equalTo(foodkey).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot datasnapshot: snapshot.getChildren()){
                                    FoodItem foodItem = datasnapshot.getValue(FoodItem.class);
                                    foodItems.add(foodItem);
                                }
                                FoodAdapter foodAdapter = new FoodAdapter(foodItems, MainActivity.this, new FoodAdapter.OnFoodclick() {
                                    @Override
                                    public void onClick(int pos) {

                                        Intent openitem =  new Intent(MainActivity.this,Menu.class);
                                        openitem.putExtra("FoodName",foodItems.get(pos).getName());
                                        startActivity(openitem);
                                        //Toast.makeText(MainActivity.this, "Food clicked!- "+foodItems.get(pos).getName(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                RecyclerView.LayoutManager gridlayout = new GridLayoutManager(MainActivity.this,2);
                                //recyclerfood.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false));
                                recyclerfood.setLayoutManager(gridlayout);
                                recyclerfood.setAdapter(foodAdapter);
                                //foodAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        recyclerCategories.setAdapter(foodCategoryadapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot: snapshot.getChildren()){
                    FoodCategory foodCategory = datasnapshot.getValue(FoodCategory.class);
                    data.add(foodCategory);
                }
                foodCategoryadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fooddatabase = FirebaseDatabase.getInstance().getReference("Food");
        fooddatabase.orderByChild("MenuID").equalTo("01").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodItems  = new ArrayList<>();
                for(DataSnapshot datasnapshot: snapshot.getChildren()){
                    FoodItem foodItem = datasnapshot.getValue(FoodItem.class);
                    foodItems.add(foodItem);

                }
                FoodAdapter foodAdapter = new FoodAdapter(foodItems, MainActivity.this, new FoodAdapter.OnFoodclick() {
                    @Override
                    public void onClick(int pos) {

                        Intent openitem =  new Intent(MainActivity.this,Menu.class);
                        openitem.putExtra("FoodName",foodItems.get(pos).getName());
                        startActivity(openitem);
                        //Toast.makeText(MainActivity.this, "Food clicked!- "+foodItems.get(pos).getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                /// , RecyclerView.VERTICAL,false
                RecyclerView.LayoutManager gridlayout = new GridLayoutManager(MainActivity.this,2);
                //recyclerfood.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false));
                recyclerfood.setLayoutManager(gridlayout);
                recyclerfood.setAdapter(foodAdapter);
                //foodAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private long backpresstime;
    private Toast toast;

    @Override
    public void onBackPressed() {

        if(backpresstime+2000> System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            Intent openhome =  new Intent(MainActivity.this,LogIn.class);
            openhome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(openhome);
        }else{
            toast =  Toast.makeText(getBaseContext(), "Press Back again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }
        backpresstime = System.currentTimeMillis();

    }



    // Intent signIn = new Intent(MainActivity.this, LogIn.class);
    // signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
    // startActivity(SignIn);


}