package com.example.linukey.launch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.linukey.login.LoginActivity;
import com.example.linukey.todo.MainActivity;
import com.example.linukey.todo.R;

/**
 * Created by linukey on 12/2/16.
 */

public class LaunchActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟六秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        initLaunchPic();
    }

    public void initLaunchPic(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
