package com.example.foodapplicationcustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapplicationcustomer.Interface.ItemClickListener;
import com.example.foodapplicationcustomer.ViewHolder.MenueViewHolder;
import com.example.foodapplicationcustomer.support.CurrentUser;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference category;

    TextView fullName;

    RecyclerView menuRecycler;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        //set up firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        category =  firebaseDatabase.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_settings, R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);
        fullName = (TextView) headerView.findViewById(R.id.fullName);
        fullName.setText(CurrentUser.currentProfile.getUserName());
        menuRecycler = (RecyclerView) findViewById(R.id.navrecycler);
        menuRecycler.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        menuRecycler.setLayoutManager(layoutManager);
        loadmenu();

    }

    public void loadmenu(){

        FirebaseRecyclerOptions<Category> config = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category,Category.class)
                .build();

        FirebaseRecyclerAdapter<Category, MenueViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenueViewHolder>(config){
            @NonNull
            @Override
            public MenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.categoryholder,parent,false);
                return   new MenueViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenueViewHolder holder, int position,@NonNull Category model) {
                holder.textMenueName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imageViewMenue);
                Category clickitem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, Boolean isLongClick) {
                        Toast.makeText(Home.this, ""+clickitem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        menuRecycler.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}