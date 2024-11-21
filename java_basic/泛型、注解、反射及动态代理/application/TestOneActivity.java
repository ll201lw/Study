package com.bgy.application;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class TestOneActivity extends AppCompatActivity {

    TextView sample_main;
    TextView sample_text;
    TextView sample_text_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_one);

        sample_main = findViewById(R.id.sample_main);
        sample_text = findViewById(R.id.sample_text);
        sample_text_two = findViewById(R.id.sample_text_two);

        sample_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        sample_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
            }
        });

        sample_text_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TestTwoActivity.class);
                startActivity(intent);
            }
        });

    }

}