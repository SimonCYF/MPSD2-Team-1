package com.example.mpsd2.chat;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mpsd2.R;
import com.example.mpsd2.blockchain.Block;
import com.example.mpsd2.userpackage.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity {

    TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;
    ImageButton enter_btn;
    EditText send_msg;
    Intent intent;
    MessageAdapter messageAdapter;
    List<Message> mMessage;
    RecyclerView recyclerView;

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 2;
    public static int count = 0;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        username = findViewById(R.id.username);
        enter_btn = findViewById(R.id.enter_btn);
        send_msg = findViewById(R.id.send_msg);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = send_msg.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fuser.getUid(), userid, msg);
                    myEdit.putString("msg", msg);
                    myEdit.commit();
                    Content content = new Content();
                    content.execute();
                } else {
                    Toast.makeText(Chat.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                send_msg.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getUsername());

                readMessages(fuser.getUid(), userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage (String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages (String myid, String userid) {
        mMessage = new ArrayList<>();
        addNotification();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMessage.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    assert message != null;
                    if (message.getReceiver().equals(myid) && message.getSender().equals(userid) ||
                            message.getReceiver().equals(userid) && message.getSender().equals(myid)) {
                        mMessage.add(message);
                    }

                    messageAdapter = new MessageAdapter(Chat.this, mMessage);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_baseline_email_24)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, Chat.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private class Content extends AsyncTask<Void,Void,Void> {

        @SuppressLint("WrongConstant")
        SharedPreferences sp = getSharedPreferences("SharedPref", MODE_PRIVATE);

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        String msg = sp.getString("msg","");


        @Override
        protected Void doInBackground(Void... voids) {
            if (count ==0){
                blockchain.add(new Block(msg, "0"));
                blockchain.get(count).mineBlock(difficulty);
                count+=1;
            }else {
                blockchain.add(new Block(msg, blockchain.get(blockchain.size() - 1).hash));
                blockchain.get(count).mineBlock(difficulty);
                count+=1;
            }

            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(Chat.this,"Stored Into BlockChain",Toast.LENGTH_SHORT).show();

            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
            Log.d("Blockchain MPSD2",blockchainJson);

            String removed = blockchainJson.replace("{","");
            String removedBack = removed.replace("}","");
            String removedBackN = removedBack.replace("\n","");
            String removedBackNN = removedBackN.replace("\\","");
            String removedBackNNEnd = removedBackNN.replace("\"","");
            String removedBackNNEndLast = removedBackNN.replace("]","NEXT BLOCKCHAIN");

            myEdit.putString("block", String.valueOf(removedBackNNEnd));
            myEdit.commit();

            return;
        }
    }
}
