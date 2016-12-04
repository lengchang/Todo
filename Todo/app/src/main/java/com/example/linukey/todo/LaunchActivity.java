package com.example.linukey.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by linukey on 12/2/16.
 */

public class LaunchActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟六秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
