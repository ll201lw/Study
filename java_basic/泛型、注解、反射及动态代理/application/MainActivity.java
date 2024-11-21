package com.bgy.application;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bgy.anotation.BindView;
import com.bgy.anotation.LayoutResource;
import com.bgy.utils.HookHelper;


@LayoutResource(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.sample_text_one)
    TextView sample_text_one;
    @BindView(R.id.sample_text_two)
    TextView sample_text_two;
    @BindView(R.id.sample_text_three)
    TextView sample_text_three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sample_text_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HookHelper.hookAmsAidl();
                Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
            }
        });

        sample_text_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestOneActivity.class);
                startActivity(intent);
            }
        });

        sample_text_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TestTwoActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}