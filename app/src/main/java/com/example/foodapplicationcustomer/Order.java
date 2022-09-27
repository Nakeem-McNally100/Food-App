package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.foodapplicationcustomer.support.CurrentUser;
import com.example.foodapplicationcustomer.support.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    OrderAdapter orderAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        recyclerView = (RecyclerView) findViewById(R.id.myorder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<Request> orderedItems  = new ArrayList<>();
        String cellnum = CurrentUser.currentProfile.getCellNumber();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set news feed button selected
        bottomNavigationView.setSelectedItemId(R.id.paymentbtn);


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
                        startActivity(new Intent( getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.paymentbtn:
                        return true;

                }
                return false;
            }
        });




       // Toast.makeText(getBaseContext(), "user cell: "+ cellnum, Toast.LENGTH_SHORT).show();

        requests.orderByChild("cellNumber").equalTo(cellnum).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot: snapshot.getChildren()){
                    Request orders = datasnapshot.getValue(Request.class);
                    //Toast.makeText(getBaseContext(), "order address: "+ orders.getAddress(), Toast.LENGTH_SHORT).show();
                    orderedItems.add(orders);

                }

                 orderAdapter = new OrderAdapter(orderedItems, Order.this, new OrderAdapter.OnOrderclick() {
                    @Override
                    public void onClick(int pos, View view) {



                    }


                });

                recyclerView.setAdapter(orderAdapter);
                //orderAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}