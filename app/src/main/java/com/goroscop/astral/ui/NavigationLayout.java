package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.fragment.MetalFragment;
import com.goroscop.astral.ui.interfaces.NavigationInterface;

@SuppressLint("ViewConstructor")
public class NavigationLayout extends Fragment {

    NavigationInterface navigationInterface;

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

        navigationInterface =(NavigationInterface) getActivity();
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
            /*FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame, new MetalFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();*/
        });

        navPlanet.setOnClickListener(v -> {
            navigationInterface.onHomePressed();
        });
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView(ConstraintLayout parent, Activity activity, FragmentManager fm) {
        View view = LayoutInflater.from(activity).inflate(R.layout.drawer_layout, parent, true);

    }


}