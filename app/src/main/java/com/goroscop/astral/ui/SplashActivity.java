package com.goroscop.astral.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goroscop.astral.R;

public class SplashActivity extends AppCompatActivity {

    private Animation logoAnim,layoutAnim;
    private ImageView logo;
    private LinearLayout layoutAstral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        layoutAstral = findViewById(R.id.layout_astral);

        logoAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        layoutAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);

        logo.startAnimation(logoAnim);
        layoutAstral.startAnimation(layoutAnim);

        layoutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent loginActivity = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
