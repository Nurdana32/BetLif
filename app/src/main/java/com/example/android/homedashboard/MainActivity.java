package com.example.android.homedashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView testCard,historyCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //defining card
        testCard = (CardView) findViewById(R.id.test_card);
        historyCard = (CardView) findViewById(R.id.history_card);
        //Add click listener to the cards
        testCard.setOnClickListener(this);
        historyCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         Intent i;

         switch (v.getId()) {
             case R.id.test_card : i = new Intent(this,Test.class);startActivity(i);break;
             case R.id.history_card : i = new Intent(this,History.class);startActivity(i);break;
             default:break;
         }
    }
}
