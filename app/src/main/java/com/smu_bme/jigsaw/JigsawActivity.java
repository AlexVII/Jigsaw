package com.smu_bme.jigsaw;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class JigsawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisaw);
        Button button = (Button) findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JigsawActivity.this, MainActivity.class);
                intent.putExtra("Get Started", 2);
                startActivity(intent);
            }
        });
    }
}
