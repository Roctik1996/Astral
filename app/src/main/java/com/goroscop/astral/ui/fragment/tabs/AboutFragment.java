package com.goroscop.astral.ui.fragment.tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goroscop.astral.R;

import java.util.Objects;

public class AboutFragment extends Fragment {

    public AboutFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        LinearLayout rulesFirst = view.findViewById(R.id.rules_1);
        LinearLayout rulesSecond = view.findViewById(R.id.rules_2);
        LinearLayout rulesThird = view.findViewById(R.id.rules_3);


        rulesFirst.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://7astral.ru/oferta.pdf"));
            startActivity(browserIntent);
        });

        rulesSecond.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://7astral.ru"));
            startActivity(browserIntent);
        });

        rulesThird.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:support@7astral.ru");
            intent.setData(data);
            startActivity(intent);
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                loadFragment(new HomeFragment());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }
}
