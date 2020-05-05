package com.goroscop.astral.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goroscop.astral.R;
import com.goroscop.astral.model.Token;
import com.goroscop.astral.presenter.RegistrationPresenter;
import com.goroscop.astral.ui.adapter.CountAdapter;
import com.goroscop.astral.ui.adapter.RegistrationAdapter;
import com.goroscop.astral.ui.fragment.BirthdayFragment;
import com.goroscop.astral.ui.fragment.CityFragment;
import com.goroscop.astral.ui.fragment.GenderFragment;
import com.goroscop.astral.ui.fragment.MailPassFragment;
import com.goroscop.astral.ui.fragment.NameFragment;
import com.goroscop.astral.ui.interfaces.RegistrationInterface;
import com.goroscop.astral.view.ViewGetToken;

import java.util.ArrayList;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CITY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_EMAIL;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_GENDER;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PASS;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class RegistrationActivity extends MvpAppCompatActivity implements RegistrationInterface, ViewGetToken {
    private ViewPager2 countPager, contentPager;
    private Button btnNext;
    private ArrayList<Integer> count = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private SharedPreferences mSettings;

    private ProgressBar progressBar;
    private FrameLayout frame;

    @InjectPresenter
    RegistrationPresenter registrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        countPager = findViewById(R.id.count_pager);
        contentPager = findViewById(R.id.content_pager);
        btnNext = findViewById(R.id.btn_next);
        ImageView back = findViewById(R.id.icon_back);

        progressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.frame);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initAnimationPager();
        initCountPager();
        initContentPager();


        back.setOnClickListener(v -> {
            if (countPager.getCurrentItem() > 0) {
                contentPager.setCurrentItem(contentPager.getCurrentItem() - 1);
                countPager.setCurrentItem(countPager.getCurrentItem() - 1);
            } else {
                Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });

    }

    private void initAnimationPager() {
        float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        final float MIN_SCALE = 0.7f;
        final float MAX_SCALE = 1f;
        final float MIN_FADE = 0.5f;
        countPager.setPageTransformer((page, position) -> {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            page.setTranslationX(myOffset);
            float scaleFactor = MIN_SCALE + (MAX_SCALE - MIN_SCALE) * (1 - Math.abs(position));

            if (position < 0) {
                page.setAlpha(MIN_FADE);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

            } else if (position == 0) {
                page.setAlpha(1);

            } else if (position > 0) {
                page.setAlpha(MIN_FADE);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }
        });
    }

    private void initCountPager() {
        countPager.setOffscreenPageLimit(5);
        countPager.setUserInputEnabled(false);
        for (int i = 1; i < 6; i++)
            count.add(i);
        countPager.setAdapter(new CountAdapter(count));
    }

    private void initContentPager() {
        countPager.setOffscreenPageLimit(5);
        contentPager.setUserInputEnabled(false);
        fragmentList.add(new NameFragment());
        fragmentList.add(new BirthdayFragment());
        fragmentList.add(new GenderFragment());
        fragmentList.add(new CityFragment());
        fragmentList.add(new MailPassFragment());

        RegistrationAdapter registrationAdapter = new RegistrationAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        contentPager.setAdapter(registrationAdapter);
    }

    @Override
    public void onBackPressed() {
        if (countPager.getCurrentItem() > 0) {
            contentPager.setCurrentItem(contentPager.getCurrentItem() - 1);
            countPager.setCurrentItem(countPager.getCurrentItem() - 1);
        } else {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }

    @Override
    public void onNext(boolean isNext, String error) {
        btnNext.setOnClickListener(v -> {
            if (isNext) {
                if (error.equals("")) {
                    contentPager.setCurrentItem(contentPager.getCurrentItem() + 1);
                    countPager.setCurrentItem(countPager.getCurrentItem() + 1);
                }
                if (error.equals("completed")) {
                    registrationPresenter.registration(mSettings.getString(APP_PREFERENCES_NAME, ""),
                            mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""),
                            mSettings.getString(APP_PREFERENCES_GENDER, ""),
                            mSettings.getString(APP_PREFERENCES_CITY, ""),
                            mSettings.getString(APP_PREFERENCES_EMAIL, ""),
                            mSettings.getString(APP_PREFERENCES_PASS, ""));
                }
            } else
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void getToken(Token token) {
        if (token != null) {
            if (token.getToken() != null) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_TOKEN, token.getToken());
                editor.apply();
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            } else {
                Toast.makeText(this, R.string.error_email_exist, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.error_email_exist, Toast.LENGTH_LONG).show();
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
}
