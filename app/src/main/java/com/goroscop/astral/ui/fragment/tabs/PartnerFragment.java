package com.goroscop.astral.ui.fragment.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.adapter.SignAdapter;
import com.goroscop.astral.utils.Const;

import java.util.ArrayList;
import java.util.Objects;

public class PartnerFragment extends Fragment implements SignAdapter.ItemClick {

    public PartnerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choise_partner, container, false);
        RecyclerView partnerRecycler = view.findViewById(R.id.recycler_partner);

        ArrayList<String> data = new ArrayList<>(Const.datesSign.keySet());
        SignAdapter signAdapter = new SignAdapter(data, this);
        partnerRecycler.setAdapter(signAdapter);

        partnerRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                loadFragment(new CompatibilityFragment());
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

    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("partner_sign", position);
        Fragment fragment = new CompatibilityFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }
}
