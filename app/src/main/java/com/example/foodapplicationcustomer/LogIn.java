package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapplicationcustomer.support.CurrentUser;
import com.example.foodapplicationcustomer.support.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    Button loginbtn, createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        loginEmail = (EditText) findViewById(R.id.loginemail);
        loginPassword = (EditText) findViewById(R.id.loginpassword);
        loginbtn = (Button)findViewById(R.id.continueloginbtn);
        createAccount = (Button)findViewById(R.id.createAccount);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myUserTable = database.getReference("User");

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUp =  new Intent(LogIn.this,SignUp.class);
                startActivity(SignUp);
            }
        });


        Query chechUser =  myUserTable.orderByChild("Email").equalTo(loginPassword.getText().toString().trim());
        loginbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

               // final ProgressDialog progressDialog = new ProgressDialog(LogIn.this);
                //progressDialog.setMessage("Please wait....");
                //progressDialog.show();

                myUserTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // GET User info
                      //  progressDialog.dismiss();
                        //snapshot.child(loginEmail.getText().toString().trim()).exists(); -- Checks if a user exists
                        if(snapshot.exists()) {

                            if (snapshot.child(loginEmail.getText().toString().trim()).exists()) {
                              //  Toast.makeText(LogIn.this, "Child exists", Toast.LENGTH_SHORT).show();
                              //  String userpassword = snapshot.child(loginEmail.getText().toString().trim()).child("password").getValue(String.class);
                                User user = snapshot.child(loginEmail.getText().toString().trim()).getValue(User.class);

                          //  Toast.makeText(LogIn.this, "The passwords are : "+ userpassword, Toast.LENGTH_SHORT).show();

                                if (user.getPassword().equals(loginPassword.getText().toString().trim())) {
                                  //  Toast.makeText(LogIn.this, "Sign In was successful", Toast.LENGTH_SHORT).show();
                                    Intent dash =  new Intent(LogIn.this,MainActivity.class);

                                    CurrentUser.currentProfile = user;
                                    startActivity(dash);
                                    finish();
                                } else {
                                    Toast.makeText(LogIn.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(LogIn.this, "Account doesn't exists. Please create an Account.. ;)", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }



    @Override
    public void onBackPressed() {
            super.onBackPressed();
            System.exit(0);
    }
}


