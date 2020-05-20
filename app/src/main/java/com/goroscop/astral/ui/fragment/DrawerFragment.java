package com.goroscop.astral.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.avatarIcon;
import static com.goroscop.astral.utils.Utils.getSign;

@SuppressLint("ViewConstructor")
public class DrawerFragment extends Fragment {

    private NavigationInterface navigationInterface;
    private boolean isPro;

    public DrawerFragment(Context context, boolean isPro) {
        navigationInterface = (NavigationInterface) context;
        this.isPro = isPro;
    }

    @SuppressLint("SetTextI18n")
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
        LinearLayout navAbout = view.findViewById(R.id.layout_about);
        LinearLayout navExit = view.findViewById(R.id.layout_exit);
        TextView txtCompatibility = view.findViewById(R.id.nav_compatibility);
        TextView txtChina = view.findViewById(R.id.nav_china);
        TextView txtElement = view.findViewById(R.id.nav_element);
        TextView txtMetal = view.findViewById(R.id.nav_metal);
        TextView txtPlanet = view.findViewById(R.id.nav_planet);
        ImageView iconSign = view.findViewById(R.id.icon_sign);
        TextView txtYear = view.findViewById(R.id.nav_china_year);

        if (isPro) {
            txtCompatibility.setAlpha(1f);
            txtChina.setAlpha(1f);
            txtElement.setAlpha(1f);
            txtMetal.setAlpha(1f);
            txtPlanet.setAlpha(1f);
        } else {
            txtCompatibility.setAlpha(0.5f);
            txtChina.setAlpha(0.5f);
            txtElement.setAlpha(0.5f);
            txtMetal.setAlpha(0.5f);
            txtPlanet.setAlpha(0.5f);
        }

        SharedPreferences mSettings = Objects.requireNonNull(getActivity()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        iconSign.setImageResource(avatarIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        Date date1 = new Date(Long.parseLong(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")) * 1000);
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(date1);
        txtYear.setText("(" + birthday.get(Calendar.YEAR) + ")");

        navHome.setOnClickListener(v -> navigationInterface.onHomePressed());

        navCompatibility.setOnClickListener(v -> navigationInterface.onCompatibilityPressed());

        navChina.setOnClickListener(v -> navigationInterface.onChinaPressed());

        navElement.setOnClickListener(v -> navigationInterface.onElementPressed());

        navMetal.setOnClickListener(v -> navigationInterface.onMetalPressed());

        navPlanet.setOnClickListener(v -> navigationInterface.onPlanetPressed());

        navAbout.setOnClickListener(v -> navigationInterface.onAboutPressed());

        navExit.setOnClickListener(v -> navigationInterface.onExitPressed());

        return view;
    }
}