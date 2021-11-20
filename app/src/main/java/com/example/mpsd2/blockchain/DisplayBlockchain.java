package com.example.mpsd2.blockchain;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.animation.content.Content;
import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.example.mpsd2.chat.Chat;
import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.swipe.SwipeMain;
import com.example.mpsd2.userpackage.Fingerprint;
import com.example.mpsd2.userpackage.Profile;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.GsonBuilder;

public class DisplayBlockchain extends AppCompatActivity {

    private TabLayout tabLayout;
    private Intent intent;
    private FirebaseAuth mAuth;
    private TextView textView;

    private final int[] ICONS = new int[]{
            R.drawable.ic_baseline_text_snippet_24,
            R.drawable.ic_baseline_fiber_new_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_follow_the_signs_24};

   // public @SuppressLint("WrongConstant")
    //SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blockchain);

        tabLayout  = findViewById(R.id.tabLayout);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
        tabLayout.getTabAt(4).setIcon(ICONS[4]);
        tabLayout.getTabAt(5).setIcon(ICONS[5]);

        textView = (TextView) findViewById(R.id.textViewBlockDisplay);

        @SuppressLint("WrongConstant")
        SharedPreferences sp = getSharedPreferences("SharedPref", MODE_PRIVATE);

        String msg = sp.getString("block","");

        String blockDisplay = new GsonBuilder().setPrettyPrinting().create().toJson(msg);
        textView.setText(blockDisplay);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void switchCase(int position) {
        switch (position) {
            case 0:
                intent = new Intent(DisplayBlockchain.this, SwipeMain.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(DisplayBlockchain.this, Scraper.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(DisplayBlockchain.this, MapsFragment.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(DisplayBlockchain.this, Fingerprint.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(DisplayBlockchain.this, Profile.class);
                startActivity(intent);
                break;

            case 5:
                showAlertDialog();
        }
    }

    private void showAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DisplayBlockchain.this,"Logged Out",Toast.LENGTH_SHORT).show();
                //mAuth.signOut();
                intent = new Intent(DisplayBlockchain.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(DisplayBlockchain.this, Scraper.class);
                startActivity(intent);
            }
        });
        alert.create().show();

    }
}
