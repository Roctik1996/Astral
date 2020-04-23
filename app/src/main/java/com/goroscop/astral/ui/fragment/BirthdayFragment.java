package com.goroscop.astral.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.RegistrationInterface;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.text.TextUtils.isEmpty;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_NAME;

public class BirthdayFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Calendar now = Calendar.getInstance();
    private TextView txtBirthday;
    private DatePickerDialog datePickerDialog;
    private RegistrationInterface registrationInterface;
    private Date date = null;
    private DateFormat formatter;
    private SharedPreferences mSettings;

    public BirthdayFragment() {
    }

    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        txtBirthday = view.findViewById(R.id.edt_birthday);
        registrationInterface = (RegistrationInterface)getActivity();

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        formatter = new SimpleDateFormat("dd MMMM, yyyy");

        if (validate(date,txtBirthday.getText().toString())){
            registrationInterface.onNext(true,"");
        }

        datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
        txtBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
            }
        });
        txtBirthday.setOnClickListener(v -> {
            datePickerDialog.show(getParentFragmentManager(), "DatePickerDialog");
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar birthday=Calendar.getInstance();
        birthday.set(year,monthOfYear,dayOfMonth);
        date = birthday.getTime();
        txtBirthday.setText(formatter.format(date));
        if (validate(date,txtBirthday.getText().toString())){
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putLong(APP_PREFERENCES_BIRTHDAY, date.getTime()/1000);
            editor.apply();
            registrationInterface.onNext(true,"");
        }
    }

    private boolean validate(Date date,String txtDate) {
        if (date==null&&isEmpty(txtDate)) {
            registrationInterface.onNext(false,getString(R.string.error_birthday_empty));
            return false;
        }
        return true;
    }
}
