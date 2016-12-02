package com.example.linukey.todo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.SelfTask;

import java.text.ParseException;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends Activity {

    boolean homeSelect, selfSelect, teamSelect;

    final int addSelfTask_ResultCode = 1;

    HomePageFragment homePageFragment;
    SelfTaskFragment selfTaskFragment;
    TeamTaskFragment teamTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        getFragmentManager().beginTransaction().add(R.id.menuFragment, homePageFragment).commit();
    }

    private void initFragment(){
        homePageFragment = new HomePageFragment();
        selfTaskFragment = new SelfTaskFragment();
        teamTaskFragment = new TeamTaskFragment();
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
        Intent addSelfTask = new Intent("com.linukey.Todo.AddSelfActivity");
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

        getFragmentManager().beginTransaction().replace(R.id.menuFragment, selfTaskFragment).commit();
        changeIcon();
    }

    public void onClick_Team(View view){
        teamSelect = true;

        getFragmentManager().beginTransaction().replace(R.id.menuFragment, teamTaskFragment).commit();
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
}
