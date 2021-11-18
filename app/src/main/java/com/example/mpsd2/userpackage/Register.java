package com.example.mpsd2.userpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    TextInputEditText username, email, password, confirm, phone;

    Button register_btn, back_btn;
    Boolean registerButtonClicked = false;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        register_btn = findViewById(R.id.reg_btn);
        back_btn = findViewById(R.id.back_btn);

        auth = FirebaseAuth.getInstance();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
        register_btn.setOnClickListener(view -> {
            registerButtonClicked = true;
            register();
        });
    }

    private void register() {
        String username_txt = Objects.requireNonNull(username.getText()).toString().trim();
        String email_txt = Objects.requireNonNull(email.getText()).toString().trim();
        String phone_txt = Objects.requireNonNull(phone.getText()).toString().trim();
        String password_txt = Objects.requireNonNull(password.getText()).toString().trim();
        String confirm_txt = Objects.requireNonNull(confirm.getText()).toString().trim();

        if(username_txt.isEmpty()){
            username.setError("Username Is Required");
            username.requestFocus();
        }
        else if(email_txt.isEmpty()){
            email.setError("Email Is Required");
            email.requestFocus();
        }
        else if(phone_txt.isEmpty()){
            phone.setError("Phone Number Is Required");
            phone.requestFocus();
        }
        else if(password_txt.isEmpty()){
            password.setError("Password Is Required");
            password.requestFocus();
        }
        else if(confirm_txt.isEmpty()){
            confirm.setError("Confirm Password Is Required");
            confirm.requestFocus();
        }
        else if(!confirm_txt.equals(password_txt)){
            confirm.setError("Confirm Password Not Equal Password");
            confirm.requestFocus();
        }
        else if(registerButtonClicked) {
            auth.createUserWithEmailAndPassword(email_txt, confirm_txt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid;
                            userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username_txt);
                            hashMap.put("email", email_txt);
                            hashMap.put("phone", phone_txt);
                            hashMap.put("password", confirm_txt);
                            hashMap.put("image", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Successfully Register", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Register.this, "Failed To Register", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
        }
    }
}

