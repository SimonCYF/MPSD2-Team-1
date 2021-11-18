package com.example.mpsd2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.navigationonclick.Chat;
import com.example.mpsd2.navigationonclick.Maps;
import com.example.mpsd2.navigationonclick.RealtimeUpdates;
import com.example.mpsd2.navigationonclick.Setting;
import com.example.mpsd2.navigationonclick.ViewNews;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.swipe.SwipeMain;
import com.example.mpsd2.userpackage.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity_main);

        toolbar = findViewById(R.id.navigationToolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        androidx.appcompat.app.ActionBarDrawerToggle toggle = new androidx.appcompat.app.ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment,new ViewNews()).commit();
            navigationView.setCheckedItem(R.id.navigationDrawerViewNews);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigationDrawerViewNews:
                Intent intent = new Intent(NavigationActivity.this, SwipeMain.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment,new ViewNews()).commit();
                break;

            case R.id.navigationDrawerViewRealtimeNews:
                Intent intent1 = new Intent(NavigationActivity.this, Scraper.class);
                startActivity(intent1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment,new RealtimeUpdates()).commit();
                break;

            case R.id.navigationDrawerNearbyClinic:
                //Intent intent2 = new Intent(NavigationActivity.this, MapsFragment.class);
                //startActivity(intent2);
                getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment,new Maps()).commit();
                break;

            case R.id.navigationDrawerChat:
                //Intent intent3 = new Intent(NavigationActivity.this, SwipeMain.class);
                //startActivity(intent3);
                getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment,new Chat()).commit();
                break;

            case R.id.navigationDrawerSettings:
                //Intent intent4 = new Intent(NavigationActivity.this, SwipeMain.class);
                //startActivity(inten4t);
                getSupportFragmentManager().beginTransaction().replace(R.id.navigationFragment,new Setting()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}