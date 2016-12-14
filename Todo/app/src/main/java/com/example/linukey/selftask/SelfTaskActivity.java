package com.example.linukey.selftask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.linukey.addedit_selftask.AddEditSelfTaskActivity;
import com.example.linukey.todo.Adapter.ListViewSelfTaskAdapter;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuListView;
import com.example.linukey.todo.R;
import com.example.linukey.data.model.SelfTask;

import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class SelfTaskActivity extends Activity implements SelfTaskContract.ActivityView{
    SwipeMenuListView listViewTask;
    String menuName = null;
    final int addSelfTask_ResultCode = 1;

    SelfTaskContract.ActivityPresenter selfTaskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selftask);

        init();
    }

    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showSelfTaskDialogFragment(int position){
        SelfTaskDialogFragment selfTaskDialogFragment = new SelfTaskDialogFragment();
        SelfTask selfTask = selfTaskPresenter.getCurrentTask(position);
        String projectTitle = selfTaskPresenter.getTaskProjectTitle(selfTask.getProjectId());
        String goalTitle = selfTaskPresenter.getTaskGoalTitle(selfTask.getGoalId());
        String sightTitle = selfTaskPresenter.getTaskSightTitle(selfTask.getSightId());
        if(menuName.equals("box"))
            selfTaskDialogFragment.presenter.setData(selfTask.getTitle(), selfTask.getContent(),
                    null, null, null, null, null, null);
        else
            selfTaskDialogFragment.presenter.setData(selfTask.getTitle(),selfTask.getContent(),
                    selfTask.getStarttime(), selfTask.getEndtime(), selfTask.getClocktime(),
                    projectTitle, goalTitle, sightTitle);
        selfTaskDialogFragment.show(getFragmentManager(), "selfTaskDiaglog");
    }

    @Override
    public void showEditTask(Intent intent){
        startActivityForResult(intent, addSelfTask_ResultCode);
    }

    public void init() {
        initActionBar();
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menuname");

        selfTaskPresenter = new SelfTaskPresenter(this);
        listViewTask = (SwipeMenuListView) findViewById(R.id.listview_selftask);
        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSelfTaskDialogFragment(position);
            }
        });
        listViewTask.setMenuCreator(selfTaskPresenter.getSwipeMenuCreator());
        listViewTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(final int position, final SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        selfTaskPresenter.editTask(position, SelfTaskActivity.this);
                        break;
                    case 1:
                        selfTaskPresenter.deleteTask(position, menuName, SelfTaskActivity.this);
                        selfTaskPresenter.notifyTaskDateSourceChanged(menuName);
                        break;
                    case 2:
                        selfTaskPresenter.completedTask(position, menuName, SelfTaskActivity.this);
                        selfTaskPresenter.notifyTaskDateSourceChanged(menuName);
                        break;
                    default:
                        break;
                }
            }
        });

        selfTaskPresenter.notifyTaskDateSourceChanged(menuName);
    }

    @Override
    public void showAfterDataSourceChanged(List<SelfTask> selfTasks){
        ListViewSelfTaskAdapter lva = new ListViewSelfTaskAdapter(this, selfTasks, menuName);
        listViewTask.setAdapter(lva);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == addSelfTask_ResultCode) {
            selfTaskPresenter.notifyTaskDateSourceChanged(menuName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        selfTaskPresenter.CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case 0:
                selfTaskPresenter.addSelfTask(this);
                break;
        }
        return true;
    }

    @Override
    public void showAddTask(Intent intent){
        startActivityForResult(intent, addSelfTask_ResultCode);
    }
}