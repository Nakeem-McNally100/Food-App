package com.example.foodapplicationcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapplicationcustomer.support.CurrentUser;
import com.example.foodapplicationcustomer.support.DatabaseHelper;
import com.example.foodapplicationcustomer.support.OrderModel;
import com.example.foodapplicationcustomer.support.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class Cart extends AppCompatActivity {


    FoodItem foodItem;
    int counter = 0;
    DatabaseReference fooddatabase;
    RecyclerView recyclerView;
    TextView totalprice;
    Button placeOrder;
    FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseHelper databaseHelper;
    RecyclerView.LayoutManager layoutManager;
    List<OrderModel> cart = new ArrayList<OrderModel>();

    CartAdapter cartAdapter;
    float total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.mycart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        totalprice = (TextView)findViewById(R.id.cartTotal);
        placeOrder = (Button)findViewById(R.id.placeOrderbtn);

        databaseHelper = new DatabaseHelper(Cart.this);

        cart = databaseHelper.getCart();
        total = 0;


         for(int i = 0;i<cart.size();i++){
            float val = Float.valueOf(cart.get(i).getPrice());
             total = total + val;
             //Toast.makeText(Cart.this, "Food pulled!- "+cart.get(counter).getProductname(), Toast.LENGTH_SHORT).show();
             //
                counter++;
            }


        totalprice.setText(String.valueOf(total));

        cartAdapter = new CartAdapter(cart, Cart.this, new CartAdapter.OnCartclick() {
            @Override
            public void onClick(int pos,View view) {

                PopupMenu popup = new PopupMenu(view.getContext(),view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.cartpopup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()){

                            case R.id.delete:
                                databaseHelper.deleteOrder(cart.get(pos).getProductID());
                                cart.remove(pos);
                                cartAdapter.notifyDataSetChanged();
                                //Intent opencart =  new Intent(Cart.this,Cart.class);
                                //startActivity(opencart);
                                return true;

                            case R.id.update:
                                Intent openitem =  new Intent(Cart.this,UpdateOrder.class);
                                ArrayList<String> myList = new ArrayList<String>();
                                myList.add(cart.get(pos).getPrice());
                                myList.add(cart.get(pos).getQuantity());
                                myList.add(cart.get(pos).getProductname());
                                myList.add(cart.get(pos).getProductID());
                                openitem.putExtra("mylist", myList);
                                openitem.putExtra("FoodName",cart.get(pos).getProductname());
                                startActivity(openitem);
                                finish();
                                return true;

                            default:
                                return false;
                        }


                    }
                });


                popup.show();//showing popup menu

            }
        });
        cartAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(cartAdapter);


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PladeOrderAlert();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent openhome =  new Intent(Cart.this,MainActivity.class);
        startActivity(openhome);

    }


    public void PladeOrderAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Almost there!");
        alertDialog.setMessage("Please enter your address: ");

        final EditText addrText = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );
        //final Request orderRequest;

        addrText.setLayoutParams(layoutParams);
        alertDialog.setView(addrText);
        alertDialog.setIcon(R.drawable.shopping_cart);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Request orderRequest = new Request(
                        CurrentUser.currentProfile.getCellNumber(),
                        CurrentUser.currentProfile.getUserName(),
                        addrText.getText().toString(),
                        totalprice.getText().toString(),
                        cart
                );


                requests.child(String.valueOf(System.currentTimeMillis())).setValue(orderRequest);
                databaseHelper.deleteCart();
                Toast.makeText(Cart.this, "Thank you for your order!", Toast.LENGTH_SHORT).show();
                finish();

            }

        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }

}