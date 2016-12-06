package com.example.linukey.todo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

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

public class MainActivity extends Activity {

    boolean homeSelect, selfSelect, teamSelect;

    final int addSelfTask_ResultCode = 1;

    HomePageFragment homePageFragment;
    SelfTaskMenuFragment selfTaskMenuFragment;
    TeamTaskMenuFragment teamTaskMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        getFragmentManager().beginTransaction().add(R.id.menuFragment, homePageFragment).commit();

        //initEMClient();
        //LoginEM();
    }

    private void initFragment(){
        homePageFragment = new HomePageFragment();
        selfTaskMenuFragment = new SelfTaskMenuFragment();
        teamTaskMenuFragment = new TeamTaskMenuFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem setting = menu.add(0,1,1, "系统设置");
        setting.setIcon(R.mipmap.setting);
        setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    private boolean MenuChoice(MenuItem item){
        switch (item.getItemId()){
            case 0:
                addSelfTask();
                return true;
            case 1:
                Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }

    private void addSelfTask(){
        Intent addSelfTask = new Intent("com.linukey.Todo.AddSelfTaskActivity");
        startActivityForResult(addSelfTask, addSelfTask_ResultCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == addSelfTask_ResultCode) {

        }
    }

    public void onClick_Home(View view){
        homeSelect = true;

        getFragmentManager().beginTransaction().replace(R.id.menuFragment, homePageFragment).commit();
        changeIcon();
    }

    public void onClick_Self(View view){
        selfSelect = true;

        getFragmentManager().beginTransaction().replace(R.id.menuFragment, selfTaskMenuFragment).commit();
        changeIcon();
    }

    public void onClick_Team(View view){
        teamSelect = true;

        getFragmentManager().beginTransaction().replace(R.id.menuFragment, teamTaskMenuFragment).commit();
        changeIcon();
    }

    private void changeIcon(){
        Button btnHome = (Button)findViewById(R.id.btnHome);
        Button btnSelf = (Button)findViewById(R.id.btnSelf);
        Button btnTeam = (Button)findViewById(R.id.btnTeam);

        //设置背景
        Drawable home_pre, self_pre, team_pre;
        home_pre = getDrawable(R.mipmap. home_pre);
        home_pre.setBounds(0, 0, home_pre.getMinimumWidth(), home_pre.getMinimumHeight());
        self_pre = getDrawable(R.mipmap.self_pre);
        self_pre.setBounds(0, 0, self_pre.getMinimumWidth(), self_pre.getMinimumHeight());
        team_pre = getDrawable(R.mipmap. team_pre);
        team_pre.setBounds(0, 0, team_pre.getMinimumWidth(), team_pre.getMinimumHeight());

        //设置按之前的背景
        btnHome.setCompoundDrawables(null, home_pre, null, null);
        btnSelf.setCompoundDrawables(null, self_pre, null, null);
        btnTeam.setCompoundDrawables(null, team_pre, null, null);
        btnHome.setTextColor(Color.parseColor("#969696"));
        btnSelf.setTextColor(Color.parseColor("#969696"));
        btnTeam.setTextColor(Color.parseColor("#969696"));


        //设置背景
        Drawable home_beh, self_beh, team_beh;
        home_beh = getDrawable(R.mipmap. home_beh);
        home_beh.setBounds(0, 0, home_beh.getMinimumWidth(), home_beh.getMinimumHeight());
        self_beh = getDrawable(R.mipmap.self_beh);
        self_beh.setBounds(0, 0, self_beh.getMinimumWidth(), self_beh.getMinimumHeight());
        team_beh = getDrawable(R.mipmap. team_beh);
        team_beh.setBounds(0, 0, team_beh.getMinimumWidth(), team_beh.getMinimumHeight());

        //设置按之后的背景
        if(homeSelect) {
            btnHome.setCompoundDrawables(null, home_beh, null, null);
            btnHome.setTextColor(Color.parseColor("#2E66CC"));
        }
        if(selfSelect) {
            btnSelf.setCompoundDrawables(null, self_beh, null, null);
            btnSelf.setTextColor(Color.parseColor("#2E66CC"));
        }
        if(teamSelect) {
            btnTeam.setCompoundDrawables(null, team_beh, null, null);
            btnTeam.setTextColor(Color.parseColor("#2E66CC"));
        }

        homeSelect = false;
        selfSelect = false;
        teamSelect = false;
    }

    public void initEMClient(){
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);


        Context appContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e("TAG", "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private void LoginEM(){
        EMClient.getInstance().login(TodoHelper.UserName, TodoHelper.PassWord, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "login success!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onProgress(int progress, String status) {

            }
            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！" + message);
            }
        });

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

        //注册接受消息的监听器
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(EMMessage message : messages){
                        Toast.makeText(getApplicationContext(), message.getUserName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){

                        }
                        //连接不到聊天服务器
                        else{
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }
}
