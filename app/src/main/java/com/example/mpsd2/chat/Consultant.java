package com.example.mpsd2.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.swipe.SwipeMain;
import com.example.mpsd2.userpackage.Fingerprint;
import com.example.mpsd2.userpackage.Profile;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class Consultant extends AppCompatActivity {

    private final int[] ICONS = new int[]{
            R.drawable.ic_baseline_text_snippet_24,
            R.drawable.ic_baseline_fiber_new_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_follow_the_signs_24};

    private TabLayout tabLayout = null, menuTabLayout;
    private ViewPager viewPager;
    private Intent intent;
    private FirebaseAuth mAuth;
    private Button viewHelp, backBtn;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private boolean test;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate((savedInstancesState));
        setContentView(R.layout.activity_consultant);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ConsultantsFragment(), "Consultants");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    createNewContactDialog();
                    return false;
                }
            });
        }

        menuTabLayout = findViewById(R.id.tabLayout);

        menuTabLayout.getTabAt(0).setIcon(ICONS[0]);
        menuTabLayout.getTabAt(1).setIcon(ICONS[1]);
        menuTabLayout.getTabAt(2).setIcon(ICONS[2]);
        menuTabLayout.getTabAt(3).setIcon(ICONS[3]);
        menuTabLayout.getTabAt(4).setIcon(ICONS[4]);
        menuTabLayout.getTabAt(5).setIcon(ICONS[5]);


        menuTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopUp = getLayoutInflater().inflate(R.layout.activity_setting, null);
        dialogBuilder.setView(contactPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        backBtn = (Button) contactPopUp.findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //intent = new Intent(Consultant.this, Consultant.class);
                //startActivity(intent);
            }
        });

    }

    private void switchCase(int position) {
        switch (position) {
            case 0:
                intent = new Intent(Consultant.this, SwipeMain.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(Consultant.this, Scraper.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(Consultant.this, MapsFragment.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(Consultant.this, Fingerprint.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(Consultant.this, Profile.class);
                startActivity(intent);
                break;

            case 5:
                showAlertDialog();
                break;

        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Consultant.this, "Logged Out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                intent = new Intent(Consultant.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(Consultant.this, Consultant.class);
                startActivity(intent);
            }
        });
        alert.create().show();

    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


}
