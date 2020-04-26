package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.goroscop.astral.R;
import com.goroscop.astral.model.User;
import com.goroscop.astral.presenter.DevicePresenter;
import com.goroscop.astral.presenter.UserPresenter;
import com.goroscop.astral.ui.fragment.ElementFragment;
import com.goroscop.astral.ui.fragment.HomeFragment;
import com.goroscop.astral.ui.fragment.MetalFragment;
import com.goroscop.astral.ui.fragment.PlanetFragment;
import com.goroscop.astral.ui.interfaces.NavigationInterface;
import com.goroscop.astral.view.ViewGetUser;
import com.goroscop.astral.view.ViewSetDevice;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CITY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_GENDER;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_IS_FIRST;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PRO;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class MainActivity extends MvpAppCompatActivity implements ViewGetUser, ViewSetDevice, NavigationInterface {
    private DrawerLayout drawerLayout;
    private SharedPreferences mSettings;
    private ProgressBar progressBar;
    private FrameLayout frame;
    private TextView title;
    private ImageView proIcon;

    @InjectPresenter
    UserPresenter userPresenter;

    @InjectPresenter
    DevicePresenter devicePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        ImageView iconMenu = findViewById(R.id.icon_menu);
        title = findViewById(R.id.txt_title);
        proIcon = findViewById(R.id.icon_pro_btn);

        progressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.frame);

        loadMenu();

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        iconMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        if (mSettings.contains(APP_PREFERENCES_PRO)) {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                title.setText(R.string.personal_horoscop);
                proIcon.setVisibility(View.GONE);
            } else {
                title.setText("");
                proIcon.setVisibility(View.VISIBLE);
            }
        }


        if (mSettings.contains(APP_PREFERENCES_IS_FIRST)) {
            if (mSettings.getString(APP_PREFERENCES_IS_FIRST, "").equals("")) {
                uploadDeviceId();
            }
        } else {
            uploadDeviceId();
        }

        if (mSettings.contains(APP_PREFERENCES_NAME)) {
            if (!mSettings.getString(APP_PREFERENCES_NAME, "").equals("")) {
                loadFragment(new HomeFragment());
            } else
                userPresenter.getUser("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
        } else
            userPresenter.getUser("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
    }

    private void uploadDeviceId() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    devicePresenter.setDevice("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""), token, deviceId);
                });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void loadMenu() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_menu, new NavigationLayout(this));
        ft.commit();
    }

    @SuppressLint("SetTextI18n")
    private void showDialog(String page) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(dialogView);
        TextView txtInfo = dialogView.findViewById(R.id.txt_alert);
        TextView txtCancel = dialogView.findViewById(R.id.txt_cancel);
        TextView btnPay = dialogView.findViewById(R.id.btn_pay);
        txtInfo.setText("Для приобретения раздела “"+page+"”, перейдите на страницу оплаты");

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        btnPay.setOnClickListener(v -> {
            //TODO: pay page
            /*Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(intent);*/
            dialog.dismiss();
        });

        txtCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void getUser(User user) {
        if (user != null) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_NAME, user.getName());
            editor.putString(APP_PREFERENCES_BIRTHDAY, user.getBirthday());
            editor.putString(APP_PREFERENCES_GENDER, user.getGender());
            editor.putString(APP_PREFERENCES_CITY, user.getCity());
            editor.putBoolean(APP_PREFERENCES_PRO, user.getIsPro());
            editor.apply();
            loadFragment(new HomeFragment());
        }
    }

    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        frame.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDevice(String result) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_IS_FIRST, "false");
        editor.apply();
    }

    @Override
    public void onHomePressed() {
        drawerLayout.closeDrawers();
        if (mSettings.getBoolean(APP_PREFERENCES_PRO,false)) {
            title.setText(R.string.personal_horoscop);
            loadFragment(new HomeFragment());
        }else {
            loadFragment(new HomeFragment());
            title.setText("");
            proIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCompatibilityPressed() {

    }

    @Override
    public void onChinaPressed() {

    }

    @Override
    public void onElementPressed() {
        drawerLayout.closeDrawers();
        if (mSettings.getBoolean(APP_PREFERENCES_PRO,false)) {
            title.setText(R.string.nav_element);
            loadFragment(new ElementFragment());
        }else {
            showDialog(getString(R.string.nav_element));
        }
    }

    @Override
    public void onMetalPressed() {
        drawerLayout.closeDrawers();
        if (mSettings.getBoolean(APP_PREFERENCES_PRO,false)) {
            title.setText(R.string.nav_metal);
            loadFragment(new MetalFragment());
        }else {
            showDialog(getString(R.string.nav_metal));
        }
    }

    @Override
    public void onPlanetPressed() {
        drawerLayout.closeDrawers();
        if (mSettings.getBoolean(APP_PREFERENCES_PRO,false)) {
            title.setText(R.string.nav_planet);
            loadFragment(new PlanetFragment());
        }else {
            showDialog(getString(R.string.nav_planet));
        }
    }
}
