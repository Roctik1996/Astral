package com.goroscop.astral.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.goroscop.astral.R;
import com.goroscop.astral.model.User;
import com.goroscop.astral.presenter.DevicePresenter;
import com.goroscop.astral.presenter.UserPresenter;
import com.goroscop.astral.service.ClearWorker;
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

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CITY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_GENDER;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_IS_FIRST;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_IS_HOROSCOPE;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PRO;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class MainActivity extends MvpAppCompatActivity implements ViewGetUser, ViewSetDevice, NavigationInterface, BackToHomeInterface {
    private DrawerLayout drawerLayout;
    private SharedPreferences mSettings;
    private ProgressBar progressBar;
    private FrameLayout frame;
    private TextView title;
    private ImageView proIcon;
    private Fragment currentFragment;
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

        if (mSettings.contains(APP_PREFERENCES_IS_FIRST)) {
            if (!mSettings.getString(APP_PREFERENCES_IS_FIRST, "").equals("")) {
                loadFragment(new HomeFragment());
                loadMenu();
                initMainTitle();
            } else
                userPresenter.getUser("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
        } else
            userPresenter.getUser("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));


        int hourOfTheDay = 4; // When to run the job
        int repeatInterval = 1; // In days

        long flexTime = calculateFlex(hourOfTheDay, repeatInterval);
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresDeviceIdle(true)
                .build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(ClearWorker.class,
                        repeatInterval, TimeUnit.DAYS,
                        flexTime, TimeUnit.MILLISECONDS)
                        .setConstraints(myConstraints)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("WORKER",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest);

    }

    private long calculateFlex(int hourOfTheDay, int periodInDays) {

        // Initialize the calendar with today and the preferred time to run the job.
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);

        // Initialize a calendar with now.
        Calendar cal2 = Calendar.getInstance();

        if (cal2.getTimeInMillis() < cal1.getTimeInMillis()) {
            // Add the worker periodicity.
            cal2.setTimeInMillis(cal2.getTimeInMillis() + TimeUnit.DAYS.toMillis(periodInDays));
        }

        long delta = (cal2.getTimeInMillis() - cal1.getTimeInMillis());

        return ((delta > PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS) ? delta
                : PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS);
    }

    private void initMainTitle() {
        if (mSettings.contains(APP_PREFERENCES_PRO)) {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                title.setText(R.string.personal_horoscop);
                proIcon.setVisibility(View.GONE);
            } else {
                title.setText("");
                proIcon.setVisibility(View.VISIBLE);
            }
        } else {
            title.setText("");
            proIcon.setVisibility(View.VISIBLE);
        }
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
        ft.replace(R.id.frame_menu, new DrawerFragment(this, mSettings.getBoolean(APP_PREFERENCES_PRO, false)));
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
        txtInfo.setText("Для приобретения раздела “" + page + "”, перейдите на страницу оплаты");

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        btnPay.setOnClickListener(v -> {
            Intent intent = new Intent(this, PayActivity.class);
            startActivity(intent);
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
            editor.putString(APP_PREFERENCES_IS_HOROSCOPE, "false");
            editor.putBoolean(APP_PREFERENCES_PRO, true);
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
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                title.setText(R.string.personal_horoscop);
                loadFragment(new HomeFragment());
            } else {
                loadFragment(new HomeFragment());
                title.setText("");
                proIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onCompatibilityPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof CompatibilityFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            loadFragment(new CompatibilityFragment());
            title.setText(getString(R.string.nav_compatibility));
            proIcon.setVisibility(View.GONE);
        }

    }

    @Override
    public void onChinaPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof ChinaFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            loadFragment(new ChinaFragment());
            title.setText(getString(R.string.nav_china));
            proIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onElementPressed() {
        drawerLayout.closeDrawers();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (currentFragment instanceof ElementFragment) {
            Log.d(TAG, "Fragment is opened now");
        } else {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                title.setText(R.string.nav_element);
                loadFragment(new ElementFragment());
            } else {
                showDialog(getString(R.string.nav_element));
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
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                title.setText(R.string.nav_metal);
                loadFragment(new MetalFragment());
            } else {
                showDialog(getString(R.string.nav_metal));
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
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                title.setText(R.string.nav_planet);
                loadFragment(new PlanetFragment());
            } else {
                showDialog(getString(R.string.nav_planet));
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
        Intent loginActivity = new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }

    @Override
    public void onBack(boolean isHome) {
        if (isHome) {
            if (mSettings.contains(APP_PREFERENCES_PRO)) {
                if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                    title.setText(R.string.personal_horoscop);
                    proIcon.setVisibility(View.GONE);
                } else {
                    title.setText("");
                    proIcon.setVisibility(View.VISIBLE);
                }
            } else {
                title.setText("");
                proIcon.setVisibility(View.VISIBLE);
            }
        }
    }
}
