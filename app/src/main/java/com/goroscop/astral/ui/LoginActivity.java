package com.goroscop.astral.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.goroscop.astral.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends MvpAppCompatActivity {

    private EditText edtMail,edtPass;
    private Button btnLogin;
    private TextView txtReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtMail = findViewById(R.id.edt_mail);
        edtPass = findViewById(R.id.edt_pass);
        btnLogin = findViewById(R.id.btn_login);
        txtReg = findViewById(R.id.txt_registration);

        btnLogin.setOnClickListener(v -> {
            if (validate(edtMail.getText().toString(),edtPass.getText().toString())){
                Toast.makeText(this, "OK",Toast.LENGTH_LONG).show();
            }
        });

        txtReg.setOnClickListener(v -> {
            Intent regActivity = new Intent(this,MainActivity.class);
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
}
