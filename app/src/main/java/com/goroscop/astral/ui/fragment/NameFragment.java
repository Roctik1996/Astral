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

import static android.text.TextUtils.isEmpty;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;

public class NameFragment extends Fragment {
    private EditText txtName;
    private String name = "";
    private RegistrationInterface registrationInterface;
    private SharedPreferences mSettings;

    public NameFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        txtName = view.findViewById(R.id.edt_name);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        registrationInterface = (RegistrationInterface) getActivity();
        if (validate(name)) {
            registrationInterface.onNext(true, "");
        }
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = String.valueOf(s);
                if (validate(name)) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_NAME, name);
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

    private boolean validate(String name) {
        if (isEmpty(name)) {
            registrationInterface.onNext(false, getString(R.string.error_name_empty));
            return false;
        }
        return true;
    }
}
