package com.bgy.application;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class TestActivity extends AppCompatActivity {

    TextView sample_main;
    TextView sample_text_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        sample_main = findViewById(R.id.sample_main);
        sample_text_one = findViewById(R.id.sample_text_one);

        sample_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        sample_text_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TestOneActivity.class);
                startActivity(intent);
            }
        });
    }

}