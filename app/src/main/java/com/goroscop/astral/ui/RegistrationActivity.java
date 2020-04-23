package com.goroscop.astral.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.adapter.CountAdapter;
import com.goroscop.astral.ui.adapter.RegistrationAdapter;
import com.goroscop.astral.ui.fragment.BirthdayFragment;
import com.goroscop.astral.ui.fragment.CityFragment;
import com.goroscop.astral.ui.fragment.GenderFragment;
import com.goroscop.astral.ui.fragment.MailPassFragment;
import com.goroscop.astral.ui.fragment.NameFragment;
import com.goroscop.astral.ui.interfaces.RegistrationInterface;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity implements RegistrationInterface {
    private ViewPager2 countPager, contentPager;
    private Button btnNext;
    private ArrayList<Integer> count = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        countPager = findViewById(R.id.count_pager);
        contentPager = findViewById(R.id.content_pager);
        btnNext = findViewById(R.id.btn_next);
        ImageView back = findViewById(R.id.icon_back);

        initAnimationPager();
        initCountPager();
        initContentPager();

        back.setOnClickListener(v -> {
            if (countPager.getCurrentItem()>0) {
                contentPager.setCurrentItem(contentPager.getCurrentItem() - 1);
                countPager.setCurrentItem(countPager.getCurrentItem() - 1);
            }
            else
            {
                Intent loginActivity = new Intent(this,LoginActivity.class);
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

    private void initContentPager(){
        countPager.setOffscreenPageLimit(1);
        contentPager.setUserInputEnabled(false);
        fragmentList.add(new NameFragment());
        fragmentList.add(new BirthdayFragment());
        fragmentList.add(new GenderFragment());
        fragmentList.add(new CityFragment());
        fragmentList.add(new MailPassFragment());

        RegistrationAdapter registrationAdapter = new RegistrationAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        contentPager.setAdapter(registrationAdapter);
        System.out.println(contentPager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        if (countPager.getCurrentItem()>0) {
            contentPager.setCurrentItem(contentPager.getCurrentItem() - 1);
            countPager.setCurrentItem(countPager.getCurrentItem() - 1);
        }
        else
        {
            Intent loginActivity = new Intent(this,LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }

    @Override
    public void onNext(boolean isNext, String error) {
        if (isNext){
            btnNext.setOnClickListener(v -> {
                contentPager.setCurrentItem(contentPager.getCurrentItem()+1);
                countPager.setCurrentItem(countPager.getCurrentItem()+1);
                System.out.println(contentPager.getCurrentItem());
            });
        }
        else {
            btnNext.setOnClickListener(v -> {
                Toast.makeText(this, error,Toast.LENGTH_LONG).show();
            });
        }
    }
}
