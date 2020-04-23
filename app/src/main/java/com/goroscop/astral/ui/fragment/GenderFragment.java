package com.goroscop.astral.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.RegistrationInterface;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_GENDER;

public class GenderFragment extends Fragment {

    private ToggleButton male, female;
    private SharedPreferences mSettings;
    private RegistrationInterface registrationInterface;

    public GenderFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (validate(false, false)) {
            registrationInterface.onNext(true, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender, container, false);
        male = view.findViewById(R.id.toggle_male);
        female = view.findViewById(R.id.toggle_female);

        registrationInterface = (RegistrationInterface) getActivity();

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        male.setOnClickListener(v -> {
            male.setChecked(true);
            if (male.isChecked()) {
                female.setChecked(false);
            }

            if (validate(male.isChecked(), female.isChecked())) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_GENDER, "male");
                editor.apply();
                registrationInterface.onNext(true, "");
            }
        });

        female.setOnClickListener(v -> {
            female.setChecked(true);
            if (female.isChecked()) {
                male.setChecked(false);
            }

            if (validate(male.isChecked(), female.isChecked())) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_GENDER, "female");
                editor.apply();
                registrationInterface.onNext(true, "");
            }
        });
        return view;
    }

    private boolean validate(boolean male, boolean female) {
        if (!male && !female) {
            registrationInterface.onNext(false, getString(R.string.error_gender_empty));
            return false;
        }
        return true;
    }
}
