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

public class LuckyNumAdapter extends RecyclerView.Adapter<LuckyNumAdapter.LuckyViewHolder> {

    private ArrayList<String> data;

    public LuckyNumAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LuckyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lacky_num, viewGroup, false);
        return new LuckyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LuckyViewHolder luckyViewHolder, int i) {
        luckyViewHolder.itemCount.setText(""+data.get(i));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class LuckyViewHolder extends RecyclerView.ViewHolder {

        TextView itemCount;
        ImageView bgCount;

        LuckyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCount = itemView.findViewById(R.id.txt_count);
            bgCount = itemView.findViewById(R.id.bg_counter);

        }
    }
}
