package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.NavigationInterface;

@SuppressLint("ViewConstructor")
public class DrawerFragment extends Fragment {

    private NavigationInterface navigationInterface;
    private boolean isPro;

    DrawerFragment(Context context, boolean isPro) {
        navigationInterface = (NavigationInterface) context;
        this.isPro = isPro;
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
        TextView txtElement = view.findViewById(R.id.nav_element);
        TextView txtMetal = view.findViewById(R.id.nav_metal);
        TextView txtPlanet = view.findViewById(R.id.nav_planet);
        ImageView iconProElement = view.findViewById(R.id.pro_element);
        ImageView iconProMetal = view.findViewById(R.id.pro_metal);
        ImageView iconProPlanet = view.findViewById(R.id.pro_planet);

        if (isPro){
            txtElement.setAlpha(1f);
            txtMetal.setAlpha(1f);
            txtPlanet.setAlpha(1f);
            iconProElement.setVisibility(View.GONE);
            iconProMetal.setVisibility(View.GONE);
            iconProPlanet.setVisibility(View.GONE);
        }
        else {
            txtElement.setAlpha(0.5f);
            txtMetal.setAlpha(0.5f);
            txtPlanet.setAlpha(0.5f);
            iconProElement.setVisibility(View.VISIBLE);
            iconProMetal.setVisibility(View.VISIBLE);
            iconProPlanet.setVisibility(View.VISIBLE);
        }



        navHome.setOnClickListener(v -> navigationInterface.onHomePressed());

        navCompatibility.setOnClickListener(v -> navigationInterface.onCompatibilityPressed());

        navChina.setOnClickListener(v -> navigationInterface.onChinaPressed());

        navElement.setOnClickListener(v -> navigationInterface.onElementPressed());

        navMetal.setOnClickListener(v -> navigationInterface.onMetalPressed());

        navPlanet.setOnClickListener(v -> navigationInterface.onPlanetPressed());
        return view;
    }
}