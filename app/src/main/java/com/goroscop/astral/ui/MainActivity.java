package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.goroscop.astral.R;
import com.goroscop.astral.model.User;
import com.goroscop.astral.presenter.DevicePresenter;
import com.goroscop.astral.presenter.UserPresenter;
import com.goroscop.astral.ui.dialog.PreviewDialog;
import com.goroscop.astral.ui.fragment.DrawerFragment;
import com.goroscop.astral.ui.fragment.tabs.AboutFragment;
import com.goroscop.astral.ui.fragment.tabs.ChinaFragment;
import com.goroscop.astral.ui.fragment.tabs.CompatibilityFragment;
import com.goroscop.astral.ui.fragment.tabs.ElementFragment;
import com.goroscop.astral.ui.fragment.tabs.HomeFragment;
import com.goroscop.astral.ui.fragment.tabs.MetalFragment;
import com.goroscop.astral.ui.fragment.tabs.PlanetFragment;
import com.goroscop.astral.ui.interfaces.BackToHomeInterface;
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
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TRIAL;

public class MainActivity extends MvpAppCompatActivity implements ViewGetUser, ViewSetDevice, NavigationInterface, BackToHomeInterface {
    private DrawerLayout drawerLayout;
    private SharedPreferences mSettings;
    private ProgressBar progressBar;
    private FrameLayout frame;
    private TextView title;
    private ImageView proIcon;
    private Fragment currentFragment;
    private DialogFragment previewDialog;
    private final String TAG = MainActivity.class.getSimpleName();

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

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        iconMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        userPresenter.getUser("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));

        proIcon.setOnClickListener(v -> {
            previewDialog = new PreviewDialog();
            previewDialog.show(getSupportFragmentManager(), "dlg1");
        });

    }

    private void initMainTitle() {
        title.setText("");
        proIcon.setVisibility(View.VISIBLE);
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
        if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
            ft.replace(R.id.frame_menu, new DrawerFragment(this, true));
            ft.commit();
        } else {
            ft.replace(R.id.frame_menu, new DrawerFragment(this, false));
            ft.commit();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(dialogView);
        TextView txtCancel = dialogView.findViewById(R.id.txt_cancel);
        TextView btnPay = dialogView.findViewById(R.id.btn_pay);
        TextView txtRules = dialogView.findViewById(R.id.txt_rules);
        CheckBox checkBox = dialogView.findViewById(R.id.checkbox);

        txtRules.setMovementMethod(LinkMovementMethod.getInstance());
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnPay.setOnClickListener(v -> {
                    Intent intent = new Intent(this, PayActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    finish();
                });
            }


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
            editor.putBoolean(APP_PREFERENCES_TRIAL, user.getTrial());
            editor.apply();
            loadFragment(new HomeFragment());
            loadMenu();
            initMainTitle();

            if (mSettings.contains(APP_PREFERENCES_IS_FIRST)) {
                if (mSettings.getString(APP_PREFERENCES_IS_FIRST, "").equals("")) {
                    uploadDeviceId();
                }
            } else {
                uploadDeviceId();
            }
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
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (currentFragment instanceof HomeFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            loadFragment(new HomeFragment());
            title.setText("");
            proIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCompatibilityPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof CompatibilityFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
                title.setText(R.string.nav_compatibility);
                loadFragment(new CompatibilityFragment());
                proIcon.setVisibility(View.GONE);
            } else {
                showDialog();
            }
        }

    }

    @Override
    public void onChinaPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof ChinaFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
                title.setText(R.string.nav_china);
                loadFragment(new ChinaFragment());
                proIcon.setVisibility(View.GONE);
            } else {
                showDialog();
            }
        }
    }

    @Override
    public void onElementPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof ElementFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
                title.setText(R.string.nav_element);
                loadFragment(new ElementFragment());
                proIcon.setVisibility(View.GONE);
            } else {
                showDialog();
            }
        }
    }

    @Override
    public void onMetalPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof MetalFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
                title.setText(R.string.nav_metal);
                loadFragment(new MetalFragment());
                proIcon.setVisibility(View.GONE);
            } else {
                showDialog();
            }
        }
    }

    @Override
    public void onPlanetPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof PlanetFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
                title.setText(R.string.nav_planet);
                loadFragment(new PlanetFragment());
                proIcon.setVisibility(View.GONE);
            } else {
                showDialog();
            }
        }
    }

    @Override
    public void onAboutPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof AboutFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            loadFragment(new AboutFragment());
            title.setText(getString(R.string.nav_about));
            proIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onExitPressed() {
        drawerLayout.closeDrawers();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }

    @Override
    public void onBack(boolean isHome) {
        if (isHome) {
            title.setText("");
            proIcon.setVisibility(View.VISIBLE);
        }
    }
}
