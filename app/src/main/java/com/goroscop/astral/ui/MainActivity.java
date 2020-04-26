package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class MainActivity extends MvpAppCompatActivity implements ViewGetUser, ViewSetDevice, NavigationInterface {
    private DrawerLayout drawerLayout;
    private SharedPreferences mSettings;
    private ProgressBar progressBar;
    private FrameLayout frame;

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

        progressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.frame);

        loadMenu();

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        iconMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));


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

    @Override
    public void getUser(User user) {
        if (user != null) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_NAME, user.getName());
            editor.putString(APP_PREFERENCES_BIRTHDAY, user.getBirthday());
            editor.putString(APP_PREFERENCES_GENDER, user.getGender());
            editor.putString(APP_PREFERENCES_CITY, user.getCity());
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
    }

    @Override
    public void onCompatibilityPressed() {

    }

    @Override
    public void onChinaPressed() {

    }

    @Override
    public void onElementPressed() {

    }

    @Override
    public void onMetalPressed() {
        loadFragment(new MetalFragment());
        drawerLayout.closeDrawers();
    }

    @Override
    public void onPlanetPressed() {
        loadFragment(new PlanetFragment());
        drawerLayout.closeDrawers();
    }
}
