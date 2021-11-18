package com.example.mpsd2.blockchain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mpsd2.R;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class BlockActivity extends AppCompatActivity {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.activity_second);



        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    blockchain.add(new Block("Lim Guan Eng", "0"));
                    blockchain.get(count).mineBlock(difficulty);
                    System.out.println("LGE");
                    count += 1;
                }else {
                    blockchain.add(new Block("Lim Guan Eng", blockchain.get(blockchain.size() - 1).hash));
                    blockchain.get(count).mineBlock(difficulty);
                    System.out.println("LGE");
                    count += 1;
                }

                display();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    blockchain.add(new Block("NAJIB", "0"));
                    blockchain.get(count).mineBlock(difficulty);
                    System.out.println("LGE");
                    count += 1;
                }else {
                    blockchain.add(new Block("NAJIB", blockchain.get(blockchain.size() - 1).hash));
                    blockchain.get(count).mineBlock(difficulty);
                    System.out.println("LGE");
                    count += 1;
                }

                display();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    blockchain.add(new Block("LKS", "0"));
                    blockchain.get(count).mineBlock(difficulty);
                    System.out.println("LGE");
                    count += 1;
                }else {
                    blockchain.add(new Block("LKS", blockchain.get(blockchain.size() - 1).hash));
                    blockchain.get(count).mineBlock(difficulty);
                    System.out.println("LGE");
                    count += 1;
                }

                display();
            }
        });


         */


        blockchain.add(new Block("Lim Guan Eng", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Najib",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Anwar ",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        blockchain.add(new Block("Mahathir",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 4... ");
        blockchain.get(3).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());





    }
/*
    public void display(){
        TextView textView = (TextView) findViewById(R.id.textView);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);

        textView.setText(blockchainJson);
    }*/



    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        //TextView textView2 = (TextView) findViewById(R.id.textView2);



        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                Toast.makeText(getApplicationContext(),"Current Hash Not Equal", Toast.LENGTH_SHORT).show();
                //textView2.setText("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                //textView2.setText("Previous Hashes not equal");
                Toast.makeText(getApplicationContext(),"Previous Hash Not Equal", Toast.LENGTH_SHORT).show();
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                //textView2.setText("This block hasn't been mined");
                Toast.makeText(getApplicationContext(),"Block Not Mined", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


}
