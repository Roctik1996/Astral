package com.goroscop.astral.ui.fragment.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.tabs.TabLayout;
import com.goroscop.astral.R;
import com.goroscop.astral.model.Horoscope;
import com.goroscop.astral.presenter.HoroscopePresenter;
import com.goroscop.astral.ui.adapter.LuckyNumAdapter;
import com.goroscop.astral.ui.fragment.HoroscopeFragment;
import com.goroscop.astral.ui.interfaces.BackToHomeInterface;
import com.goroscop.astral.view.ViewHoroscope;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hiennguyen.me.circleseekbar.CircleSeekBar;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CHINA;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_SUCCESS;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;
import static com.goroscop.astral.utils.Const.avatarIcon;
import static com.goroscop.astral.utils.Const.datesSign;
import static com.goroscop.astral.utils.Const.miniChinaIcon;
import static com.goroscop.astral.utils.Const.miniIcon;
import static com.goroscop.astral.utils.Const.tabTitle;
import static com.goroscop.astral.utils.Utils.getAge;
import static com.goroscop.astral.utils.Utils.getChinaSign;
import static com.goroscop.astral.utils.Utils.getSign;

public class HomeFragment extends MvpAppCompatFragment implements ViewHoroscope {

    private TabLayout tabLayout;
    private RecyclerView recyclerLucky;
    private ArrayList<Integer> count = new ArrayList<>();
    private ArrayList<String> horoscopeData = new ArrayList<>();
    private ProgressBar progressBar;
    private FrameLayout frame;
    private ImageView avatar, iconSign, iconChinaSign;
    private TextView txtNameAge, txtSign, txtChinaSign, txtToday;
    private CircleSeekBar love, health, career;

    private SharedPreferences mSettings;

    @InjectPresenter
    HoroscopePresenter horoscopePresenter;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerLucky = view.findViewById(R.id.recycler_num);

        progressBar = view.findViewById(R.id.progress);
        frame = view.findViewById(R.id.frame);

        avatar = view.findViewById(R.id.my_sign);
        txtNameAge = view.findViewById(R.id.txt_name_age);
        iconSign = view.findViewById(R.id.icon_sign);
        iconChinaSign = view.findViewById(R.id.icon_china_sign);
        txtSign = view.findViewById(R.id.txt_sign);
        txtChinaSign = view.findViewById(R.id.txt_china_sign);
        txtToday = view.findViewById(R.id.txt_today);

        love = view.findViewById(R.id.circular_love);
        health = view.findViewById(R.id.circular_health);
        career = view.findViewById(R.id.circular_career);

        BackToHomeInterface backToHomeInterface = (BackToHomeInterface) getActivity();
        backToHomeInterface.onBack(true);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(APP_PREFERENCES_TOKEN)) {
            if (!mSettings.getString(APP_PREFERENCES_TOKEN, "").equals("")) {
                horoscopePresenter.getHoroscope("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
            }
        }

        initPersonalInfo();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initPersonalInfo() {
        txtNameAge.setText(mSettings.getString(APP_PREFERENCES_NAME, "") + ", " +
                getAge(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")));

        avatar.setImageResource(avatarIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        iconSign.setImageResource(miniIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        iconChinaSign.setImageResource(miniChinaIcon.get(getChinaSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        getDifferentTextForSign();
        getDifferentTextForChinaSign();
        getDifferentTextForToday();


    }

    private void getDifferentTextForSign() {
        Spannable word = new SpannableString(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")));
        word.setSpan(new ForegroundColorSpan(Color.WHITE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSign.setText(word);
        Spannable wordTwo = new SpannableString(" (" + datesSign.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))) + ")");
        wordTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#80FFFFFF")), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSign.append(wordTwo);
    }

    private void getDifferentTextForChinaSign() {
        Spannable word = new SpannableString(getChinaSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")));
        word.setSpan(new ForegroundColorSpan(Color.WHITE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtChinaSign.setText(word);
        Date date1 = new Date(Long.parseLong(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")) * 1000);
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(date1);
        Spannable wordTwo = new SpannableString(" (" + birthday.get(Calendar.YEAR) + ")");
        wordTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#80FFFFFF")), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtChinaSign.append(wordTwo);
    }

    @SuppressLint("SimpleDateFormat")
    private void getDifferentTextForToday() {
        Calendar now = Calendar.getInstance();
        Spannable word = new SpannableString("Сегодня");
        word.setSpan(new ForegroundColorSpan(Color.WHITE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtToday.setText(word);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = formatter.format(new Date(now.getTime().getTime()));
        Spannable wordTwo = new SpannableString(" (" + dateString + ")");
        wordTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#80FFFFFF")), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtToday.append(wordTwo);
    }

    @Override
    public void getHoroscope(Horoscope horoscope) {
        if (horoscope != null) {

            horoscopeData.add(horoscope.getToday().getInfo());
            horoscopeData.add(horoscope.getTomorrow());
            horoscopeData.add(horoscope.getWeek());
            horoscopeData.add(horoscope.getMonth());
            horoscopeData.add(horoscope.getYear());

            loadFragment(new HoroscopeFragment(horoscopeData.get(0),horoscope.getToday().getSuccessDay()));
            initTabHoroscope(horoscope.getToday().getSuccessDay());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerLucky.setLayoutManager(layoutManager);
            count.addAll(horoscope.getToday().getLuckNumbers());
            recyclerLucky.setAdapter(new LuckyNumAdapter(count));

            love.setProgressDisplayAndInvalidate(horoscope.getToday().getLove());
            health.setProgressDisplayAndInvalidate(horoscope.getToday().getHealth());
            career.setProgressDisplayAndInvalidate(horoscope.getToday().getCareer());


            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_CHINA, horoscope.getChina().getInfo());
            editor.putInt(APP_PREFERENCES_SUCCESS, horoscope.getChina().getSuccessDay());
            editor.apply();
        }
    }


    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        frame.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    private void initTabHoroscope(int success) {
        TabLayout.Tab today = tabLayout.newTab();
        today.setText(tabTitle[0]);
        tabLayout.addTab(today);

        TabLayout.Tab tomorrow = tabLayout.newTab();
        tomorrow.setText(tabTitle[1]);
        tabLayout.addTab(tomorrow);

        TabLayout.Tab week = tabLayout.newTab();
        week.setText(tabTitle[2]);
        tabLayout.addTab(week);

        TabLayout.Tab month = tabLayout.newTab();
        month.setText(tabTitle[3]);
        tabLayout.addTab(month);

        TabLayout.Tab year = tabLayout.newTab();
        year.setText(tabTitle[4]);
        tabLayout.addTab(year);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(0),success));
                        break;
                    case 1:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(1),-1));
                        break;
                    case 2:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(2),-1));
                        break;

                    case 3:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(3),-1));
                        break;

                    case 4:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(4),-1));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.selectTab(today);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.horoscope_frame, fragment);
        ft.commit();
    }
}
