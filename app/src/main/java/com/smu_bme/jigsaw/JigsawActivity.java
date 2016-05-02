package com.smu_bme.jigsaw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class JigsawActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    int p = R.mipmap.p0;
    private View ContentView;
    private ImageView Jigsaw;
    private DbHelper dbHelper;
    private Button button1;
    private Button button2;
    private RelativeLayout Background;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jisaw);
        ContentView = findViewById(R.id.jigsaw_background);
        Background = (RelativeLayout) findViewById(R.id.jigsaw_background);
        Jigsaw = (ImageView) findViewById(R.id.jigsaw);
        fab = (FloatingActionButton) findViewById(R.id.jigsaw_fab);
        fullScreen();
        setJigsaw();
        ContentView.setOnClickListener(this);
        Background.setBackgroundResource(R.mipmap.inori);
        button1 = (Button) findViewById(R.id.set_plan);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.main_page);
        button2.setOnClickListener(this);
        fab.setOnTouchListener(this);
//        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(JigsawActivity.this, MainActivity.class);
        if (v.getId() == R.id.set_plan) {
            intent.putExtra("Action", "button");
        } else if (v.getId() == R.id.main_page) {
            intent.putExtra("Action", "nothing");
//        } else if (v.getId() == R.id.jigsaw_background){
//            Toast.makeText(JigsawActivity.this, "You Had Collected " + dbHelper.queryProgress() + " / 44 Jigsaws !!", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateProgress();
            setJigsaw();
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Jigsaw.setAlpha(0.5f);
            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            fab.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
        } else if (event.getAction() == MotionEvent.ACTION_UP){
            Jigsaw.setAlpha(1f);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        fullScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    public void fullScreen(){
        ContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void setJigsaw(){
        dbHelper = new DbHelper(JigsawActivity.this);
        int i = dbHelper.queryProgress();
        if (i == 44){
            Jigsaw.setVisibility(View.GONE);
        } else if (i == 0) {
            Jigsaw.setVisibility(View.VISIBLE);
            Jigsaw.setImageResource(p + i);
        } else {
            Jigsaw.setImageResource(p + i);
        }
    }
}
