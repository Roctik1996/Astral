package com.goroscop.astral.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.goroscop.astral.R;

import static com.goroscop.astral.utils.Const.APP_PREFERENCES;
import static com.goroscop.astral.utils.Const.APP_PREFERENCES_TOKEN;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logo = findViewById(R.id.logo);
        LinearLayout layoutAstral = findViewById(R.id.layout_astral);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation layoutAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);

        logo.startAnimation(logoAnim);
        layoutAstral.startAnimation(layoutAnim);

        layoutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mSettings.contains(APP_PREFERENCES_TOKEN)){
                    if (!mSettings.getString(APP_PREFERENCES_TOKEN,"").equals("")){
                        Intent loginActivity = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(loginActivity);
                        finish();
                    }
                    else {
                        Intent loginActivity = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(loginActivity);
                        finish();
                    }
                }
                else{
                    Intent loginActivity = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
