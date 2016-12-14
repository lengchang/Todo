package com.example.linukey.todo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements IMainContract.View {

    boolean homeSelect, selfSelect, teamSelect;
    HomePageFragment homePageFragment;
    SelfTaskMenuFragment selfTaskMenuFragment;
    TeamTaskMenuFragment teamTaskMenuFragment;
    IMainContract.Presenter todoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        getFragmentManager().beginTransaction().add(R.id.menuFragment, homePageFragment).commit();

        todoPresenter = new MainPresenter(this);
    }

    private void initFragment(){
        homePageFragment = new HomePageFragment();
        selfTaskMenuFragment = new SelfTaskMenuFragment();
        teamTaskMenuFragment = new TeamTaskMenuFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        todoPresenter.CreateMenu(menu);
        return true;
    }

    @Override
    public void addSelfTask(Intent intent){
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return todoPresenter.MenuChoice(item, this);
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

    @Override
    public void changeIcon(){
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
