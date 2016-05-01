package com.smu_bme.jigsaw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class JigsawActivity extends AppCompatActivity implements View.OnClickListener {

    private View ContentView;
    private ImageView Background;
    private ImageView Jigsaw;
    private DbHelper dbHelper;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisaw);
        dbHelper = new DbHelper(JigsawActivity.this);
        ContentView = findViewById(R.id.jigsaw_content);
        Background = (ImageView) findViewById(R.id.background);
        Jigsaw = (ImageView) findViewById(R.id.jigsaw);
        ContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        ContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JigsawActivity.this, "You Had Collected " + dbHelper.queryProgress() + " / 44 Jigsaws !!", Toast.LENGTH_SHORT).show();
            }
        });
        switch (dbHelper.queryProgress()) {
            case 0:
                Jigsaw.setImageResource(R.mipmap.p0);
            case 1:
                Jigsaw.setImageResource(R.mipmap.p_1);
            case 2:
                Jigsaw.setImageResource(R.mipmap.p_2);
            case 3:
                Jigsaw.setImageResource(R.mipmap.p_3);
            case 4:
                Jigsaw.setImageResource(R.mipmap.p_4);
            case 5:
                Jigsaw.setImageResource(R.mipmap.p_5);
            case 6:
                Jigsaw.setImageResource(R.mipmap.p_6);
            case 7:
                Jigsaw.setImageResource(R.mipmap.p_7);
            case 8:
                Jigsaw.setImageResource(R.mipmap.p_8);
            case 9:
                Jigsaw.setImageResource(R.mipmap.p_9);
            case 10:
                Jigsaw.setImageResource(R.mipmap.p_10);
            case 11:
                Jigsaw.setImageResource(R.mipmap.p_11);
            case 12:
                Jigsaw.setImageResource(R.mipmap.p_12);
            case 13:
                Jigsaw.setImageResource(R.mipmap.p_13);
            case 14:
                Jigsaw.setImageResource(R.mipmap.p_14);
            case 15:
                Jigsaw.setImageResource(R.mipmap.p_15);
            case 16:
                Jigsaw.setImageResource(R.mipmap.p_16);
            case 17:
                Jigsaw.setImageResource(R.mipmap.p_17);
            case 18:
                Jigsaw.setImageResource(R.mipmap.p_18);
            case 19:
                Jigsaw.setImageResource(R.mipmap.p_19);
            case 20:
                Jigsaw.setImageResource(R.mipmap.p_20);
            case 21:
                Jigsaw.setImageResource(R.mipmap.p_21);
            case 22:
                Jigsaw.setImageResource(R.mipmap.p_22);
            case 23:
                Jigsaw.setImageResource(R.mipmap.p_23);
            case 24:
                Jigsaw.setImageResource(R.mipmap.p_24);
            case 25:
                Jigsaw.setImageResource(R.mipmap.p_25);
            case 26:
                Jigsaw.setImageResource(R.mipmap.p_26);
            case 27:
                Jigsaw.setImageResource(R.mipmap.p_27);
            case 28:
                Jigsaw.setImageResource(R.mipmap.p_28);
            case 29:
                Jigsaw.setImageResource(R.mipmap.p_29);
            case 30:
                Jigsaw.setImageResource(R.mipmap.p_30);
            case 31:
                Jigsaw.setImageResource(R.mipmap.p_31);
            case 32:
                Jigsaw.setImageResource(R.mipmap.p_32);
            case 33:
                Jigsaw.setImageResource(R.mipmap.p_33);
            case 34:
                Jigsaw.setImageResource(R.mipmap.p_34);
            case 35:
                Jigsaw.setImageResource(R.mipmap.p_35);
            case 36:
                Jigsaw.setImageResource(R.mipmap.p_36);
            case 37:
                Jigsaw.setImageResource(R.mipmap.p_37);
            case 38:
                Jigsaw.setImageResource(R.mipmap.p_38);
            case 39:
                Jigsaw.setImageResource(R.mipmap.p_39);
            case 40:
                Jigsaw.setImageResource(R.mipmap.p_40);
            case 41:
                Jigsaw.setImageResource(R.mipmap.p_41);
            case 42:
                Jigsaw.setImageResource(R.mipmap.p_42);
            case 43:
                Jigsaw.setImageResource(R.mipmap.p_43);
            case 44:
                Jigsaw.setImageResource(R.mipmap.p_44);
            case 45:
                Jigsaw.setVisibility(View.GONE);
        }
        button1 = (Button) findViewById(R.id.set_plan);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.main_page);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(JigsawActivity.this, MainActivity.class);
        if (v.getId() == R.id.set_plan) {
            intent.putExtra("PressButton", "true");
        } else {
            intent.putExtra("PressButton", "False");
        }
        startActivity(intent);
        finish();
    }
}
