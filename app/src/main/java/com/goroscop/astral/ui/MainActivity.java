package com.goroscop.astral.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.goroscop.astral.R;
import com.goroscop.astral.ui.interfaces.NavigationInterface;

public class MainActivity extends AppCompatActivity implements NavigationInterface {
    NavigationLayout navigationLayout;
    ConstraintLayout menu;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu=findViewById(R.id.left_drawer);

        navigationLayout=new NavigationLayout(menu,this);
        menu.addView(navigationLayout);

        /*drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.close, R.string.close);
        actionBarDrawerToggle.syncState();*/
    }

    @Override
    public void onHomePressed() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void onCompatibilityPressed() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void onChinaPressed() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void onElementPressed() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void onMetalPressed() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void onPlanetPressed() {
        drawerLayout.closeDrawers();
    }
}
