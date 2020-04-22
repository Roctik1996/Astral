package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.NavigationInterface;

@SuppressLint("ViewConstructor")
public class NavigationLayout extends ConstraintLayout {

    NavigationInterface navigationInterface;

    public NavigationLayout(ConstraintLayout parent,Activity activity) {
        super(activity);
        initView(parent,activity);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView(ConstraintLayout parent, Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.drawer_layout, parent, true);
        LinearLayout navHome = view.findViewById(R.id.layout_home);
        LinearLayout navCompatibility = view.findViewById(R.id.layout_compatibility);
        LinearLayout navChina = view.findViewById(R.id.layout_china);
        LinearLayout navElement = view.findViewById(R.id.layout_element);
        LinearLayout navMetal = view.findViewById(R.id.layout_metal);
        LinearLayout navPlanet = view.findViewById(R.id.layout_planet);

        navigationInterface =(NavigationInterface) activity;
        navHome.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });

        navCompatibility.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });

        navChina.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });

        navElement.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });

        navMetal.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });

        navPlanet.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });

    }


}