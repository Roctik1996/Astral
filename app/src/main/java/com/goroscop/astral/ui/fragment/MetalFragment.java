package com.goroscop.astral.ui.fragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goroscop.astral.R;
import com.goroscop.astral.utils.MetalConst;

import java.util.Calendar;
import java.util.Date;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.datesSign;
import static com.goroscop.astral.utils.Const.miniChinaIcon;
import static com.goroscop.astral.utils.Const.miniIcon;
import static com.goroscop.astral.utils.Utils.getAge;
import static com.goroscop.astral.utils.Utils.getChinaSign;
import static com.goroscop.astral.utils.Utils.getSign;

public class MetalFragment extends Fragment {

    private ImageView iconSign, iconChinaSign;
    private TextView txtNameAge, txtSign, txtChinaSign,txtMetal;

    private SharedPreferences mSettings;


    public MetalFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_metal, container, false);
        txtNameAge = view.findViewById(R.id.txt_name_age);
        iconSign = view.findViewById(R.id.icon_sign);
        iconChinaSign = view.findViewById(R.id.icon_china_sign);
        txtSign = view.findViewById(R.id.txt_sign);
        txtChinaSign = view.findViewById(R.id.txt_china_sign);
        txtMetal = view.findViewById(R.id.txt_metal);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initPersonalInfo();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initPersonalInfo() {
        txtNameAge.setText(mSettings.getString(APP_PREFERENCES_NAME, "") + ", " +
                getAge(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")));
        iconSign.setImageResource(miniIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        iconChinaSign.setImageResource(miniChinaIcon.get(getChinaSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        txtMetal.setText(MetalConst.metalData.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        getDifferentTextForSign();
        getDifferentTextForChinaSign();

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


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.horoscope_frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
