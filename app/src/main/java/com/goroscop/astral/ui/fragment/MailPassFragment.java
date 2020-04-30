package com.goroscop.astral.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.RegistrationInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_EMAIL;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PASS;

public class MailPassFragment extends Fragment {
    private EditText edtMail, edtPass;
    private String mail = "";
    private String pass = "";
    private RegistrationInterface registrationInterface;
    private SharedPreferences mSettings;

    public MailPassFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
        if (validateMail(mail)&&validatePass(pass)) {
            registrationInterface.onNext(true, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail_pass, container, false);
        edtMail = view.findViewById(R.id.edt_mail);
        edtPass = view.findViewById(R.id.edt_pass);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        registrationInterface = (RegistrationInterface) getActivity();

        edtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mail = String.valueOf(s);
                if (validateMail(mail)) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_EMAIL, mail);
                    editor.apply();
                    registrationInterface.onNext(true, "next");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass = String.valueOf(s);
                if (validatePass(pass)) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_PASS, pass);
                    editor.apply();
                    registrationInterface.onNext(true, "completed");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private boolean validateMail(String email) {
        if (isEmpty(email)) {
            registrationInterface.onNext(false, getString(R.string.error_empty_mail));
            return false;
        } else if (!isEmailValid(email)) {
            registrationInterface.onNext(false, getString(R.string.error_email_invalid));
            return false;
        }
        return true;
    }

    private boolean validatePass(String password) {
        if (isEmpty(password)) {
            registrationInterface.onNext(false, getString(R.string.error_pass_empty));
            return false;
        } else if (!isPasswordValid(password)) {
            registrationInterface.onNext(false, getString(R.string.error_pass_invalid));
            return false;
        }
        return true;
    }

    private static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}
