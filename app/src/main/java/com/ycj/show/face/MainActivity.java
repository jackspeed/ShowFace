package com.ycj.show.face;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button tvShow = (Button) findViewById(R.id.tv_start_show);
        Button tvShow1 = (Button) findViewById(R.id.tv_start_show1);
        tvShow.setOnClickListener(this);
        tvShow1.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_show:
                startActivity(new Intent(this, ShowActivity.class));
                break;
            case R.id.tv_start_show1:
                startActivity(new Intent(this, ShowActivity.class));
                break;
        }
    }
}
