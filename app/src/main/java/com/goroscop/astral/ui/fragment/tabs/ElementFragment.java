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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goroscop.astral.R;

import java.util.Calendar;
import java.util.Date;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;
import static com.goroscop.astral.utils.Const.datesSign;
import static com.goroscop.astral.utils.Const.elements;
import static com.goroscop.astral.utils.Const.elementsCompatibility;
import static com.goroscop.astral.utils.Const.elementsIcon;
import static com.goroscop.astral.utils.Const.miniChinaIcon;
import static com.goroscop.astral.utils.Const.miniIcon;
import static com.goroscop.astral.utils.Utils.getAge;
import static com.goroscop.astral.utils.Utils.getChinaSign;
import static com.goroscop.astral.utils.Utils.getSign;

public class ElementFragment extends Fragment {

    private ImageView iconSign, iconChinaSign, iconElement;
    private TextView txtNameAge, txtSign, txtChinaSign, txtElement, txtElementCompatibility;
    private ProgressBar progressBar;
    private FrameLayout frame;

    private SharedPreferences mSettings;

    public ElementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_element, container, false);
        txtNameAge = view.findViewById(R.id.txt_name_age);
        iconSign = view.findViewById(R.id.icon_sign);
        iconChinaSign = view.findViewById(R.id.icon_china_sign);
        iconElement = view.findViewById(R.id.my_sign);
        txtSign = view.findViewById(R.id.txt_sign);
        txtChinaSign = view.findViewById(R.id.txt_china_sign);
        txtElement = view.findViewById(R.id.txt_element);
        txtElementCompatibility = view.findViewById(R.id.txt_element_compatibility);

        progressBar = view.findViewById(R.id.progress);
        frame = view.findViewById(R.id.frame);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initInfo();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                loadFragment(new HomeFragment());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initInfo() {
        txtNameAge.setText(mSettings.getString(APP_PREFERENCES_NAME, "") + ", " +
                getAge(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")));
        iconSign.setImageResource(miniIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        iconChinaSign.setImageResource(miniChinaIcon.get(getChinaSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        getDifferentTextForSign();
        getDifferentTextForChinaSign();

        txtElement.setText(elements.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        txtElementCompatibility.setText(elementsCompatibility.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        iconElement.setImageResource(elementsIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

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


    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        frame.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }


}
