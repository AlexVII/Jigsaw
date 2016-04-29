package com.smu_bme.jigsaw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    private TextView cardName;
    private TextView cardTime;
    private TextView cardDuration;
    private TextView cardRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        cardName = (TextView) findViewById(R.id.card_name);
        cardTime = (TextView) findViewById(R.id.card_time);
        cardDuration = (TextView) findViewById(R.id.card_duration);
        cardRemark = (TextView) findViewById(R.id.card_remark);

        Intent intent = getIntent();

        cardName.setText("233");
        cardTime.setText("233");
        cardDuration.setText("233");
        cardRemark.setText("233");

    }
}
