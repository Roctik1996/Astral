package com.goroscop.astral.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;

public class HoroscopeFragment extends Fragment {

    private TextView txtHoroscope;
    private String txt;

    HoroscopeFragment(String txt) {
        this.txt=txt;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horoscope, container, false);
        txtHoroscope = view.findViewById(R.id.txt_horoscope);
        txtHoroscope.setText(txt);
        return view;
    }

}
