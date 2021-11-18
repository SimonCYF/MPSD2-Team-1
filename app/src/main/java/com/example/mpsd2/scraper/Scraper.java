package com.example.mpsd2.scraper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.navigationonclick.Chat;
import com.example.mpsd2.news.NewsAdapter;
import com.example.mpsd2.swipe.SwipeMain;
import com.example.mpsd2.swipe.SwipeRight;
import com.example.mpsd2.userpackage.Fingerprint;
import com.example.mpsd2.userpackage.Profile;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Scraper extends AppCompatActivity {

    private TabLayout tabLayout;
    private Intent intent;
    private FirebaseAuth mAuth;

    //textview
    private TextView NewLocalCases;
    private TextView TotalCases;
    private TextView ActiveCases;
    private TextView NewDeath;
    private TextView TotalDeath;
    private TextView Recover;
    private TextView NewForeignCases;
    private TextView TotalRecover;

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private final int[] ICONS = new int[]{
            R.drawable.ic_baseline_text_snippet_24,
            R.drawable.ic_baseline_fiber_new_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_follow_the_signs_24};

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraper_view_new);

        //date and time
        dateTimeDisplay = (TextView) findViewById(R.id.scraperDate);
        NewLocalCases = (TextView) findViewById(R.id.scraperNewCasesLocal);
        NewForeignCases = (TextView) findViewById(R.id.scraperNewCasesImported);
        TotalCases = (TextView) findViewById(R.id.scraperTotalCases);
        ActiveCases = (TextView) findViewById(R.id.scraperActiveCases);
        NewDeath = (TextView) findViewById(R.id.scraperNewDeath);
        TotalDeath = (TextView) findViewById(R.id.scraperTotalDeath);
        Recover = (TextView) findViewById(R.id.scraperRecover);
        TotalRecover = (TextView) findViewById(R.id.scraperTotalRecover);



        tabLayout  = findViewById(R.id.tabLayout);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
        tabLayout.getTabAt(4).setIcon(ICONS[4]);
        tabLayout.getTabAt(5).setIcon(ICONS[5]);

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

        Content content = new Content();
        content.execute();
    }

    private void switchCase(int position) {
        switch (position) {
            case 0:
                intent = new Intent(Scraper.this, SwipeMain.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(Scraper.this, Scraper.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(Scraper.this, MapsFragment.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(Scraper.this, Fingerprint.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(Scraper.this, Profile.class);
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
                Toast.makeText(Scraper.this,"Logged Out",Toast.LENGTH_SHORT).show();
                //mAuth.signOut();
                intent = new Intent(Scraper.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(Scraper.this, Scraper.class);
                startActivity(intent);
            }
        });
        alert.create().show();
    }



    private class Content extends AsyncTask<Void,Void,Void>{

        private ArrayList<String> temp = new ArrayList<>();
        private ArrayList<String> justForDateAndTime = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                String url = "https://covidnow.moh.gov.my/";

                Document doc = Jsoup.connect(url).get();

                Elements caseNew = doc.getElementsByClass("chip bg-gray-300 px-2 font-semibold");
                Elements caseTotal = doc.getElementsByClass("number flex justify-center gap-1.5");
                Elements active = doc.getElementsByClass("bg-blue-100 px-2 m-auto");
                Elements death = doc.getElementsByClass("has-tooltip");
                Elements relative = doc.getElementsByClass("relative");
                Elements dateTime = doc.getElementsByClass("col-span-1 text-xs text-gray-500 text-right tracking-tighter leading-3");


                temp.add(caseNew.get(0).text());
                temp.add(caseNew.get(1).text());
                temp.add(caseNew.get(2).text());
                temp.add(caseNew.get(3).text());
                temp.addAll(Arrays.asList(caseTotal.get(0).text().split(" ",2)));
                temp.addAll(Arrays.asList(active.get(0).text().split(" ",2)));
                temp.add(death.get(12).text());
                temp.add(relative.get(40).text());
                temp.addAll(Arrays.asList(caseTotal.get(1).text().split(" ",2)));
                temp.addAll(Arrays.asList(caseTotal.get(2).text().split(" ",2)));
                temp.addAll(Arrays.asList(caseTotal.get(3).text().split(" ",2)));
                temp.addAll(Arrays.asList(caseTotal.get(4).text().split(" ",2)));

                justForDateAndTime.add(dateTime.get(0).text());



            }catch (IOException e){
                e.printStackTrace();
            }


            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("MPSD2", String.valueOf(temp));

            NewLocalCases.setText(temp.get(0));
            NewForeignCases.setText(temp.get(1));
            Recover.setText(temp.get(2));
            NewDeath.setText(temp.get(3));
            TotalCases.setText(temp.get(4));
            ActiveCases.setText(temp.get(5));
            TotalDeath.setText(temp.get(6));
            TotalRecover.setText(temp.get(12));
            dateTimeDisplay.setText(justForDateAndTime.get(0));

            return;
        }
    }


}
