package com.goroscop.astral.ui.fragment.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.goroscop.astral.ui.interfaces.NavigationInterface;
import com.goroscop.astral.utils.MetalConst;
import com.goroscop.astral.view.ViewHoroscope;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import hiennguyen.me.circleseekbar.CircleSeekBar;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CAREER;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CHINA;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_HEALTH;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_IS_HOROSCOPE;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_LOVE;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_LUCK_NUMBER;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_MONTH;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PLANET;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PRO;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_SUCCESS_CHINA;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TODAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TODAY_SUCCESS;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOMORROW;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_WEEK;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_YEAR;
import static com.goroscop.astral.utils.Const.avatarIcon;
import static com.goroscop.astral.utils.Const.datesSign;
import static com.goroscop.astral.utils.Const.elements;
import static com.goroscop.astral.utils.Const.elementsIcon;
import static com.goroscop.astral.utils.Const.miniChinaIcon;
import static com.goroscop.astral.utils.Const.miniIcon;
import static com.goroscop.astral.utils.Const.planetUrls;
import static com.goroscop.astral.utils.Const.tabTitle;
import static com.goroscop.astral.utils.Utils.getAge;
import static com.goroscop.astral.utils.Utils.getChinaSign;
import static com.goroscop.astral.utils.Utils.getSign;

public class HomeFragment extends MvpAppCompatFragment implements ViewHoroscope {

    private TabLayout tabLayout;
    private RecyclerView recyclerLucky;
    private ArrayList<String> count = new ArrayList<>();
    private ArrayList<String> horoscopeData = new ArrayList<>();
    private ProgressBar progressBar, progressSuccess;
    private FrameLayout frame;
    private ImageView avatar, iconSign, iconChinaSign, iconChinaPro, iconElementPro;
    private TextView txtNameAge, txtSign, txtChinaSign, txtToday, txtChinaPro, txtChinaSuccess, txtMetal, txtElement, txtPlanet;
    private CircleSeekBar love, health, career;
    private ConstraintLayout contentPro;

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

        //init pro components
        iconChinaPro = view.findViewById(R.id.icon_china_sign_pro);
        iconElementPro = view.findViewById(R.id.icon_element_sign_pro);
        contentPro = view.findViewById(R.id.content_pro);
        txtChinaPro = view.findViewById(R.id.txt_china);
        txtChinaSuccess = view.findViewById(R.id.txt_success_progress);
        progressSuccess = view.findViewById(R.id.progressBar);
        txtMetal = view.findViewById(R.id.txt_metal);
        txtElement = view.findViewById(R.id.txt_element);
        txtPlanet = view.findViewById(R.id.txt_planet);
        LinearLayout detailChina = view.findViewById(R.id.detail_china);
        LinearLayout detailElement = view.findViewById(R.id.detail_element);
        LinearLayout detailMetal = view.findViewById(R.id.detail_metal);
        LinearLayout detailPlanet = view.findViewById(R.id.detail_planet);

        NavigationInterface navigationInterface = (NavigationInterface) getActivity();

        BackToHomeInterface backToHomeInterface = (BackToHomeInterface) getActivity();
        backToHomeInterface.onBack(true);

        detailChina.setOnClickListener(v -> navigationInterface.onChinaPressed());
        detailElement.setOnClickListener(v -> navigationInterface.onElementPressed());
        detailMetal.setOnClickListener(v -> navigationInterface.onMetalPressed());
        detailPlanet.setOnClickListener(v -> navigationInterface.onPlanetPressed());

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(APP_PREFERENCES_TOKEN)) {
            if (!mSettings.getString(APP_PREFERENCES_TOKEN, "").equals("") && mSettings.getString(APP_PREFERENCES_IS_HOROSCOPE, "").equals("false")) {
                horoscopePresenter.getHoroscope("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
            } else {
                initHome();
            }
        } else {
            initHome();
        }

        initPersonalInfo();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initProContent() {
        iconChinaPro.setImageResource(miniChinaIcon.get(getChinaSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        iconElementPro.setImageResource(elementsIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        txtChinaPro.setText(mSettings.getString(APP_PREFERENCES_CHINA, ""));
        progressSuccess.setProgress(mSettings.getInt(APP_PREFERENCES_SUCCESS_CHINA, 0));
        txtChinaSuccess.setText(mSettings.getInt(APP_PREFERENCES_SUCCESS_CHINA, 0) + "%");
        txtMetal.setText(MetalConst.metalData.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        txtElement.setText(elements.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        initPlanet();
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

    private void initPlanet() {
        if (mSettings.contains(APP_PREFERENCES_PLANET)) {
            if (!mSettings.getString(APP_PREFERENCES_PLANET, "").equals("")) {
                txtPlanet.setText(mSettings.getString(APP_PREFERENCES_PLANET, ""));
            } else new ParseData().execute();
        } else new ParseData().execute();
    }

    private void initHome() {

        if (mSettings.contains(APP_PREFERENCES_PRO)) {
            if (mSettings.getBoolean(APP_PREFERENCES_PRO, false)) {
                contentPro.setVisibility(View.VISIBLE);
                initProContent();
            } else {
                contentPro.setVisibility(View.GONE);
            }
        } else {
            contentPro.setVisibility(View.GONE);
        }

        horoscopeData.add(mSettings.getString(APP_PREFERENCES_TODAY, ""));
        horoscopeData.add(mSettings.getString(APP_PREFERENCES_TOMORROW, ""));
        horoscopeData.add(mSettings.getString(APP_PREFERENCES_WEEK, ""));
        horoscopeData.add(mSettings.getString(APP_PREFERENCES_MONTH, ""));
        horoscopeData.add(mSettings.getString(APP_PREFERENCES_YEAR, ""));

        loadFragment(new HoroscopeFragment(horoscopeData.get(0), mSettings.getInt(APP_PREFERENCES_TODAY_SUCCESS, 0)));
        initTabHoroscope(mSettings.getInt(APP_PREFERENCES_TODAY_SUCCESS, 0));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerLucky.setLayoutManager(layoutManager);
        ArrayList<String> luckNum = new ArrayList<>();
        luckNum.addAll(mSettings.getStringSet(APP_PREFERENCES_LUCK_NUMBER, null));
        recyclerLucky.setAdapter(new LuckyNumAdapter(luckNum));

        love.setProgressDisplayAndInvalidate(mSettings.getInt(APP_PREFERENCES_LOVE, 0));
        health.setProgressDisplayAndInvalidate(mSettings.getInt(APP_PREFERENCES_HEALTH, 0));
        career.setProgressDisplayAndInvalidate(mSettings.getInt(APP_PREFERENCES_CAREER, 0));
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
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_TODAY, horoscope.getToday().getInfo());
            editor.putInt(APP_PREFERENCES_TODAY_SUCCESS, horoscope.getToday().getSuccessDay());
            editor.putString(APP_PREFERENCES_CHINA, horoscope.getChina().getInfo());
            editor.putInt(APP_PREFERENCES_SUCCESS_CHINA, horoscope.getChina().getSuccessDay());
            editor.putString(APP_PREFERENCES_TOMORROW, horoscope.getTomorrow());
            editor.putString(APP_PREFERENCES_WEEK, horoscope.getWeek());
            editor.putString(APP_PREFERENCES_MONTH, horoscope.getMonth());
            editor.putString(APP_PREFERENCES_YEAR, horoscope.getYear());
            editor.putInt(APP_PREFERENCES_LOVE, horoscope.getToday().getLove());
            editor.putInt(APP_PREFERENCES_HEALTH, horoscope.getToday().getHealth());
            editor.putInt(APP_PREFERENCES_CAREER, horoscope.getToday().getCareer());
            for (Integer i : horoscope.getToday().getLuckNumbers()) {
                count.add(String.valueOf(i));
            }
            Set<String> foo = new HashSet<>(count);
            editor.putStringSet(APP_PREFERENCES_LUCK_NUMBER, foo);
            editor.putString(APP_PREFERENCES_IS_HOROSCOPE, "true");
            editor.apply();
            initHome();
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

        tabLayout.selectTab(today,true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(0), success));
                        break;
                    case 1:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(1), -1));
                        break;
                    case 2:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(2), -1));
                        break;

                    case 3:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(3), -1));
                        break;

                    case 4:
                        loadFragment(new HoroscopeFragment(horoscopeData.get(4), -1));
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
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.horoscope_frame, fragment);
        ft.commit();
    }

    private class ParseData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();
            try {
                Document doc = Jsoup.connect(planetUrls.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")))).get();
                Elements newsHeadlines = doc.select("p");
                for (Element headline : newsHeadlines) {
                    result.append(headline).append("\n\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String data = result.toString().replaceAll("<p>", "").replaceAll("</p>", "");

            return data.substring(0, data.length() - 2);
        }


        @Override
        protected void onPostExecute(String result) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_PLANET, result);
            editor.apply();
            txtPlanet.setText(result);
        }
    }
}
