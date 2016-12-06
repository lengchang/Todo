package com.example.linukey.todo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.linukey.BLL.LoginBLL;
import com.example.linukey.BLL.TodoHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.NetUtils;

import java.util.Iterator;
import java.util.List;

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
                Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
