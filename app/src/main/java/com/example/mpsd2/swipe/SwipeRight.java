package com.example.mpsd2.swipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.example.mpsd2.chat.Consultant;
import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.navigationonclick.Chat;
import com.example.mpsd2.news.NewsAdapter;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.userpackage.Fingerprint;
import com.example.mpsd2.userpackage.Profile;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SwipeRight extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem mHome, mCovid, mEducation, mEntertainment;
    NewsAdapter newsAdapter;
    Toolbar mToolbar;
    private FirebaseAuth mAuth;

    private TabLayout tabLayout2;
    private TabItem news, updates, location, chat, profile, logout;
    private Intent intent;


    private final int[] ICONS = new int[]{
            R.drawable.ic_baseline_text_snippet_24,
            R.drawable.ic_baseline_fiber_new_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_follow_the_signs_24};

    String api = "b4904842e33645bc9ec0b619dd8dc3c3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_right_page);

        tabLayout2  = findViewById(R.id.tabLayout);

        tabLayout2.getTabAt(0).setIcon(ICONS[0]);
        tabLayout2.getTabAt(1).setIcon(ICONS[1]);
        tabLayout2.getTabAt(2).setIcon(ICONS[2]);
        tabLayout2.getTabAt(3).setIcon(ICONS[3]);
        tabLayout2.getTabAt(4).setIcon(ICONS[4]);
        tabLayout2.getTabAt(5).setIcon(ICONS[5]);

        news = findViewById(R.id.navToViewNews);
        location = findViewById(R.id.navToViewLocation);
        chat = findViewById(R.id.navToChat);
        updates = findViewById(R.id.navToViewUpdate);

        //declare button left
        ImageButton swipeLeft = (ImageButton) findViewById(R.id.btn_left_right);
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent swipeLeftIntent = new Intent(SwipeRight.this, SwipeMain.class);
                startActivity(swipeLeftIntent);
            }
        });

        //declare button right
        ImageButton swipeRight = (ImageButton) findViewById(R.id.btn_right_right);
        swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent swipeRightIntent = new Intent(SwipeRight.this, SwipeLeft.class);
                startActivity(swipeRightIntent);
            }
        });

        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchCase(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switchCase(tab.getPosition());
            }
        });




        //pager activity
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mHome = findViewById(R.id.homeNews);
        mCovid = findViewById(R.id.covid19News);
        mEducation = findViewById(R.id.educationNews);
        mEntertainment = findViewById(R.id.entertainmentNews);

        ViewPager viewPager=findViewById(R.id.fragmentConatainer);
        tabLayout=findViewById(R.id.include);

        newsAdapter=new NewsAdapter(getSupportFragmentManager(),4);
        viewPager.setAdapter(newsAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0||tab.getPosition() == 2||tab.getPosition() == 3||tab.getPosition() == 4){
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void switchCase(int position){
        switch (position){
            case 0:
                intent = new Intent(SwipeRight.this,SwipeMain.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(SwipeRight.this, Scraper.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(SwipeRight.this, MapsFragment.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(SwipeRight.this, Fingerprint.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(SwipeRight.this, Profile.class);
                startActivity(intent);
                break;

            case 5:
                showAlertDialog();
                break;

        }
    }


    private void showAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SwipeRight.this,"Logged Out",Toast.LENGTH_SHORT).show();
                //mAuth.signOut();
                intent = new Intent(SwipeRight.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(SwipeRight.this, SwipeMain.class);
                startActivity(intent);
            }
        });
        alert.create().show();

    }
}
