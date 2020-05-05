package com.goroscop.astral.ui.fragment.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goroscop.astral.R;
import com.goroscop.astral.utils.Const;

import java.util.ArrayList;
import java.util.Objects;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.avatarIcon;
import static com.goroscop.astral.utils.Utils.getSign;

public class CompatibilityFragment extends Fragment {

    public CompatibilityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compatibility, container, false);

        ImageView avatar = view.findViewById(R.id.my_sign);
        ImageView partnerIcon = view.findViewById(R.id.you_sign);
        Button btnNext = view.findViewById(R.id.btn_next);

        ArrayList<String> data = new ArrayList<>();

        SharedPreferences mSettings = Objects.requireNonNull(getActivity()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        avatar.setImageResource(avatarIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            data.addAll(Const.datesSign.keySet());
            partnerIcon.setImageResource(avatarIcon.get(data.get(bundle.getInt("partner_sign"))));
        }

        partnerIcon.setOnClickListener(v -> loadFragment(new PartnerFragment()));

        btnNext.setOnClickListener(v -> {
            if (data.size() > 0) {
                bundle.putInt("partner_sign", bundle.getInt("partner_sign"));
                Fragment fragment = new CompatibilityResultFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
            } else {
                Toast.makeText(getContext(), "Укажите знак партнера", Toast.LENGTH_LONG).show();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
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
