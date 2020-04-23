package com.goroscop.astral.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.goroscop.astral.R;
import com.goroscop.astral.ui.adapter.HoroscopeAdapter;
import com.goroscop.astral.ui.adapter.LuckyNumAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final String[] tabTitle = new String[]{"Сегодня",
            "Завтра",
            "Неделя",
            "Месяц",
            "Год",
    };

    private ViewPager2 horoscopePager;
    private TabLayoutMediator tabLayoutMediator;
    private TabLayout tabLayout;
    private RecyclerView recyclerLucky;
    private ArrayList<Integer> count = new ArrayList<>();

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        horoscopePager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerLucky = view.findViewById(R.id.recycler_num);

        horoscopePager.setAdapter(new HoroscopeAdapter(tabTitle));

        tabLayoutMediator = new TabLayoutMediator(tabLayout, horoscopePager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        });
        tabLayoutMediator.attach();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerLucky.setLayoutManager(layoutManager);
        for (int i = 1; i < 6; i++)
            count.add(i);
        recyclerLucky.setAdapter(new LuckyNumAdapter(count));

        return view;
    }
}
