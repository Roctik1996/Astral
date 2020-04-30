package com.goroscop.astral.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;

public class HoroscopeFragment extends Fragment {

    private String txt;
    private int success;

    public HoroscopeFragment(String txt, int success) {
        this.txt = txt;
        this.success = success;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horoscope, container, false);
        TextView txtHoroscope = view.findViewById(R.id.txt_horoscope);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView txtSuccessProgress = view.findViewById(R.id.txt_success_progress);
        TextView txtSuccess = view.findViewById(R.id.txt_success);
        txtHoroscope.setText(txt);
        if (success != -1) {
            txtSuccessProgress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            txtSuccess.setVisibility(View.VISIBLE);
            progressBar.setProgress(success);
            txtSuccessProgress.setText(success + "%");
        } else {
            txtSuccessProgress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            txtSuccess.setVisibility(View.GONE);
        }
        return view;
    }

}
