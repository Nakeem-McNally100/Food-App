package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tracking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);







        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set news feed button selected
        bottomNavigationView.setSelectedItemId(R.id.trackerbtn);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.profilebtn:
                        startActivity(new Intent( getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.trackerbtn:
                        return true;


                    case R.id.menubotton:
                        startActivity(new Intent( getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.paymentbtn:
                        startActivity(new Intent( getApplicationContext(),Order.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });






    }
}