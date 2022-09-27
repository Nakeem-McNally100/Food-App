package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditAccount extends AppCompatActivity {


    Button updateAcc;
    EditText updateSignupEmail;
    EditText updatepassword;
    EditText updatefirstname, updatelastname, updatecellnumber, updateusername;
    EditText updateconfirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        updateSignupEmail = (EditText) findViewById(R.id.updateaccemail);
        updateusername = (EditText) findViewById(R.id.updateaccUsername);
        updatecellnumber = (EditText) findViewById(R.id.updateaccCellnumber);
        updatelastname = (EditText) findViewById(R.id.updateacclastname);
        updatefirstname = (EditText) findViewById(R.id.updateaccfirstname);
        updateconfirmpassword = (EditText) findViewById(R.id.updateacccomfirmpassword);
        updatepassword = (EditText) findViewById(R.id.updateaccpassword);
        updateAcc = (Button) findViewById(R.id.updateaccconbtn);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myUserTable = database.getReference("User");


        updateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myUserTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {




                            String userName = updateusername.getText().toString().trim();
                            String firstName = updatefirstname.getText().toString().trim();
                            String lastName = updatelastname.getText().toString().trim();
                            String STRpassword = updatepassword.getText().toString().trim();
                            String STRconfirmpassword = updateconfirmpassword.getText().toString().trim();
                            String email = updateSignupEmail.getText().toString().trim();
                            String cellNumber = updatecellnumber.getText().toString().trim();

                            if(userName.isEmpty()||firstName.isEmpty()||lastName.isEmpty()||STRconfirmpassword.isEmpty()||STRpassword.isEmpty()||email.isEmpty()||cellNumber.isEmpty()){
                                Toast.makeText(EditAccount.this, "Updated Failed", Toast.LENGTH_SHORT).show();
                            }else{
                                if(STRpassword.equals(STRconfirmpassword)){

                                    User user = new User(userName, firstName, lastName, STRpassword, email, cellNumber);
                                    ///myUserTable.child(CurrentUser.currentProfile.getCellNumber()).removeValue();
                                    myUserTable.child(cellNumber).setValue(user);
                                    Toast.makeText(EditAccount.this, "User Updated", Toast.LENGTH_SHORT).show();

                                    Intent dash =  new Intent(EditAccount.this,MainActivity.class);
                                    CurrentUser.currentProfile = user;
                                    startActivity(dash);
                                    finish();
                                }else{
                                    Toast.makeText(EditAccount.this, "Updated Failed", Toast.LENGTH_SHORT).show();
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