package com.example.linukey.teamtask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.TaskClassify;
import com.example.linukey.data.model.Team;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.todo.Adapter.ListViewTeamTaskAdapter;
import com.example.linukey.todo.R;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamTaskActivity extends Activity implements TeamTaskContract.TeamTaskActivityView {

    SwipeMenuListView listViewTask = null;
    final int ResultCode_addTeamTask = 1;
    TeamTaskContract.TeamTaskActivityPresenter presenter;
    String menuName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamtask);

        init();
    }

    public void init() {
        initActionBar();
        presenter = new TeamTaskPresenter(this);
        listViewTask = (SwipeMenuListView) findViewById(R.id.listview_teamtask);
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menuname");

        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listViewTask.setMenuCreator(presenter.getSwipeMenuCreator());
        listViewTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        presenter.editTask(position, TeamTaskActivity.this);
                        break;
                    case 1:
                        presenter.deleteTask(position, menuName, TeamTaskActivity.this);
                        presenter.notifyTeamTaskDataChanged(menuName);
                        break;
                    case 2:
                        presenter.completedTask(position, menuName, TeamTaskActivity.this);
                        presenter.notifyTeamTaskDataChanged(menuName);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ResultCode_addTeamTask) {
            presenter.notifyTeamTaskDataChanged(menuName);
        }
    }


    @Override
    public void showAddTeamTask(Intent intent) {
        startActivityForResult(intent, ResultCode_addTeamTask);
    }

    @Override
    public void showAfterDataSourceChanged(List<TeamTask> teamTasks) {
        ListViewTeamTaskAdapter lva = new ListViewTeamTaskAdapter(this, teamTasks);
        listViewTask.setAdapter(lva);
    }

    @Override
    public void showEditTask(Intent intent) {
        startActivityForResult(intent, ResultCode_addTeamTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        presenter.CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case 0:
                presenter.addTeamTask(this);
                break;
        }
        return true;
    }
}
