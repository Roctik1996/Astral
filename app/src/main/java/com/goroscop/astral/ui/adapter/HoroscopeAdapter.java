package com.goroscop.astral.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goroscop.astral.R;

import java.util.ArrayList;

public class HoroscopeAdapter extends RecyclerView.Adapter<HoroscopeAdapter.HoroscopeViewHolder> {

    private ArrayList<String> data;
    private View txt;

    public HoroscopeAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public HoroscopeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_horoscope, viewGroup, false);
        return new HoroscopeViewHolder(view);
    }


    public View getView(){
        return txt;
    }
    public void setTxt(View txt) {
        this.txt = txt;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HoroscopeViewHolder horoscopeViewHolder, int i) {
        horoscopeViewHolder.txtHoroscope.setText(""+data.get(i));
        setTxt(horoscopeViewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    static class HoroscopeViewHolder extends RecyclerView.ViewHolder {

        TextView txtHoroscope;

        HoroscopeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHoroscope = itemView.findViewById(R.id.txt_horoscope);
        }
    }
}
