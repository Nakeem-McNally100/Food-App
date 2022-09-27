package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    Button logOut, editAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logOut = (Button) findViewById(R.id.logOutbtn);
        editAcc = (Button) findViewById(R.id.editAccountbtn);



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openhome =  new Intent(Profile.this,LogIn.class);
                openhome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(openhome);
            }
        });

        editAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent edit =  new Intent(Profile.this, EditAccount.class);
                startActivity(edit);

            }
        });




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set news feed button selected
        bottomNavigationView.setSelectedItemId(R.id.profilebtn);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.profilebtn:
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
                        startActivity(new Intent( getApplicationContext(),Order.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });





    }








}