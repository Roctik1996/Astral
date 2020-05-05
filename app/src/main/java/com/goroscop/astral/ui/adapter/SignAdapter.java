package com.goroscop.astral.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goroscop.astral.R;
import com.goroscop.astral.utils.Const;

import java.util.ArrayList;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.SignViewHolder> {

    public interface ItemClick{
        void onClick(int position);
    }

    ItemClick itemClick;

    private ArrayList<String> data;

    public SignAdapter(ArrayList<String> data, ItemClick itemClick) {
        this.itemClick=itemClick;
        this.data = data;
    }

    @NonNull
    @Override
    public SignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partner, viewGroup, false);
        return new SignViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SignViewHolder signViewHolder, int i) {
        signViewHolder.iconSign.setImageResource(Const.avatarIcon.get(data.get(i)));
        signViewHolder.txtSign.setText(data.get(i)+"\n"+Const.datesSign.get(data.get(i)));
        signViewHolder.itemView.setOnClickListener(v -> itemClick.onClick(i));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class SignViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView txtSign;
        ImageView iconSign;

        SignViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSign = itemView.findViewById(R.id.txt_sign);
            iconSign = itemView.findViewById(R.id.icon_sign);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onItemClick(view, getAdapterPosition());
        }
    }
    public void onItemClick(View view, int position) {
        Log.d("Item click", String.valueOf(position));
    }
}
