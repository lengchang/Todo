package com.example.linukey.teampt;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.linukey.todo.R;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuListView;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamPTActivity extends Activity implements TeamPTContract.TeamPTView {

    TeamPTContract.TeamPTPresenter presenter = null;
    SwipeMenuListView listViewPT = null;
    final int ResultCode_addTeamPT = 1;
    String menuName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teampt);

        init();
    }

    public void init(){
        initActionBar();
        presenter = new TeamPTPresenter(this);
        listViewPT = (SwipeMenuListView)findViewById(R.id.listview_teampt);
        listViewPT.setMenuCreator(presenter.getSwipeMenuCreator());
        listViewPT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listViewPT.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showAddTeamPTA(Intent intent) {
        startActivityForResult(intent, ResultCode_addTeamPT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        presenter.CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case 0:
                presenter.addTeamPTA(menuName, this);
                break;
        }
        return true;
    }
}
