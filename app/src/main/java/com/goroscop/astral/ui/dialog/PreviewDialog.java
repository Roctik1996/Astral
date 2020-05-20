package com.goroscop.astral.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goroscop.astral.R;
import com.goroscop.astral.presenter.CancelPresenter;
import com.goroscop.astral.ui.PayActivity;
import com.goroscop.astral.ui.adapter.PreviewAdapter;
import com.goroscop.astral.view.ViewCancel;
import com.rd.PageIndicatorView;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_PRO;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TRIAL;
import static com.goroscop.astral.utils.Const.previewData;

public class PreviewDialog extends DialogFragment implements ViewCancel {
    private Button btnNext;
    private SharedPreferences mSettings;
    private MvpDelegate<PreviewDialog> mMvpDelegate;

    @InjectPresenter
    CancelPresenter cancelPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_preview, null);
        getMvpDelegate().onCreate(savedInstanceState);

        ViewPager2 previewPager = view.findViewById(R.id.preview_pager);
        PageIndicatorView pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
        TextView txtCancel = view.findViewById(R.id.txt_cancel);
        btnNext = view.findViewById(R.id.btn_next);
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        TextView txtRules = view.findViewById(R.id.txt_rules);
        txtRules.setMovementMethod(LinkMovementMethod.getInstance());
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        pageIndicatorView.setCount(4);
        pageIndicatorView.setSelection(2);
        previewPager.setOffscreenPageLimit(5);
        PreviewAdapter previewAdapter;
        if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false))
            previewAdapter = new PreviewAdapter(previewData, true);
        else
            previewAdapter = new PreviewAdapter(previewData, false);
        previewPager.setAdapter(previewAdapter);

        previewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageIndicatorView.setSelection(position);
                previewAdapter.notifyDataSetChanged();
                if (position == 3) {
                    txtCancel.setVisibility(View.VISIBLE);
                    pageIndicatorView.setVisibility(View.GONE);
                    if (mSettings.getBoolean(APP_PREFERENCES_PRO, false) || mSettings.getBoolean(APP_PREFERENCES_TRIAL, false)) {
                        btnNext.setText(R.string.btn_cancel_subscription);
                        txtRules.setVisibility(View.GONE);
                        checkBox.setVisibility(View.GONE);
                    } else {
                        btnNext.setText(R.string.btn_trial);
                        txtRules.setVisibility(View.VISIBLE);
                        checkBox.setVisibility(View.VISIBLE);
                    }
                } else {
                    txtCancel.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.VISIBLE);
                    btnNext.setText(R.string.dialog_btn);
                    txtRules.setVisibility(View.GONE);
                    checkBox.setVisibility(View.GONE);
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            if (btnNext.getText().toString().equals(getString(R.string.btn_cancel_subscription))) {
                cancelPresenter.cancelSubscription("Token " + mSettings.getString(APP_PREFERENCES_TOKEN, ""));
            }
            if (checkBox.isChecked()) {
                if (btnNext.getText().toString().equals(getString(R.string.btn_trial))) {
                    Intent intent = new Intent(getContext(), PayActivity.class);
                    startActivity(intent);
                    dismiss();
                    getActivity().finish();
                }
            } else
                previewPager.setCurrentItem(previewPager.getCurrentItem() + 1);

        });

        txtCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
            mMvpDelegate.onCreate();
            mMvpDelegate.onAttach();
        }

        return mMvpDelegate;
    }

    @Override
    public void cancelSubscription(String url) {
        Toast.makeText(getContext(), "Подписка успешно отменена", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showProgress(boolean isLoading) {

    }

    @Override
    public void showError(String error) {

    }
}
