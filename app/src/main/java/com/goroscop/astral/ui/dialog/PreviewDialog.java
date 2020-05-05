package com.goroscop.astral.ui.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.PayActivity;
import com.goroscop.astral.ui.adapter.PreviewAdapter;
import com.rd.PageIndicatorView;

import static com.goroscop.astral.utils.Const.previewData;

public class PreviewDialog extends DialogFragment {
    private ViewPager2 previewPager;
    private Button btnNext;

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
        previewPager = view.findViewById(R.id.preview_pager);
        PageIndicatorView pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
        TextView txtCancel = view.findViewById(R.id.txt_cancel);
        btnNext = view.findViewById(R.id.btn_next);
        /*CheckBox checkBox = view.findViewById(R.id.checkbox);
        TextView txtRules = view.findViewById(R.id.txt_rules);
        txtRules.setMovementMethod(LinkMovementMethod.getInstance());*/

        pageIndicatorView.setCount(4); // specify total count of indicators
        pageIndicatorView.setSelection(2);

        previewPager.setOffscreenPageLimit(5);
        previewPager.setUserInputEnabled(false);
        PreviewAdapter previewAdapter = new PreviewAdapter(previewData);
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
                    btnNext.setText(R.string.btn_trial);
                    /*txtRules.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.VISIBLE);*/
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            if (btnNext.getText().toString().equals(getString(R.string.btn_trial))) {
                Intent intent = new Intent(getContext(), PayActivity.class);
                startActivity(intent);
                dismiss();
                getActivity().finish();
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
}
