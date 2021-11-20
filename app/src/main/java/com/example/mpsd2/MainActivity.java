package com.example.mpsd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mpsd2.blockchain.Block;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.swipe.SwipeMain;
import com.example.mpsd2.userpackage.Animation;
import com.example.mpsd2.userpackage.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.GsonBuilder;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /*
* ***************************************************************
*  This software and related documentation are done by CHEAH YEE FEI, SEOW CHEE PING, COLIN under
*  license agreement containing restrictions on use and
*  disclosure and are protected by intellectual property
*  laws. Except as expressly permitted in your license agreement
*  or allowed by law, you may not use, copy, reproduce,
*  translate, broadcast, modify, license, transmit, distribute,
*  exhibit, perform, publish or display any part, in any form or
*  by any means. Reverse engineering, disassembly, or
*  decompilation of this software, unless required by law for
*  interoperability, is prohibited.
*  The information contained herein is subject to change without
*  notice and is not warranted to be error-free. If you find any
*  errors, please report them to us in writing.

* ***************************************************************
     */

    TextInputEditText email, password;

    Button login_btn, account_btn;

    FirebaseAuth auth;
    Boolean loginButtonClicked = false;

    //blockchain
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        account_btn = findViewById(R.id.account_btn);


        login_btn.setOnClickListener(view -> {
            loginButtonClicked = true;
            login();
        });

        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();


    }

    public void login() {
        Bundle bundle = new Bundle();

        String email_txt = Objects.requireNonNull(email.getText()).toString();
        String password_txt = Objects.requireNonNull(password.getText()).toString();

        if (email_txt.isEmpty()) {
            email.setError("Email Is Required");
            email.requestFocus();
        } else if (password_txt.isEmpty()) {
            password.setError("Password Is Required");
            password.requestFocus();
        } else if (loginButtonClicked) {
            auth.signInWithEmailAndPassword(email_txt, password_txt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();

                                bundle.putString("email",email_txt);
                                Intent intent = new Intent(MainActivity.this, Animation.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                                email.getText().clear();
                                password.getText().clear();

                            } else {
                                Toast.makeText(MainActivity.this, "Login failed. [ " + Objects.requireNonNull(task.getException()).getMessage() + " ]", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        }
    }




}