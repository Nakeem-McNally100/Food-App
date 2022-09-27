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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText SignupEmail;
    EditText password;
    EditText firstname, lastname, cellnumber, username;
    EditText confirmpassword;
    Button SignUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignupEmail = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.Username);
        cellnumber = (EditText) findViewById(R.id.Cellnumber);
        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);
        confirmpassword = (EditText) findViewById(R.id.comfirmpassword);
        password = (EditText) findViewById(R.id.password);
        SignUpButton = (Button) findViewById(R.id.conbtn);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myUserTable = database.getReference("User");


        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Please wait....");
                progressDialog.show();

                myUserTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String userName = username.getText().toString().trim();
                        String firstName = firstname.getText().toString().trim();
                        String lastName = lastname.getText().toString().trim();
                        String STRpassword = password.getText().toString().trim();
                        String STRconfirmpassword = confirmpassword.getText().toString().trim();
                        String email = SignupEmail.getText().toString().trim();
                        String cellNumber = cellnumber.getText().toString().trim();

                        if(userName.isEmpty()||firstName.isEmpty()||lastName.isEmpty()||STRconfirmpassword.isEmpty()||STRpassword.isEmpty()||email.isEmpty()||cellNumber.isEmpty()){
                            Toast.makeText(SignUp.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                        }else if(snapshot.child(cellnumber.getText().toString().trim()).exists()){
                            Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else{
                            if(STRpassword.equals(STRconfirmpassword)){

                                User user = new User(userName,firstName,lastName, STRpassword, email, cellNumber);
                                myUserTable.child(cellnumber.getText().toString().trim()).setValue(user);
                                Toast.makeText(SignUp.this, "User created", Toast.LENGTH_SHORT).show();
                                Intent dash =  new Intent(SignUp.this,MainActivity.class);
                                CurrentUser.currentProfile = user;
                                startActivity(dash);
                                finish();
                                progressDialog.dismiss();
                            }else{
                                Toast.makeText(SignUp.this, "User creation Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }


                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {



                    }
                });





            }
        });



    }
}