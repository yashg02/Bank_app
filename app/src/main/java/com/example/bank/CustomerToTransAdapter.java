package com.example.bank;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerToTransAdapter extends RecyclerView.Adapter<CustomerToTransAdapter.ViewHolder> {
    String date;
    float amount;
    int senderId;
    ArrayList<User> users;

    public CustomerToTransAdapter(ArrayList<User> users, float amount, int senderId, String date) {
        this.users = users;
        this.amount = amount;
        this.senderId = senderId;
        this.date = date;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(vv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getUsername());
        holder.amount.setText(String.valueOf(user.getCurrentBalance()) + "₹");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewAllCustomers.class);
                user.setCurrentBalance(user.getCurrentBalance() + amount);
                User senderUser = MyDatabase.getInstance(view.getContext()).getUser(senderId);
                senderUser.setCurrentBalance(senderUser.getCurrentBalance() - amount);
                boolean isUpdate = MyDatabase.getInstance(view.getContext()).updateUser(user);
                boolean isUpateSender = MyDatabase.getInstance(view.getContext()).updateUser(senderUser);

                if (isUpdate && isUpateSender) {
                    Transfer transfer = new Transfer(senderUser.getId(), user.getId(), amount, date);
                    MyDatabase.getInstance(view.getContext()).insertTransfer(transfer);
                }
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, amount;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.name_customer);
            amount = itemView.findViewById(R.id.amount_customer);

        }
    }
}
