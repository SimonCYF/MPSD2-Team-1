package com.example.mpsd2.swipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.navigationonclick.Chat;
import com.example.mpsd2.news.NewsAdapter;
import com.example.mpsd2.pdf.ItemClickListener;
import com.example.mpsd2.pdf.PDFActivity;
import com.example.mpsd2.pdf.PDFAdapter;
import com.example.mpsd2.pdf.PDFModel;
import com.example.mpsd2.recycleview.RecycleViewAdapter;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.userpackage.Fingerprint;
import com.example.mpsd2.userpackage.Profile;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class SwipeLeft extends AppCompatActivity {
    private static final String TAG = "SwipeLeft";

    private TabLayout tabLayout;
    private Intent intent;
    private FirebaseAuth mAuth;

    private final int[] ICONS = new int[]{
            R.drawable.ic_baseline_text_snippet_24,
            R.drawable.ic_baseline_fiber_new_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_follow_the_signs_24};



    //vars
    private ArrayList<String> nameOfVaccine = new ArrayList<>();
    private ArrayList<String> imageOfVaccine = new ArrayList<>();
    private ArrayList<String> detailsOfVaccine = new ArrayList<>();


    //vars for pdf list
    public static List<PDFModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_left_page);

        setTabCount();

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
        tabLayout.getTabAt(4).setIcon(ICONS[4]);
        tabLayout.getTabAt(5).setIcon(ICONS[5]);

        //declare button left
        ImageButton swipeLeft = (ImageButton) findViewById(R.id.btn_left_left);
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent swipeLeftIntent = new Intent(SwipeLeft.this, SwipeRight.class);
                startActivity(swipeLeftIntent);
            }
        });

        //declare button right
        ImageButton swipeRight = (ImageButton) findViewById(R.id.btn_right_left);
        swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent swipeRightIntent = new Intent(SwipeLeft.this, SwipeMain.class);
                startActivity(swipeRightIntent);
            }
        });

        //get the function on vaccines name and image
        getVaccines();

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.RV);

        list = new ArrayList<>();
        list.add(new PDFModel("Vaccine","https://www.vaksincovid.gov.my/pdf/National_COVID-19_Immunisation_Programme.pdf"));
        list.add(new PDFModel("Clinical", "https://covid-19.moh.gov.my/garis-panduan/garis-panduan-kkm/ANNEX_48_CLINICAL_GUIDELINES_FOR_COVID_IN_MALAYSIA_3rd_EDITION_12072021.pdf"));
        list.add(new PDFModel("Efficiencies","https://covid-19.moh.gov.my/kajian-dan-penyelidikan/mahtas-covid-19-rapid-evidence-updates/COVID-19_VACCINES_editted.pdf"));
        list.add(new PDFModel("Immunisation","https://www.fondation-merieux.org/wp-content/uploads/2017/10/vaccinology-2017-faridah-kusnin.pdf"));

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(SwipeLeft.this, PDFActivity.class);
                //intent.putExtra("url",list.get(position).getPdfUrl());
                intent.putExtra("position",position);
                startActivity(intent);
            }
        };

        PDFAdapter adapter = new PDFAdapter(list,this,itemClickListener);
        recyclerView.setAdapter(adapter);




    }

    private void setTabCount(){

        tabLayout  = findViewById(R.id.tabLayout);

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

    private void switchCase(int position){
        switch (position){

            case 0:
                intent = new Intent(SwipeLeft.this,SwipeMain.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(SwipeLeft.this, Scraper.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(SwipeLeft.this, MapsFragment.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(SwipeLeft.this, Fingerprint.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(SwipeLeft.this, Profile.class);
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
                Toast.makeText(SwipeLeft.this,"Logged Out",Toast.LENGTH_SHORT).show();
               // mAuth.signOut();
                intent = new Intent(SwipeLeft.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(SwipeLeft.this, SwipeMain.class);
                startActivity(intent);
            }
        });
        alert.create().show();

    }

    private void getVaccines(){

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        //adding pzifer information
        nameOfVaccine.add("BioNTech,Pfizer");
        imageOfVaccine.add("https://www.reuters.com/resizer/E_DrXOrtOPSPy_kIbjpZ3YWdc6o=/1920x2400/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/NOLAOAVXFZJJVPRJ64BGJWCHOA.jpg");
        detailsOfVaccine.add("Brand Name:Cormitary\nVaccine Name:BNT162b2\nType of Vaccine:mRNA\nNumber of Shots:2, 21 Days Apart\nHow Given:Shot in the arm muscle\nTemperature:-80°C to -60°C \n\n");

        //adding cansino information
        nameOfVaccine.add("CanSino");
        imageOfVaccine.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlYEPFS3VHOiVHc6HkUqaQGhxa5l7lGsJ8UA&usqp=CAU");
        detailsOfVaccine.add("Brand Name:CanSino\nVaccine Name:AD5-nCOV\nType of Vaccine:Non Replicating Viral Vector\nNumber of Shots:1\nHow Given:Shot in the arm muscle\nTemperature:2°C to 8°C \n\n");

        //adding sinovac information
        nameOfVaccine.add("CoronaVac, Sinovac");
        imageOfVaccine.add("https://static.straitstimes.com.sg/s3fs-public/articles/2021/02/06/nz_sinovac_060233.jpg");
        detailsOfVaccine.add("Brand Name:SinoVac\nVaccine Name:CoronaVac\nType of Vaccine:Inactivated\nNumber of Shots:2, 21 Days Apart\nHow Given:Shot in the arm muscle\nTemperature:2°C to 8°C \n\n");

        //adding j&j information
        nameOfVaccine.add("Johnson & Johnson");
        imageOfVaccine.add("data:image/jpeg;base64,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");
        detailsOfVaccine.add("Brand Name:Johnson & Johnson\nVaccine Name:JNJ-78436735\nType of Vaccine:Viral Vector\nNumber of Shots:1\nHow Given:Shot in the arm muscle\nTemperature:2°C to 8°C \n\n");

        //adding AZr information
        nameOfVaccine.add("Oxford, AstraZeneca");
        imageOfVaccine.add("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUSFRISEhUSGRESGBgYEhgSGBgRGBgUGBgZGhkYGBgcIS4lHB4rIRgYJjgmKy8xNTU1HCQ7QDs0Py40NTEBDAwMEA8QHhISHjQrJCs0NDQ0NDQ0NDQ0MTQ0NDQ0NDQ0NDQ0NDQxNDQ0NDQ0NDQ1NDQ0NDQ0NDQ0NDQ0NDQ0NP/AABEIAMEBBQMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAADAAECBAUGBwj/xAA+EAACAQIEAwUGBQQBAwQDAAABAgADEQQSITEFQVETImFxgQYyQlKRoSNyscHwFGKC0eEHM5IkosLxU2Oy/8QAGQEAAwEBAQAAAAAAAAAAAAAAAAECAwQF/8QAKREAAwEAAgICAQIGAwAAAAAAAAECEQMhEjFBUQQTIjJSYYGRwRRCcf/aAAwDAQACEQMRAD8A6xBDLApCBp2NnMlhYQw6GVUMMhmdFJllYVTAoZNWmTLQdY5kFMcmAxyYNjEzQTNBAxOYB2k3aBJm0oyokDJQYMkDNCCUeRvHvACUaNeK8AJRXkby3QwRbvN3V+58hJbS9jSb9AEUsbKCT4S/RwYXV9T8o29TDJZRZBYfc+ZjXmF8rfo2njS9gscruhWk4RuRyZ7DwFxbz1gsLhWCKKzio41zMirY+AA+8Oay/Mv1EgtdTblfa/P1mHnO+zXxYa8aNFLEKNFFABRQVautMAuwGY5VvuzEEhQOZ0OklTqBhddvEEEHmCDqD4GADU6qsLqbj9jzjOMt21tzA19ZWqoabF1sFJW4sWJtpa+awFjpyEto4YAjYwAffURQQsvWx2Aubddo8AOfWOJAGSvOs59CIYZGlUNCK8TQtLiNCK8pCrCI8zclKi8rx80ro8nnkNGiYRjBOY5aCdo0DIO0EXkmgWE2lmVIIHjh5XvJKZZGMsBpK8giSzToE6AayXSRSlgbw+Hw7ObKNOZOgHrLi4RUGaqQB8o/eTV3q91Bkp9diR4TKubPRc8f2QNMUgciipVHUhQD5EzFr8TxCNd9L8mUW9D/AMzpafDqYFiobqW3v+0rYjAMAezIZOdOr3gfJjqPWTN/zLSqj+V4ZmG4+p0qLl/uXUeo3H3mt2ylc6m62uCut/Kc5ieGh83ZXV19+k+hH5SdxM7CY96DHLffvo2x8xyPjLfFNzsPsz/VqHlLo6YONSSArX7oOx5XhWyAO1gQoLHKMxsBc2A3Om3jFgMelZcy7j3lO6n9x4yzOCfxnDysf9jq81S1GbQ4oSyioqoKhApLmzPYgkMw2IPVCwHPnbTlWpTKf9tA1Mhs6KFW5a3eF7A8wQSL38Na3DcUArBtAHZQqnMKYuFCMemYNqLqNRfSdTW9ogPxKkzqoBfJm/EFMlXZMraKRqO9lJtrYG0r4BRTVqh/DornBFQZMwuuR2Gyn3xsCbg+E1JTqYINUWoxLBfdR9VQ/Mg5N4m510tJGFr0RUVdvmQkZhqpGo5gqxBHjKPDy6vUUkuqt+JUf8MZgiBVRNSbKFuzH1MvpXuXDDKVIFzswb3SD47W6jyJocWwofXIXaxJRmNOk9iovVIBzZQbgG9wDpADSYAixAIPXUESlhiabGmx0suS2YgDqxsFXkAB0iwNZrLTqHMxXOrqoRHU2NkAJIC5lFm1566yeJpLcVMhd00Vc1gLkd6zHKCPmte1/KAFq8Ur4WrnUFrA2FwQV310DFTb+abRQAwWkC8I4gHnYjkoRqQbYiDcyrVe02mUzlvkcl5MRLdOtOfSvrLtGvC+IXD+R5G4lSFFSZVOtDrVnNUHZNpl7tIxeVe0hFeT44X5aTJkbRxJAQ3CkiAp3hkox0WLEY9KYJdgFXRj/d8qjm0l0ysRcw2Gv5cyZY/qQvcormfmx2HrKeHqNVAJulLkB7zf6HjLLVVprZRlUdN/rMqpspIktEXzVDnf/wBqx3xFtTr0voPQc5WbEAgEEG+wGtvPx8Jn4nFa6nWRpeF/EcRbkSPLSU34m4+N/rf9Zm18YACSRbqZg4zjNyVp79T+0a0RrcY4y+ZLu2Ye41lFv8gLiWqNZMYMrFVxSjusLZXsNjbn/BzE5zD8PNSz1iQG90bu35ROnwXCe4AVyINVA9/MNmLfCZpNOXqIqVSx+jOpl6L3F1dDYg/oeoM63h+NWsuYaMPfXof9SjicMK62OldB3SdM69DMbDV2pPmGjLowOlxzUzoecs78nOt4qz4OymPxXCEAFGIVnClTZUGdrnOwGbIW1t1OhF5pYbEK6h12P2PMHxhGAIIOx0PlOdNpnT7AYesL9mzh6qjNUsAtrnmBou+gOtuu8POdxI/ps9Omr94/+nSmrGzhBkYMPeu2YOHJ3B2m9SqhwSDsSrb6MpsRr/DoecGs7BGRjsGc5qVUqYhdezSmFUU2B7pVCwBa3xk3BGlgdLvCsUKiAF0epTAStkOdRUCjMuYaMRfW3O8PiKAqLkYnIbhwDYMpUqVbnbW+ljcCZPDzWp1VoZsOaVNLutKgaCoG0p01JqMMx1NraBf7hJGT/pXSoxSo7GjTBpJVyimqszBluoDFrJlzNfKGG+t9WjUDqrrfK6hhfQ2YXF/rKHHMKlSkxdKblAbdpcoASMxcAjMgHeK7HLIcFruRUp1HZ3p5S7FQmVnzXQAACy5bi3wsm+5ADU6b02cIlQqSCLOFA02ubsfXQC1ucUNiqea3eItfYkX28YoAYzys5hnMqVXnXJyUAqtM7E1JYxNSZdd7zs4pPN/JrERNSFpYsiU2aRLTpcprs82bqXqN6hjAZcTEXnLK5EsU8WRMa4U/R2cf5bXVHULUlukZgYLFZpt4d5x8kePR6fBfmtLiwgEGsMs52dsiZwilibADfeYScOevUWpUsEQ3poTmtr7zjbNNjE4unTsHN2+RO83hcDUCWMOFKlgBre/Ow6AdZlVYUlpFsR2a96wUbkm0o4/jCAZcw15jVfQiRxuEp1LjKy/3KST6i9vtOWxPCKlN/wAO70z7wHL/AA3+klNfI8HxftOKT2UknmF1FvGbj02qAOm1QArryO5vyE5nh/so9R0LArTYktfUjXbrr1npGC4WiqEVRZB3b8uUyvklNKSpl49OO4jwisxF7ZOZ2A8/DxhuF8H1/DUMw3qOO4v5R8Rnb1cErqAx8xyPnGWllsABYbcgPTlNOOm12KkUMDwxKfeN2qHd21PoPhE0VX6SSp6mTJA8T9poSVMRh9mBy21U87zI4th+0XtkFnXSqo8Pi/nLym3iKwUXY77cyfKZVWu1MipbubVE3JQ/Fbwlzs/uRF5XTM3g3EuzfKx7j6H+1uTf7/4nVziONYTsn7utNxmpncZTyv4fpab/ALO8Q7VMjHv07A+KfCf29PGacsJyrkx4rapxQbivaK1N0qulO+SpZUcXcgI7BgTbN3TYj3wb6GVMPiGpYns6tWiz1lHdpo6NnUHK7qWYLdRluT3sqgDSbZEwOJ4VUaretToirdy7D3k7oqK17DOtlKNmJGY92wN8pafTOlm/MnjWFR2pvUoNWpqHvTVVqXqHJkcoxANgrKGPu5uQuRp4euKiioubK2ozKyG3irAEesr8Sq9mKdRmy00cGq17AJlYDMeS5yhJ2sDfS8zGRwDs1BcrU2qKuRu/2qioosyOy7kEWPPQwGGasrpSJw/Zqt3WmjpkSxCAMXI1I0GXZW20uTh9VKj1alKxpMEGdfdd1z5mU7MLFBmG9rfDLVCgEzWvd2LMTqST1PgAAPACABoo0UAObcynXEuPK1RZ1Sc1Ix8TM+oJr16d5UejOzjrDz+bi8jNZYMrNBqMG1Ga/qHN/wAcpZYrSw1KKnQvcscqIMzseQvbQcySQAOZMHypLWNfja8SL/CKLOQqAlug19fKa+GxKF+ypl61VffXCqKioej1WZUU+AJM572Z4nh8RVqJi6q0cKgHZ0XbshVYnes/xafCTbXbeesYAUsi9h2fZAdzssuS3hl0nmc3O6r9p7HB+OolJmRQwFTTMhXwDJUI89QJcrYZUQlSbj3yQQyjqByHj95pyLqDv/r78pg6bOjEcy/CKVPv0z331LHW/jfpK1KoUcKxy336ETTq8MNIMaZZqRuSl8xU82Qn9OfnrMrEWtYm6m5Vh/NCOkkZZqsVzkgXXU+XIjqDOaxPF37VOz7xB90X73hcawZ4y61Qtvd7yj/8lLZsh5kb28Ju4vgQUh6QVEqcwGLEndR8o+kazcYPc6N7hDq40N7Dv2ILKeh6+c0xSOgG3LymJgMCaYUA2tsBpcnct1m9hMSGuraMv38RMOTh+ilYY0tPSUstiQeXSaKm4/WUcWlj3SPXXWa8U48Jp9EHqWHICVlqM+iDTmx29JWq0jcGoxYclXQQWNx2UWa4HwogLMfMD9TpOjEjLdDOyqe73n5sdQPKValYA66k7319Lc5Rp18RVOWnSyA/ExufTLz8rzYwXs+zEPXYBfkTn+ZungPrFVtdIcyn2zJNA1qLUrHPTBfD33ZASLDqNCPVekxOE43saqVPh91/yHf6aH0nf8bo5VSsg1oG9gLXpnR1HpY+k4bj+FFOsxX3Kgzpbazbj639CJt+PSacM5/yJctWjuZncbpuURqaUmem4fNWsUpgAhqltLkAnYjzEj7PYrtKCX95O43+O32Imi6BgVIurAgjqCLETnac1j+DqmlUpr5M3hLlWqU2LMzO7Z2YXdkyI5yAAIB3AAL3FjveakxeH1FFTLnJUA9mzlA9Z3Y5m0A7oyqAANd9dDNqKvY0KMTIO9tBqeg/mko4zGrTF3N25KNgf3MhsZqUqeYX+kU5OriMTWOZLqvIRSfMrAzCCdJYtGKTtRysoPTld6U03SDalNFRlU6ZhoSDUJpmlItSleQvAyHoSuuHepUWkhACjPUO9ibqhPUgZrDqx6TaejIcJp2bENzLqv8AitNLD6s31mXNX7cNOGF5aQT2foWPaIKhPxVQrHyGmglVfZ3sG7TA1auGqf8A62L028HpsbMJ0EU4zrOK9rvafiSrTSpell0NTDMypVPI9VP9pM5Sp7S4uoMlTEV2Xozsf3nstTCpiEZHVSxFmVgCrjxHWeb+0/sW9ENWoKxpKfxF3ZD+6y5aM6TMng/G8Th3D0Kjg31UksjeDIdDO8o+0aVcnaIadSoe8ljlLj46ZOmvQ/lO6mZv/TDCYWtUaniR+OO9RVtFdR7w/MOnTyM9V4vwOjiqDYaoiimR3coAKMNmXoRB4Ep5p53/AEa1HFJiyoSHp1E3pvydCfhNrFT+oInoWDTudm2rKBYnnpOG4ZQek1TB1rf1VC7BjfLVpHaqt+WwYciL9Qer4NiS6ZSCHp6AHcgbp6Hb0ia3/wBGmaK0raweLfKM+2XpCVaugYa8wOZEsO6rqxBvoqjUa7WHWPc7B99CwtcuFYdBflvFWrIg1F2OgVRmY+QEzv65UZlB799VSxZb7BuSf5G/hC08JUqav3FO6qTcjozb+mg8Im18Al9lKvi7tkAJYfChBIPR6mqr+VczeULS4Q1Qg1QoUahFWwv1IOrHxYnyE2MPg0pgBVAtsecNe28PY/QOhh1QWUAePP6w8UgzW84hkaiBgQ3ukEEdQZw3G8KewsffwzlCeqHQH/8Aj6GdwerHb6Tm8ZUSs+IRDcPTsfzqLD9vpL478aTM+SfKGjH9kq1nqU+TrmHmpt+jfadUZxHAHtXpkc7g+RUztGfkNT0H80mn5CytI/GexgDD4KlSH4dOmgHyIqfoIVm0vfKvU7nyEjUcLqxBI5fCP9mZeIxRc2H1/m05nXf9ToSC4rG2ulMWvueZ8zKlPCi+apq3SFRQPPrJgzWOF13X+DKuVLpBAf4I8jFN/GTLyYACPaJRCAQGwRSNkhrRWjEVikiacslZArHoiuUlbDLleqvUq/oUC/qhmgVlSsuV0bk4KHzHeX9H+sjkWyVHVHMe2fE3pmnRpsy5lzuVJUkEkKLjUDun7TncFxnEUjdKjkc1qE1FPoTp6Wmv7c0rVab8mTL6qxP/AMxOZnKdB6NwLj6YnT3Ky6lL725oeY8Nx951mGrhwTYZwLOu4ZfEfy08ORypDKSGU3UjQgjmDPRPZfj/AG4sSBiKfvDYOu2YD9Ry9YAUfa32a7N/6rCVGQqO0A2COuoKkbA2sTyNuV7dz7De1C8Rw4ZrDEU7LXUad7k4Hym3objlMvEYoCqKb2FGvrTb5KmzKfAm31B5m3GcZwlfhGI/rMJ3ab92ooF1GY7EfISBboZSaawj51HpHtrwlqtNcTQ0xeEPaUj8wHvIeoIvp/uYGC4iKy08RQdE7QhWWo2W1QfABux3AA1IPgZyGN9t8TXAvUI8F7v6SpwPGl6/ZuwVazgklVKhxqHy2t1v116zX9J+Pl9Gf6i8s+z02qHSpVyVgoqEMUyNVdGygMV74FNTa/ftbWwmbwjhmPqOzVal8OCSjWCOQb6Kw7wW3QgHxG+rh8OtVKuHYCmWDIpp9zKTsy26Eetj1mt7N45qtPJVsMRSY06y/wB67keB0YeDTM2DcM4elMDKB3fdAAAHkBpNMSkaVQ1L5vwxsBpfrf1l2JiT34HkWF4DE41KejsATqPEeEBT4xRa9nUHo3d/WTuFZvRdN9thKmO4glFbuRfpzMxMf7SbpSF267zG7B6hz1WN/GLWwD8Q4vUxByrdafQbnzk+D0OzcEnVtx00O8nhqBbSmLDmZoJSSkLnVuph6AbBYFKQPZi1/edtWPgOgjYrHLTFh/yfMyhjOKFjlp6n+fSVUpc3Nz9pamuR/wCyW5hBWqNU1Oi8hCoLaCREcTpjimfRhVuggMcGQEcGaGYW8UjeKSWJZOQEe8golFGvFeMQjImOTIkxiGMBiUzKQPe0K3+YG6+lxDMYGrUVRdiAOpNo+s7DcOd9rMN2uHFRRrTIfxyHRgfLQ/4zhJ6kmVsy6NTqglehv76+u/qek844pgjh6j022U90nmh90/T7gzkpY8Oma8lpTIksLinpOtSmbOhuP3B8DtI081RxTpoz1WNgiAs1/IdOfSDSi18zEX6DWSM9KwlV+IoBTTJRXWpUqWCipkP4aDdtWF20HnNzAIMVRahiFvUpjJUDakqbgMfoQfFTPPvZDiRpVOxYns6x25Cpsrevu/TpPU8Ait+J8Y7r+IsLX+g+h6wzsnx708Y9oeDPgKxQrmpNrTY7Mvn8w2P/ADK2HxHaMqgAMdVy6kMNp7hxXhlPEo1OqoZTt1B6g8jPJsdwJ8FUZSLpujhfeF9vPkRB1U+mV4y/aO39neJdpTpVW0NO1Otc7MqjW/5RfyQdZr8TpGliKddTlXE2p1DyXEJ/2XPmLofAziPZnElHq0WtkxC3UH4aqDMD5EXv+UTtCf6jB1KZ98KCL750tv42yn1gq0TWHT8PxYqoHAsbkMp3VhoQYapWVQWYgAb32nA4PjL027Qb1FHaKdi40LeZ0PqYPiPEq1dsh7q/KOfnB6CLdTFVMU7X1CklBtlUn/6ka9FBoxJPPLtLRpCigQW7RgC/Wx5eUr4dGJ1AIJ22/SMY1EqNKa6zQoYEnvVD6SxTRaYvYL9z9ZnYzidzkp6nwiAuYnHJTFhbyExqtZ6puTZP5tIikb3fVvsIW83jh3ujK+XOkJEC6CEBkLySmdOYYboQGSEgI4MQyYjyAMkIhYTijhYotLwsFIxpyYMe8WHIuUCUg2WWCJWxmKSkL1GC9BzPkJLaXbNZsizQFbEqgu7ADx/brMfE8bepdaCG3zN/LD7zOelc3quWY8hqZhf5KX8PYq5vo0MVx2/dpKSerD9B/uZ1RHqHNVc35Dc+g5S7QwjEbBE/9x/1GbFJT0prmfmd/qf9Tmq6r2zN6+6Yl7SmgK91KeoD2JJvf03PPnLGGdMQDUenT7SiNXfMwVdSMiIc7todLrbqQSsyatRnN3OY8lHuibPC6JppmPxG7DoNgf518JXH5U8XwjXhp+WJ9HJ8T4/nzphwyJUAV3bKtSogvZCEAWmmp7iADXW95hzp/angmQnEUh+G2tRR8LH4h/afsftzM0O8YG22/K3Wevey/EO1SnU51Fs/5xofuD9Z5FO7/wCntclKtP5HVh5OLfqn3gB6KRKeLw4OjAEHUXF5dQ3HnHxCXTyjA57HcLSplZQFqU2D02AtqpvY9QdvWCwlbLUqJyzA+YY5Gb6sB/jNaYlHDO+JYLsVdSeQyuji5/zkvpjKmGwRapVpsfdzFPMH/U6zCcMU5HYAdxSx8hMLCsXxFaog/DuVVvmJtfL1tr6zWxGIrspROzytofe93bKu1vvLSbJ1FGkhqvUqW0qN3fyLoP54SxVqpRGti/QcvPpAsuJbujs0XztYeAUH9RJ4fhaqc1Ql38RZQfBf9kx+ItKX4uJ1Hdp/MdLj+0c5ZTCLTFlGvMnczTMBUEqVnoTX2ZTpIFJedIApOiWZuQGSOEhwkkEjbF4gcsVobJHFOT5D8QKpCosmEhFpyHQKSMUMKUUnyL8Sga7U2AbJ2Z2YnKwPIEbEeI+nOTxvEEogGoSM2wAuTC1uGCsjU6ig03BDBuYPT/ek5L2own9GaTVq1SphScrg2z07qMrXItY2APj5xu/FYc9fjzVb6+y9i+MVql1pKET5yQxPlMt6aA5qjM7nrrrMPA8cpvWWij3pajtLGncaFSUY3DakGxI5jSdV+HRF/i+rf8Tivyp9syvi8X/T7YOnh3ffuJ05/TlJPWp0dF1f6n1PKVMTxB30Xur9z6yqi9N+sz1L0ZeSX8Pb+w2IxT1PeNl+UfvIJT0voqDcnQQi0rFQQzVG9xEF2b05Dxm/w/2bLWqYq2mq0kPdX8x5n+eEal0y546tmJgcI9fSkCqbNUbS/UL/AD6Tq6OEyqq3JygC53NpppQAAAAAGgA0AHgJIU51caUejrjhUmNVw+QbXpnTXUC/I+H/ANTjeOezDKTUwwum7INSv5Oo8N/OelmlfQjQ73mbicIafeFzT+pXz8PH69YV9myfweOkWuDoRuDoQfGd3/06wrBa1Qg5XKKvjkzFiP8AyA+s26uApVDepTpuerorH6kTVwlMKAqgBRoAosAOgA2klGnh9hCVTZWEhSFrSWJ2jAo2lGgWXtQbfiOx0+TRQPoohcdi8lqaa1X9xen9zdAN5FaJUAXJIGpPM9ZUzr1k08QlNtBsOkMjwGUySma5pCZaUyV5XVpLNDxL0IzQTGKIylItBuIErDNITRIlkAskFkgJJViYhgsIqRwsKizKgTGVJIJJgR7TMpMjlikooFaFsen3mF7Y0VqYarZO0emhOQG1156+G8LVx71NF0HhuR5y3hMPprzHPUeXlLqVM9nHx81XeJdfZ82MezcMNNbrfkQdQw6jYz0DAYlaiKy32GhvzHjv0v4TO9sPZdsPi3DD/wBNVOdHI2LHvLpzH3uDNrhWCUOadIVGXvZBa7WvcDKNOZnNc6jbm4/KSxSoFpqcL4W9f/sgCn8VZxdR4IPjPjtNvhvs4DZsRa3Kkpuv+bD3z/aNPOdMigAAAADQAaADwEzmPsz4+D5Zm8M4PTw4OQE1G9937zt5nkPAaS4Uli0YrNF0dalJYgASPkhgsWWPQBZI4SFtHCx6GGXX4WPepkKflPuny+X9PCRpVOz0qKy2+IglP/IaCa+WLLDQM1uJUV17Sn/5qP3g3xT1dKSnX43Uqg8QDYv6aeImpkG9heMVj1AZmE4ctPMRdqj61HfVmP7DwGkOactFZErK0WFJqcGyS66QLrLlhhVy2j3k3EAz2m8rSW8J3iLQBrCQatNFDIdIOxkLyu1aJXleBPl2W1hVldGhlMypA6DqJNRBqZMGZUheROKQzR80jBqiV4oMtHiwryKGDUAD+XmhRbWcP7M8WY0VRyC1IBCebLyP7Tq8JiAdje+3rItuuzTjiYWIpe3mB7bDbEmmc9hzyEXH0vNDhtKmQlZEQPURTnAAJBA5y/i0DLY7EEehtMX2fJSn2Zv+CzIL/Kp7p+lob+1Da7N5WhFaVFeEV5DKRZBjwSvJhohkxHEiDHBgIlaPaNeK8AHtFaK8cSgGtGIk4oADKyJWFjEQArusC6yy4gXEuWJlOosoYgTRqCUMROvjZnZQYyN4zmMDOxHK/ZMQ9FJCnTlpBM6ouZJKIVYwjic9UW5JhoRXgI95kzNphmeQNSCYxrxCW6ENSKBvFEXjPPvZ33n8l/UztuFe/wCqxRSP+p1fJ0NTaZlP3qnmP0jRTP4BlkQiRRRDDLCiKKIYRZIRRQAeKKKAh44iijAePFFGA0RiigANoBooo5EVakzsRFFOviIv0ZjxU4op2/By/JeSEWKKc9myCiSiimDGNEYopLM2RMaKKAkNFFFEaH//2Q==");
        detailsOfVaccine.add("Brand Name:AstraZeneca\nVaccine Name:ChAdOx1-S\nType of Vaccine:Viral Vector\nNumber of Shots:2, 21 Days Apart\nHow Given:Shot in the arm muscle\nTemperature:2°C to 8°C \n\n");

        initRecycleView();
    }

    private void initRecycleView(){
        Log.d(TAG, "initRecycleView: init recyclerview.");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycleViewForVaccines);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, nameOfVaccine, imageOfVaccine, detailsOfVaccine);
        recyclerView.setAdapter(adapter);
    }
}
