package com.smu_bme.jigsaw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    private EditText cardName;
    private TextView cardTime;
    private TextView cardDuration;
    private EditText cardRemark;
    private Button cardDelete;
    private Button cardEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        cardName = (EditText) findViewById(R.id.card_name);
        cardName.setEnabled(false);
        cardTime = (TextView) findViewById(R.id.card_time);
        cardDuration = (TextView) findViewById(R.id.card_duration);
        cardRemark = (EditText) findViewById(R.id.card_remark);
        cardRemark.setEnabled(false);
        cardDelete = (Button) findViewById(R.id.button_delete);
        cardEdit = (Button) findViewById(R.id.button_edit);

        Intent intent = getIntent();

        DbData item = (DbData) intent.getSerializableExtra("Event");
        cardName.setText(item.getName());
        cardTime.setText(item.getTime());
        cardDuration.setText(String.valueOf(item.getDuration()));
        cardRemark.setText(item.getRemark());
        cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
