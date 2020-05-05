package com.goroscop.astral.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goroscop.astral.R;
import com.goroscop.astral.model.Token;
import com.goroscop.astral.presenter.LoginPresenter;
import com.goroscop.astral.view.ViewGetToken;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class LoginActivity extends MvpAppCompatActivity implements ViewGetToken {

    private SharedPreferences mSettings;

    private ProgressBar progressBar;
    private FrameLayout frame;

    @InjectPresenter
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edtMail = findViewById(R.id.edt_mail);
        EditText edtPass = findViewById(R.id.edt_pass);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView txtReg = findViewById(R.id.txt_registration);

        progressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.frame);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            if (validate(edtMail.getText().toString(),edtPass.getText().toString())){
                loginPresenter.login(edtMail.getText().toString(),edtPass.getText().toString());
            }
        });

        txtReg.setOnClickListener(v -> {
            Intent regActivity = new Intent(this,RegistrationActivity.class);
            startActivity(regActivity);
            finish();
        });
    }

    private boolean validate(String email, String password) {
        if (isEmpty(email)) {
            Toast.makeText(this, R.string.error_empty_mail,Toast.LENGTH_LONG).show();
            return false;
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, R.string.error_email_invalid,Toast.LENGTH_LONG).show();
            return false;
        }
        if (isEmpty(password)) {
            Toast.makeText(this, R.string.error_pass_empty,Toast.LENGTH_LONG).show();
            return false;
        } else if (!isPasswordValid(password)) {
            Toast.makeText(this, R.string.error_pass_invalid,Toast.LENGTH_LONG).show();
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

    @Override
    public void getToken(Token token) {
        if (token!=null) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_TOKEN, token.getToken());
            editor.apply();
            Intent mainActivity = new Intent(this,MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }

    @Override
    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        frame.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, R.string.error_invalid_mail_pass, Toast.LENGTH_LONG).show();
    }
}
