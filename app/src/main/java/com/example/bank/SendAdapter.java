package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SendAdapter extends RecyclerView.Adapter<SendAdapter.ViewHolder> {
    String type;
    Context context;
    ArrayList<Transfer> transfers;

    public SendAdapter(ArrayList<Transfer> transfers, String type, Context context) {
        this.transfers = transfers;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public SendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_card, parent, false);
        SendAdapter.ViewHolder viewHolder = new SendAdapter.ViewHolder(vv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendAdapter.ViewHolder holder, int position) {
        Transfer transfer = transfers.get(position);
        User user;
        if (!type.equals("sender")) {
            user = MyDatabase.getInstance(context).getUser(transfer.getSenderID());
        } else {
            user = MyDatabase.getInstance(context).getUser(transfer.getReceiverID());
        }
        holder.name.setText(user.getUsername());
        holder.amount.setText(String.valueOf(transfer.getAmount()) + "₹");
        holder.date.setText(transfer.getDateTime());


    }

    @Override
    public int getItemCount() {
        return transfers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, amount, date;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.customer_name_sr);
            amount = itemView.findViewById(R.id.customer_amount_sr);
            date = itemView.findViewById(R.id.trans_date);

        }
    }
}


