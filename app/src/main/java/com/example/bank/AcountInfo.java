package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AcountInfo extends AppCompatActivity {
    private TextView name, email, balance, send, recieve;
    private User user;
    private MyDatabase database;
    private int id;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_info);

        getSupportActionBar().hide();

        init();
        getUserInfo();


    }

    private void init() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        balance = findViewById(R.id.balance);
        send = findViewById(R.id.send);
        recieve = findViewById(R.id.recieve);

        database = MyDatabase.getInstance(getApplicationContext());
        bundle = getIntent().getExtras();
    }

    private void getUserInfo() {
        id = bundle.getInt("id");
        user = database.getUser(id);
        name.setText(user.getUsername());
        email.setText(user.getEmail());
        balance.setText(String.valueOf(user.getCurrentBalance()) + "₹");
        send.setText(String.valueOf(database.getNumOfSending(user)));
        recieve.setText(String.valueOf(database.getNumOfReceiving(user)));
    }

    public void sendTransactions(View view) {
        startActivity(new Intent(getApplicationContext(), TransactionSend.class).putExtras(bundle));
    }

    public void receiveTransactions(View view) {
        startActivity(new Intent(getApplicationContext(), TransactionReceive.class).putExtras(bundle));
    }

    public void enterAmount(View view) {
        AlertDialog.Builder d = new AlertDialog.Builder(AcountInfo.this);
        View v = getLayoutInflater().inflate(R.layout.transfer_amount, null);
        d.setTitle("Enter Amount")
                .setView(v)
                .setPositiveButton("enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = v.findViewById(R.id.enter_money);
                        float amount = Float.parseFloat(editText.getText().toString());
                        if (amount <= user.getCurrentBalance() && amount > 0) {
                            bundle.putFloat("amount", amount);
                            startActivity(new Intent(getApplicationContext(), CustomerToTransfer.class).putExtras(bundle));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        d.show();
    }
}