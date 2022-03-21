package com.example.bank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

public class ViewAllCustomers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ViewCustomersAdapter viewCustomersAdapter;
    private MyDatabase database;
    private ArrayList<User> users ;

    @SuppressLint("NotifyDataSetChanged")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_customers);

        recyclerView = findViewById(R.id.customer_recycle_view);
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        database = MyDatabase.getInstance(getApplicationContext());
        users = database.getAllUser();
        viewCustomersAdapter = new ViewCustomersAdapter(users);
        recyclerView.setAdapter(viewCustomersAdapter);
        viewCustomersAdapter.notifyDataSetChanged();


    }
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
                viewCustomersAdapter = new ViewCustomersAdapter(users);
                recyclerView.setAdapter(viewCustomersAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                users = database.getMatchedUser(s);
                viewCustomersAdapter = new ViewCustomersAdapter(users);
                recyclerView.setAdapter(viewCustomersAdapter);

                return true;
            }
        });
        return true;
    }
}