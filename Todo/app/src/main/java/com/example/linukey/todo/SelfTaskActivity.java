package com.example.linukey.todo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.linukey.BLL.Adapter.ListViewSelfTaskAdapter;
import com.example.linukey.BLL.SelfTaskBLL;
import com.example.linukey.BLL.SwipeMenu.SwipeMenu;
import com.example.linukey.BLL.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.BLL.SwipeMenu.SwipeMenuItem;
import com.example.linukey.BLL.SwipeMenu.SwipeMenuListView;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.Goal;
import com.example.linukey.Model.Project;
import com.example.linukey.Model.SelfTask;
import com.example.linukey.Model.Sight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class SelfTaskActivity extends Activity{
    final Context context = this;
    List<SelfTask> datesourceTask;
    SwipeMenuListView listViewTask;
    String menuName = null;
    final int addSelfTask_ResultCode = 1;
    List<Project> projectList = LocalDateSource.projects;
    List<Goal> goalList = LocalDateSource.goals;
    List<Sight> sightList = LocalDateSource.sights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selftask);

        try {
            initDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initActionBar();
    }

    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initDate() throws ParseException {
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menuname");
        listViewTask = (SwipeMenuListView) findViewById(R.id.listview_selftask);
        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelfTaskDialogFragment selfTaskDialogFragment = new SelfTaskDialogFragment();
                SelfTask selfTask = datesourceTask.get(position);
                String project = null;
                String goal = null;
                String sight = null;
                if(selfTask.getProjectId() != null){
                    for(Project p : projectList){
                        if(p.getSelfId().equals(selfTask.getProjectId()))
                            project = p.getTitle();
                    }
                }
                if(selfTask.getGoalId() != null){
                    for(Goal g : goalList){
                        if(g.getSelfId().equals(selfTask.getGoalId()))
                            goal = g.getTitle();
                    }
                }
                if(selfTask.getSightId() != null){
                    for(Sight s : sightList){
                        if(s.getSelfId().equals(selfTask.getSightId()))
                            sight = s.getTitle();
                    }
                }
                if(menuName.equals("box"))
                    selfTaskDialogFragment.initDate(selfTask.getTitle(), selfTask.getContent(),
                            null, null, null, null, null, null);
                else
                    selfTaskDialogFragment.initDate(selfTask.getTitle(),selfTask.getContent(),
                            selfTask.getStarttime(), selfTask.getEndtime(), selfTask.getClocktime(),
                            project, goal, sight);
                selfTaskDialogFragment.show(getFragmentManager(), "selfTaskDiaglog");
            }
        });

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
                EditItem.setId(0);
                menu.addMenuItem(EditItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setId(1);
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FF2730")));
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem completeItem = new SwipeMenuItem(getApplicationContext());
                completeItem.setBackground(new ColorDrawable(Color.parseColor("#FF9700")));
                completeItem.setWidth(200);
                completeItem.setId(2);
                completeItem.setTitle("完成");
                completeItem.setTitleSize(18);
                completeItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(completeItem);
            }
        };
        listViewTask.setMenuCreator(creator);
        listViewTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(final int position, final SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        SelfTask selfTask = datesourceTask.get(position);

                        Intent intent = new Intent("com.linukey.Todo.AddSelfTaskActivity");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("date", selfTask);
                        intent.putExtra("bundle", bundle);

                        startActivityForResult(intent, addSelfTask_ResultCode);
                        break;
                    case 1:
                        AlertDialog.Builder adDel = new AlertDialog.Builder(SelfTaskActivity.this);
                        adDel.setMessage("是否要删除?");
                        adDel.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adDel.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SelfTaskBLL().deleteOne(datesourceTask.get(position).getId(),
                                        SelfTaskActivity.this);
                                LocalDateSource.updateSelfTasks(SelfTaskActivity.this, TodoHelper.UserId);
                                try {
                                    notifyTaskDateSourceChanged(menuName);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        adDel.create();
                        adDel.show();
                        break;
                    case 2:
                        AlertDialog.Builder adCom = new AlertDialog.Builder(SelfTaskActivity.this);
                        adCom.setMessage("是否已经完成?");
                        adCom.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adCom.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SelfTaskBLL().completed(datesourceTask.get(position).getId(),
                                        SelfTaskActivity.this);
                                LocalDateSource.updateSelfTasks(SelfTaskActivity.this, TodoHelper.UserId);
                                try {
                                    notifyTaskDateSourceChanged(menuName);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        adCom.create();
                        adCom.show();
                        break;
                    default:
                        break;
                }
            }
        });

        switch (menuName){
            case "today":
                datesourceTask = getTodayDate();
                break;
            case "tomorrow":
                datesourceTask = getTomorrowDate();
                break;
            case "next":
                datesourceTask = getNextDate();
                break;
            case "box":
                datesourceTask = getBoxDate();
                break;
            default:
                break;
        }
        
        if(datesourceTask != null){
            ListViewSelfTaskAdapter lva = new ListViewSelfTaskAdapter(this, datesourceTask, menuName);
            listViewTask.setAdapter(lva);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == addSelfTask_ResultCode) {
            try {
                notifyTaskDateSourceChanged(menuName);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyTaskDateSourceChanged(String menuName) throws ParseException {
        switch (menuName) {
            case "today":
                datesourceTask = getTodayDate();
                break;
            case "tomorrow":
                datesourceTask = getTomorrowDate();
                break;
            case "next":
                datesourceTask = getNextDate();
                break;
            case "box":
                datesourceTask = getBoxDate();
                break;
            default:
                break;
        }

        if(datesourceTask != null){
            ListViewSelfTaskAdapter lva = new ListViewSelfTaskAdapter(this, datesourceTask, menuName);
            listViewTask.setAdapter(lva);
        }
    }

    private List<SelfTask> getTodayDate() throws ParseException {
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() >0){
            result = new ArrayList<>();
            Date today = getDateToday();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrNotUser(selfTask) &&
                        today.getTime() >= sdt.parse(selfTask.getStarttime()).getTime()
                        && today.getTime() <= sdt.parse(selfTask.getEndtime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private List<SelfTask> getTomorrowDate() throws ParseException{
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() > 0){
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrNotUser(selfTask) &&
                        tomorrow.getTime() >= sdt.parse(selfTask.getStarttime()).getTime()
                        && tomorrow.getTime() <= sdt.parse(selfTask.getEndtime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private List<SelfTask> getNextDate() throws ParseException{
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() >0){
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrNotUser(selfTask) &&
                        tomorrow.getTime() < sdt.parse(selfTask.getStarttime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private List<SelfTask> getBoxDate() throws ParseException{
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() > 0){
            result = new ArrayList<>();
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(Integer.parseInt(selfTask.getIsTmp()) == 1){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private boolean isBoxTaskOrOvertimeOrCompleteOrNotUser(SelfTask selfTask){
        if(Integer.parseInt(selfTask.getIsTmp()) == 1)
            return true;
        if(!selfTask.getState().equals(TodoHelper.TaskState.get("noComplete")))
            return true;
        if(!selfTask.getUserId().equals(TodoHelper.UserId))
            return true;

        return false;
    }

    private Date getDateToday() throws ParseException{
        Date date = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdt.parse(date.getYear()+1900+"-"
                + (date.getMonth()+1) + "-" + date.getDate());
        return today;
    }

    public Date getNextDay(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }

    private void addSelfTask(){
        Intent addSelfTask = new Intent("com.linukey.Todo.AddSelfTaskActivity");
        startActivityForResult(addSelfTask, addSelfTask_ResultCode);
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
                addSelfTask();
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