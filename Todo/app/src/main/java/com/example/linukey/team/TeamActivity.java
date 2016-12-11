package com.example.linukey.team;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.linukey.data.model.Team;
import com.example.linukey.todo.Adapter.TeamAdapter;
import com.example.linukey.todo.R;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuListView;

import java.util.List;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamActivity extends Activity implements TeamContract.TeamPTView {

    TeamContract.TeamPTPresenter presenter = null;
    SwipeMenuListView listViewTeam = null;
    final int ResultCode_addTeam = 1;
    String menuName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        init();
    }

    public void init(){
        initActionBar();
        presenter = new TeamPresenter(this);
        listViewTeam = (SwipeMenuListView)findViewById(R.id.listview_team);
        listViewTeam.setMenuCreator(presenter.getSwipeMenuCreator());
        listViewTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listViewTeam.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        presenter.editTask(position, TeamActivity.this);
                        break;
                    case 1:
                        presenter.deleteOne(position, TeamActivity.this);
                        break;
                    default:
                        break;
                }
            }
        });

        presenter.notifyDataSourceTeamsChanged(this);
    }

    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showAddTeam(Intent intent) {
        startActivityForResult(intent, ResultCode_addTeam);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ResultCode_addTeam) {
            presenter.notifyDataSourceTeamsChanged(this);
        }
    }

    @Override
    public void showEditTask(Intent intent) {
        startActivityForResult(intent, ResultCode_addTeam);
    }

    @Override
    public void showDataSourceChangedView(List<Team> datasourceTeams) {
        TeamAdapter teamAdapter = new TeamAdapter(this, datasourceTeams);
        listViewTeam.setAdapter(teamAdapter);
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
                presenter.addTeam(menuName, this);
                break;
        }
        return true;
    }
}
