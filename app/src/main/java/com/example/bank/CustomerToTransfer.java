package com.example.bank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

public class CustomerToTransfer extends AppCompatActivity {
    private BoundService boundService;
    private User user = new User();
    private int id;
    float amount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> users;
    private CustomerToTransAdapter viewCustomersAdapter;
    private String date = "";
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_to_transfer);


        Intent intent = new Intent(getApplicationContext(), BoundService.class);
        //startService(intent);
        boolean flag = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        database = MyDatabase.getInstance(getApplicationContext());
        recyclerView = findViewById(R.id.customertotrans_recycle_view);
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        amount = b.getFloat("amount");
        user = database.getUser(id);

    }

    private ServiceConnection connection = new ServiceConnection() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public synchronized void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            boundService = myBinder.getService();
            date = boundService.getCurrentTime();
            users = database.getAllUser(id);
            viewCustomersAdapter = new CustomerToTransAdapter(users, amount, id, date);
            recyclerView.setAdapter(viewCustomersAdapter);
            viewCustomersAdapter.notifyDataSetChanged();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SearchView searchView = (SearchView) item.getActionView();
        searchView.onActionViewExpanded();
        searchView.setQueryHint("Enter UserName here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                users = database.getMatchedUser(s);
                users.remove(user);
                viewCustomersAdapter = new CustomerToTransAdapter(users,amount, id, date);
                recyclerView.setAdapter(viewCustomersAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                users = database.getMatchedUser(s);
                users.remove(user);
                viewCustomersAdapter = new CustomerToTransAdapter(users,amount, id, date);
                recyclerView.setAdapter(viewCustomersAdapter);

                return true;
            }
        });
        return true;
    }
}