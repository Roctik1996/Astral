package com.goroscop.astral.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goroscop.astral.R;

import java.util.ArrayList;

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.CountViewHolder> {

    private ArrayList<Integer> data;

    public CountAdapter(ArrayList<Integer> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reg_count, viewGroup, false);
        return new CountViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountViewHolder countViewHolder, int i) {
        countViewHolder.itemCount.setText(""+data.get(i));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class CountViewHolder extends RecyclerView.ViewHolder {

        TextView itemCount;
        ImageView bgCount;

        CountViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCount = itemView.findViewById(R.id.txt_count);
            bgCount = itemView.findViewById(R.id.bg_counter);

        }
    }
}
