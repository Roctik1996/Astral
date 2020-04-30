package com.goroscop.astral.ui.fragment.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goroscop.astral.R;
import com.goroscop.astral.utils.Const;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import hiennguyen.me.circleseekbar.CircleSeekBar;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_BIRTHDAY;
import static com.goroscop.astral.utils.Const.avatarIcon;
import static com.goroscop.astral.utils.Const.signEn;
import static com.goroscop.astral.utils.Utils.getSign;
import static java.lang.Thread.sleep;

public class CompatibilityResultFragment extends Fragment {
    private SharedPreferences mSettings;
    private TextView txtCompatibilityInfo, txtPercentCompatibilityPreview, txtPercentCompatibilityContent;
    private ConstraintLayout layoutPreview, layoutContent;
    private CircleSeekBar circularCompatibilityPreview, circularCompatibilityContent;
    private int percent;
    private String url;

    public CompatibilityResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compatibility_result, container, false);

        ImageView avatar = view.findViewById(R.id.my_sign);
        ImageView partnerIcon = view.findViewById(R.id.you_sign);
        ImageView avatarContent = view.findViewById(R.id.my_sign_content);
        ImageView partnerIconContent = view.findViewById(R.id.you_sign_content);
        Button btnMain = view.findViewById(R.id.btn_main);
        txtPercentCompatibilityPreview = view.findViewById(R.id.txt_percent);
        txtCompatibilityInfo = view.findViewById(R.id.txt_compatibility_info);
        circularCompatibilityPreview = view.findViewById(R.id.circular_compatibility);
        circularCompatibilityContent = view.findViewById(R.id.circular_compatibility_content);
        txtPercentCompatibilityContent = view.findViewById(R.id.txt_percent_content);
        layoutPreview = view.findViewById(R.id.layout_preview);
        layoutContent = view.findViewById(R.id.layout_content);

        ArrayList<String> data = new ArrayList<>();

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        avatar.setImageResource(avatarIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));
        avatarContent.setImageResource(avatarIcon.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, ""))));

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            data.addAll(Const.datesSign.keySet());
            partnerIcon.setImageResource(avatarIcon.get(data.get(bundle.getInt("partner_sign"))));
            partnerIconContent.setImageResource(avatarIcon.get(data.get(bundle.getInt("partner_sign"))));
        }

        String mySign = signEn.get(getSign(mSettings.getString(APP_PREFERENCES_BIRTHDAY, "")));
        String partnerSign = signEn.get(data.get(bundle.getInt("partner_sign")));

        percent = generatePercent();
        url = generateUrl(mySign, partnerSign);

        getPreview();

        btnMain.setOnClickListener(v -> loadFragment(new HomeFragment()));

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
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private int generatePercent() {
        int min = 40;
        int max = 100;
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;
        return i;
    }

    private String generateUrl(String mySign, String partnerSign) {
        return "https://orakul.com/horoscopecomp/man_woman/" + mySign + "_" + partnerSign + ".html";
    }

    private void getPreview() {
        Animation previewAnim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        layoutPreview.setAnimation(previewAnim);

        previewAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mSettings.contains(url)) {
                    if (mSettings.getInt(url, 0) != 0) {
                        animatePercent(mSettings.getInt(url, 0));
                    } else {
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putInt(url, percent);
                        editor.apply();
                        animatePercent(percent);
                    }
                } else {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt(url, percent);
                    editor.apply();
                    animatePercent(percent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void animatePercent(int percent) {
        Thread t = new Thread(() -> {
            while (circularCompatibilityPreview.getProgressDisplay() < percent) {
                circularCompatibilityPreview.setProgressDisplayAndInvalidate(circularCompatibilityPreview.getProgressDisplay() + 1);
                txtPercentCompatibilityPreview.setText(circularCompatibilityPreview.getProgressDisplay() + "%");
                circularCompatibilityContent.setProgressDisplayAndInvalidate(circularCompatibilityPreview.getProgressDisplay() + 1);
                txtPercentCompatibilityContent.setText(circularCompatibilityPreview.getProgressDisplay() + "%");

                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new ParseData().execute();

        });
        t.start();
    }

    private class ParseData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();
            try {
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36").get();
                Elements newsHeadlines = doc.select("p");
                result.append(newsHeadlines.get(2));
            } catch (IOException e) {
                Log.d("TAG", e.getMessage());
            }
            String data = result.toString().replaceAll("<p>", "").replaceAll("</p>", "");

            return data;
        }


        @Override
        protected void onPostExecute(String result) {
            Animation previewAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_to_top);
            Animation contentAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_to_top_content_comp);
            layoutPreview.setAnimation(previewAnim);
            layoutPreview.startAnimation(previewAnim);
            layoutContent.setVisibility(View.VISIBLE);
            layoutContent.setAnimation(contentAnim);
            layoutContent.startAnimation(contentAnim);
            txtCompatibilityInfo.setText(Html.fromHtml(result));
            Log.d("TAG", result);
        }
    }
}
