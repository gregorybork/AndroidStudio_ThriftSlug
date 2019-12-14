package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buyingButton = findViewById(R.id.BuyB);
        buyingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator.ofFloat(v, "rotation", 0, 360).start();
                mHandler.postDelayed(mLaunchTaskBuy, 500);

            }
        });



        Button sellingButton = findViewById(R.id.SellB);
        sellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator.ofFloat(v, "rotation", 0, -360).start();
                mHandler.postDelayed(mLaunchTaskSell, 500);

            }
        });

        Button userProfile = findViewById(R.id.myProfile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it3 = new Intent(MainActivity.this, UserProfile.class);
                startActivity(it3);
            }
        });

        Button myItemsButton = findViewById(R.id.myItems);
        myItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, MyItems.class);
                startActivity(it);
            }
        });


    }
    private Runnable mLaunchTaskBuy = new Runnable() {
        public void run() {
            Intent it = new Intent(MainActivity.this, Buying.class);
            startActivity(it);
        }
    };

    private Runnable mLaunchTaskSell = new Runnable() {
        public void run() {
            Intent it2 = new Intent(MainActivity.this, Selling.class);
            startActivity(it2);
        }
    };


}