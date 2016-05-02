package com.smu_bme.jigsaw;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardActivity extends AppCompatActivity {

    private EditText cardName;
    private TextView cardTime;
    private TextView cardDuration;
    private EditText cardRemark;
    private Button cardDelete;
    private Button cardEdit;
    private DbHelper dbHelper;
    private ImageView imageView;
    private CardView cardView;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        cardName = (EditText) findViewById(R.id.card_name);
        cardTime = (TextView) findViewById(R.id.card_time);
        cardDuration = (TextView) findViewById(R.id.card_duration);
        cardRemark = (EditText) findViewById(R.id.card_remark);
        cardRemark.setEnabled(false);
        cardDelete = (Button) findViewById(R.id.button_delete);
        cardEdit = (Button) findViewById(R.id.button_edit);
        dbHelper = new DbHelper(CardActivity.this);
        imageView = (ImageView) findViewById(R.id.card_header);
//        imageView.setImageResource(R.mipmap.bull_red);
        cardView = (CardView) findViewById(R.id.card_view);

//        Intent intent = getIntent();

        final DbData item = new DbData("2333-33-33", "33:33", 233, "233"); // (DbData) intent.getSerializableExtra("Event");
        Log.d("DEBUGGING", "Get Name = " + item.getName());
        cardName.setText(item.getName());
        cardTime.setText(item.getTime());
        cardDuration.setText(String.valueOf(item.getDuration()));
        cardRemark.setText(item.getRemark());
//        DELETE AND CANCEL
        cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardDelete.getText().equals(getString(R.string.delete))){
                    new AlertDialog.Builder(CardActivity.this)
                            .setTitle(getString(R.string.delete_title))
                            .setPositiveButton(getString(R.string.yes),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dbHelper.deleteData(item.getId());
                                            Intent intent = new  Intent (CardActivity.this, MainActivity.class);
                                            intent.putExtra("Action", "nothing");
                                            startActivity(intent);
                                            finish();
//                                            TODO refresh layout
                                        }
                                    }).setNegativeButton(getString(R.string.cancel), null).show();
                } else {
                    cardName.setEnabled(false);
                    cardRemark.setEnabled(false);
                    cardName.setText(item.getName());
                    cardRemark.setText(item.getRemark());
                    cardEdit.setText(getString(R.string.edit));
                    cardDelete.setText(getString(R.string.delete));
//                    TODO refresh layout
                }
            }
        });
//        EDIT AND ACCEPT
        cardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardEdit.getText().equals(getString(R.string.edit))) {
                    cardEdit.setText(getString(R.string.accept));
                    cardDelete.setText(R.string.cancel);
                    cardName.setEnabled(true);
                    cardRemark.setEnabled(true);
                } else {
                    String nameInput = cardName.getText().toString();
                    String remarkInput = cardRemark.getText().toString();
                    if (nameInput.equals("")) {
                        Toast.makeText(CardActivity.this, getString(R.string.noName), Toast.LENGTH_SHORT).show();
                    } else {
                        cardEdit.setText(getString(R.string.edit));
                        item.setName(nameInput);
                        item.setRemark(remarkInput);
                        dbHelper.updateData(item);
                        cardName.setEnabled(false);
                        cardRemark.setEnabled(false);
                        Toast.makeText(CardActivity.this, getString(R.string.successful_change_1) + item.getName() + getString(R.string.successful_change_2), Toast.LENGTH_SHORT).show();
                        cardDelete.setText(R.string.delete);
//                        TODO refresh layout
                    }
                }
            }
        });
    }
}
