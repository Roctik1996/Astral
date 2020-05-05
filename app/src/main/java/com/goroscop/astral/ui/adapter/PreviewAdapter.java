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
import com.goroscop.astral.model.Preview;

import java.util.ArrayList;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.CountViewHolder> {

    private ArrayList<Preview> data;

    public PreviewAdapter(ArrayList<Preview> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_preview, viewGroup, false);
        return new CountViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountViewHolder countViewHolder, int i) {
        countViewHolder.txtPreview.setText(""+data.get(i).getInfo());
        countViewHolder.iconPreview.setImageResource(data.get(i).getIconResource());
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

        TextView txtPreview;
        ImageView iconPreview;

        CountViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPreview = itemView.findViewById(R.id.txt_preview);
            iconPreview = itemView.findViewById(R.id.icon_preview);

        }
    }
}
