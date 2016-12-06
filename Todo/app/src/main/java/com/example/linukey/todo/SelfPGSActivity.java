package com.example.linukey.todo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linukey.BLL.ListViewSelfGoalAdapter;
import com.example.linukey.BLL.ListViewSelfProjectAdapter;
import com.example.linukey.BLL.ListViewSelfSightAdapter;
import com.example.linukey.BLL.SwipeMenu;
import com.example.linukey.BLL.SwipeMenuCreator;
import com.example.linukey.BLL.SwipeMenuItem;
import com.example.linukey.BLL.SwipeMenuListView;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.Goal;
import com.example.linukey.Model.Project;
import com.example.linukey.Model.Sight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/4/16.
 */

public class SelfPGSActivity extends Activity {

    SwipeMenuListView listViewPGS = null;
    String menuName = null;
    List<Project> datesourceProject = null;
    List<Goal> datesourceGoal = null;
    List<Sight> datesourceSight = null;
    final int addSelfPGS_ResultCode = 2;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfpgs);

        initDate();
        initActionBar();
    }

    public void initDate(){
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menuname");
        listViewPGS = (SwipeMenuListView)findViewById(R.id.listview_selfpgs);
        switch (menuName){
            case "project":
                datesourceProject = getProjectDate();
                listViewPGS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Project project = datesourceProject.get(position);
                        SelfPGSDialogFragment selfPGSDialogFragment = new SelfPGSDialogFragment();
                        selfPGSDialogFragment.initDate(project.getTitle(), project.getContent());
                        selfPGSDialogFragment.show(getFragmentManager(), "selfpgsdialog");
                    }
                });
                break;
            case "goal":
                datesourceGoal = getGoalsDate();
                listViewPGS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                break;
            case "sight":
                datesourceSight = getSightDate();
                listViewPGS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                break;
            default:
                break;
        }

        if(datesourceProject != null){
            ListViewSelfProjectAdapter lva = new ListViewSelfProjectAdapter(this, datesourceProject);
            listViewPGS.setAdapter(lva);
        }else if(datesourceSight != null){
            ListViewSelfSightAdapter lva = new ListViewSelfSightAdapter(this, datesourceSight);
            listViewPGS.setAdapter(lva);
        }else if(datesourceGoal != null){
            ListViewSelfGoalAdapter lva = new ListViewSelfGoalAdapter(this, datesourceGoal);
            listViewPGS.setAdapter(lva);
        }

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem EditItem = new SwipeMenuItem(
                        getApplicationContext());
                EditItem.setBackground(new ColorDrawable(Color.parseColor("#C7C6CC")));
                EditItem.setWidth(200);
                EditItem.setTitle("编辑");
                EditItem.setTitleSize(18);
                EditItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(EditItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FF2730")));
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);

                if(!menuName.equals("sight")) {
                    SwipeMenuItem completeItem = new SwipeMenuItem(getApplicationContext());
                    completeItem.setBackground(new ColorDrawable(Color.parseColor("#FF9700")));
                    completeItem.setWidth(200);
                    completeItem.setTitle("完成");
                    completeItem.setTitleSize(18);
                    completeItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(completeItem);
                }
            }
        };
        listViewPGS.setMenuCreator(creator);
        listViewPGS.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        if(menuName.equals("project")){
                            Project project = datesourceProject.get(position);
                            Intent intent = new Intent("com.linukey.Todo.AddSelfpgsActivity");
                            intent.putExtra("menuname", menuName);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("date", project);
                            intent.putExtra("bundle", bundle);
                            startActivityForResult(intent, addSelfPGS_ResultCode);
                        }else if(menuName.equals("goal")){
                            Goal goal = datesourceGoal.get(position);
                            Intent intent = new Intent("com.linukey.Todo.AddSelfpgsActivity");
                            intent.putExtra("menuname", menuName);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("date", goal);
                            intent.putExtra("bundle", bundle);
                            startActivityForResult(intent, addSelfPGS_ResultCode);
                        }else if(menuName.equals("sight")){
                            Sight sight = datesourceSight.get(position);
                            Intent intent = new Intent("com.linukey.Todo.AddSelfpgsActivity");
                            intent.putExtra("menuname", menuName);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("date", sight);
                            intent.putExtra("bundle", bundle);
                            startActivityForResult(intent, addSelfPGS_ResultCode);
                        }
                        break;
                    case 1:

                        break;
                    case 3:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == addSelfPGS_ResultCode) {
            notifyPGSDateSourceChanged(menuName);
        }
    }

    public void notifyPGSDateSourceChanged(String menuName){
        switch (menuName){
            case "project":
                datesourceProject = getProjectDate();
                ListViewSelfProjectAdapter lva_project = new ListViewSelfProjectAdapter(this, datesourceProject);
                listViewPGS.setAdapter(lva_project);
                break;
            case "goal":
                datesourceGoal = getGoalsDate();
                ListViewSelfGoalAdapter lva_goal = new ListViewSelfGoalAdapter(this, datesourceGoal);
                listViewPGS.setAdapter(lva_goal);
                break;
            case "sight":
                datesourceSight = getSightDate();
                ListViewSelfSightAdapter lva_sight = new ListViewSelfSightAdapter(this, datesourceSight);
                listViewPGS.setAdapter(lva_sight);
                break;
            default:
                break;
        }
    }

    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private List<Project> getProjectDate(){
        List<Project> result = null;
        if(LocalDateSource.projects != null && LocalDateSource.projects.size() > 0){
            result = new ArrayList<>();
            for(Project project : LocalDateSource.projects){
                if(project.getUserId().equals(TodoHelper.UserId)
                        && project.getState().equals(TodoHelper.PGS_State.get("noComplete"))){
                    result.add(project);
                }
            }
        }
        return result;
    }

    private List<Goal> getGoalsDate(){
        List<Goal> result = null;
        if(LocalDateSource.goals != null && LocalDateSource.goals.size() > 0){
            result = new ArrayList<>();
            for(Goal goal : LocalDateSource.goals){
                if(goal.getUserId().equals(TodoHelper.UserId)
                        && goal.getState().equals(TodoHelper.PGS_State.get("noComplete"))){
                    result.add(goal);
                }
            }
        }
        return result;
    }

    private List<Sight> getSightDate(){
        List<Sight> result = null;
        if(LocalDateSource.sights != null && LocalDateSource.sights.size() > 0){
            result = new ArrayList<>();
            for(Sight sight : LocalDateSource.sights){
                if(sight.getUserId().equals(TodoHelper.UserId)){
                    result.add(sight);
                }
            }
        }
        return result;
    }

    public void addSelfPGS(){
        Intent intent = new Intent("com.linukey.Todo.AddSelfpgsActivity");
        intent.putExtra("menuname", menuName);
        startActivityForResult(intent, addSelfPGS_ResultCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case 0:
                addSelfPGS();
                break;
        }
        return true;
    }

    private void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
}
