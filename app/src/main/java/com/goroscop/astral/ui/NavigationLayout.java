package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.NavigationInterface;

@SuppressLint("ViewConstructor")
public class NavigationLayout extends Fragment {

    private NavigationInterface navigationInterface;

    NavigationLayout(Context context) {
        navigationInterface = (NavigationInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_layout, container, false);
        LinearLayout navHome = view.findViewById(R.id.layout_home);
        LinearLayout navCompatibility = view.findViewById(R.id.layout_compatibility);
        LinearLayout navChina = view.findViewById(R.id.layout_china);
        LinearLayout navElement = view.findViewById(R.id.layout_element);
        LinearLayout navMetal = view.findViewById(R.id.layout_metal);
        LinearLayout navPlanet = view.findViewById(R.id.layout_planet);

        navHome.setOnClickListener(v -> navigationInterface.onHomePressed());

        navCompatibility.setOnClickListener(v -> navigationInterface.onCompatibilityPressed());

        navChina.setOnClickListener(v -> navigationInterface.onChinaPressed());

        navElement.setOnClickListener(v -> navigationInterface.onElementPressed());

        navMetal.setOnClickListener(v -> navigationInterface.onMetalPressed());

        navPlanet.setOnClickListener(v -> navigationInterface.onPlanetPressed());
        return view;
    }
}