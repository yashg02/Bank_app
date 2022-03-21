package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class TransactionReceive extends AppCompatActivity {
    private int id;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Transfer> transfers;
    private SendAdapter sendAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_receive);getSupportActionBar().hide();


        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        transfers = MyDatabase.getInstance(getApplicationContext()).getTransferAsReceiver(id);
        recyclerView = findViewById(R.id.receives_recycle_view);
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        sendAdapter = new SendAdapter(transfers, "receive", getApplicationContext());
        recyclerView.setAdapter(sendAdapter);
        sendAdapter.notifyDataSetChanged();
    }
}