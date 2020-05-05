package com.goroscop.astral.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.RegistrationInterface;

import java.util.Objects;

import static android.text.TextUtils.isEmpty;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_CITY;

public class CityFragment extends Fragment {

    private String city = "";
    private RegistrationInterface registrationInterface;
    private SharedPreferences mSettings;

    public CityFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (validate(city)) {
            registrationInterface.onNext(true, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        EditText txtCity = view.findViewById(R.id.edt_city);
        mSettings = Objects.requireNonNull(getActivity()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        registrationInterface = (RegistrationInterface) getActivity();

        txtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                city = String.valueOf(s);
                if (validate(city)) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_CITY, city);
                    editor.apply();
                    registrationInterface.onNext(true, "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private boolean validate(String city) {
        if (isEmpty(city)) {
            registrationInterface.onNext(false, getString(R.string.error_city_empty));
            return false;
        }
        return true;
    }
}
