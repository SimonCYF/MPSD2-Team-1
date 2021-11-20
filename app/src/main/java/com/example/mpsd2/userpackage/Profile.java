package com.example.mpsd2.userpackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mpsd2.MainActivity;
import com.example.mpsd2.R;
import com.example.mpsd2.map.MapsFragment;
import com.example.mpsd2.scraper.Scraper;
import com.example.mpsd2.swipe.SwipeMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    private TabLayout tabLayout;
    private Intent intent;
    private String usernameVerification, userId , emailVerification, passwordVerification;

    private FirebaseAuth mAuth;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextInputEditText popUpUsername, popUpPassword, popUpConfirmPassword, popUpPasswordDelete, popUpOldPassword;
    private Button popUpCancel, popUpConfirm;

    private Button btnUpdatePassword, btnUpdateUsername , btnDeleteAccount;

    private final int[] ICONS = new int[]{
            R.drawable.ic_baseline_text_snippet_24,
            R.drawable.ic_baseline_fiber_new_24,
            R.drawable.ic_baseline_location_on_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_follow_the_signs_24};

    private ImageView image;
    private TextView username, email, phone, usernameDisplay;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate((savedInstancesState));
        setContentView(R.layout.activity_view_profile);

        image = findViewById(R.id.imageView);
        username = findViewById(R.id.username);
        email = findViewById(R.id.emailAdd);
        phone = findViewById(R.id.phoneNum);
        btnUpdatePassword = (Button) findViewById(R.id.updatePassword);
        btnUpdateUsername = (Button) findViewById(R.id.updateUsername);
        btnDeleteAccount = (Button) findViewById(R.id.deleteAccount);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getUsername());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                emailVerification = user.getEmail();
                usernameVerification = user.getUsername();
                userId = user.getId();
                passwordVerification = user.getPassword();
                Log.d("Pass","Pass"+passwordVerification);
                if (user.getImage().equals("default")) {
                    image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(Profile.this).load(user.getImage()).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tabLayout  = findViewById(R.id.tabLayout);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
        tabLayout.getTabAt(4).setIcon(ICONS[4]);
        tabLayout.getTabAt(5).setIcon(ICONS[5]);

        btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewContactDialog();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteuser();
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

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
                intent = new Intent(Profile.this,SwipeMain.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(Profile.this, Scraper.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(Profile.this, MapsFragment.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(Profile.this, Fingerprint.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(Profile.this, Profile.class);
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
                Toast.makeText(Profile.this,"Logged Out",Toast.LENGTH_SHORT).show();
                //mAuth.signOut();
                intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
            }
        });
        alert.create().show();

    }

    private void deleteuser() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopUp = getLayoutInflater().inflate(R.layout.activity_popup_confirm_delete, null);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        popUpPasswordDelete = (TextInputEditText) contactPopUp.findViewById(R.id.passwordToDelete);
        popUpCancel = (Button) contactPopUp.findViewById(R.id.btnCancelDelete);
        popUpConfirm = (Button) contactPopUp.findViewById(R.id.btnConfirmDelete);

        dialogBuilder.setView(contactPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        popUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        popUpConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password_txt = Objects.requireNonNull(popUpPasswordDelete.getText()).toString().trim();
                AuthCredential credential = EmailAuthProvider.getCredential(emailVerification,password_txt);

                if(password_txt.isEmpty()){
                    popUpPasswordDelete.setError("Username Is Required");
                    popUpPasswordDelete.requestFocus();
                }else if (!passwordVerification.equals(password_txt)){
                    Toast.makeText(Profile.this, "Password Do Not Match!", Toast.LENGTH_SHORT).show();
                }else{
                    if (user != null) {
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        user.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("TAG", "User account deleted.");
                                                            startActivity(new Intent(Profile.this, MainActivity.class));
                                                            Toast.makeText(Profile.this, "Deleted User Successfully,", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                }
            }
        });
    }

    public void updatePassword(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopUp = getLayoutInflater().inflate(R.layout.activity_popup_change_password, null);

        popUpOldPassword = (TextInputEditText) contactPopUp.findViewById(R.id.oldPassword);
        popUpPassword = (TextInputEditText) contactPopUp.findViewById(R.id.newPassword);
        popUpConfirmPassword = (TextInputEditText) contactPopUp.findViewById(R.id.newConfirmPassword);

        popUpCancel = (Button) contactPopUp.findViewById(R.id.btnCancelPassword);
        popUpConfirm = (Button) contactPopUp.findViewById(R.id.btnConfirmPassword);

        dialogBuilder.setView(contactPopUp);
        dialog = dialogBuilder.create();
        dialog.show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        popUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popUpConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordOld_txt = Objects.requireNonNull(popUpOldPassword.getText()).toString().trim();
                String password_txt = Objects.requireNonNull(popUpPassword.getText()).toString().trim();
                String passwordConfirm_txt = Objects.requireNonNull(popUpConfirmPassword.getText()).toString().trim();
                AuthCredential credential = EmailAuthProvider.getCredential(emailVerification,password_txt);
                if(passwordOld_txt.isEmpty()){
                    popUpOldPassword.setError("Old Password Is Required");
                    popUpOldPassword.requestFocus();
                }else if (!passwordOld_txt.equals(passwordVerification)){
                    Toast.makeText(getApplicationContext(),"Old Password Is Incorrect!", Toast.LENGTH_SHORT).show();
                }else if(password_txt.isEmpty()){
                    popUpPassword.setError("Password Is Required");
                    popUpPassword.requestFocus();
                }else if(passwordConfirm_txt.isEmpty()){
                    popUpConfirmPassword.setError("Confirm Password Is Required");
                    popUpConfirmPassword.requestFocus();
                }else  if (!password_txt.equals(passwordConfirm_txt)){
                    Toast.makeText(getApplicationContext(),"Password Do Not Match!", Toast.LENGTH_SHORT).show();
                }else{
                    reference.child("password").setValue(password_txt);
                    if (user != null) {
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        user.updatePassword(password_txt)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            startActivity(new Intent(Profile.this, Profile.class));
                                                            Toast.makeText(Profile.this, "Password Update Successfully,", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                    intent = new Intent(Profile.this, Profile.class);
                    startActivity(intent);
                }

            }
        });

    }


    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopUp = getLayoutInflater().inflate(R.layout.activity_popup_change_username, null);
        usernameDisplay = (TextView) contactPopUp.findViewById(R.id.currentUsername);
        usernameDisplay.setText(usernameVerification);
        popUpUsername = (TextInputEditText) contactPopUp.findViewById(R.id.newUsername);

        popUpCancel = (Button) contactPopUp.findViewById(R.id.btnCancel);
        popUpConfirm = (Button) contactPopUp.findViewById(R.id.btnConfirm);

        dialogBuilder.setView(contactPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        popUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popUpConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_txt = Objects.requireNonNull(popUpUsername.getText()).toString().trim();
                if(username_txt.isEmpty()){
                    popUpUsername.setError("Username Is Required");
                    popUpUsername.requestFocus();
                }else if (usernameVerification.equals(username_txt)){
                    Toast.makeText(getApplicationContext(),"Same Username As Previous!", Toast.LENGTH_SHORT).show();
                }else{
                    reference.child("username").setValue(username_txt);
                    intent = new Intent(Profile.this, Profile.class);
                    startActivity(intent);
                }

            }
        });

    }

}
