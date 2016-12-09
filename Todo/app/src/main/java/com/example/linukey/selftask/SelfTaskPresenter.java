package com.example.linukey.selftask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.linukey.addedit_selftask.AddEditSelfTaskActivity;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.todo.SwipeMenu.SwipeMenuItem;
import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Sight;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.todo.R;
import com.example.linukey.util.TodoHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by linukey on 12/8/16.
 */

public class SelfTaskPresenter implements SelfTaskContract.ActivityPresenter{
    private final SelfTaskContract.ActivityView selfTaskView;
    List<SelfTask> datesourceTasks = null;
    List<SelfTask> datesourceCurrent = null;
    List<Project> projectList = LocalDateSource.projects;
    List<Goal> goalList = LocalDateSource.goals;
    List<Sight> sightList = LocalDateSource.sights;

    public SelfTaskPresenter(SelfTaskContract.ActivityView selfTaskView){
        this.selfTaskView = selfTaskView;
    }

    @Override
    public void notifyTaskDateSourceChanged(String menuName) {
        LocalDateSource.updateSelfTasks(TodoHelper.getInstance());
        datesourceTasks = LocalDateSource.selfTasks;

        switch (menuName) {
            case "today":
                try {
                    datesourceCurrent = getTodayDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "tomorrow":
                try {
                    datesourceCurrent = getTomorrowDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "next":
                try {
                    datesourceCurrent = getNextDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "box":
                try {
                    datesourceCurrent = getBoxDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        if(datesourceCurrent != null)
            selfTaskView.showAfterDataSourceChanged(datesourceCurrent);
        else
            selfTaskView.showAfterDataSourceChanged(new ArrayList<SelfTask>());
    }

    @Override
    public List<SelfTask> getTodayDate() throws ParseException {
        List<SelfTask> result = null;
        if(datesourceTasks != null && datesourceTasks.size() >0){
            result = new ArrayList<>();
            Date today = getDateToday();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            for(SelfTask selfTask : datesourceTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrDelete(selfTask) &&
                        today.getTime() >= sdt.parse(selfTask.getStarttime()).getTime()
                        && today.getTime() <= sdt.parse(selfTask.getEndtime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    @Override
    public List<SelfTask> getTomorrowDate() throws ParseException{
        List<SelfTask> result = null;
        if(datesourceTasks != null && datesourceTasks.size() > 0){
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for(SelfTask selfTask : datesourceTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrDelete(selfTask) &&
                        tomorrow.getTime() >= sdt.parse(selfTask.getStarttime()).getTime()
                        && tomorrow.getTime() <= sdt.parse(selfTask.getEndtime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    @Override
    public List<SelfTask> getNextDate() throws ParseException{
        List<SelfTask> result = null;
        if(datesourceTasks != null && datesourceTasks.size() >0){
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for(SelfTask selfTask : datesourceTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrDelete(selfTask) &&
                        tomorrow.getTime() < sdt.parse(selfTask.getStarttime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    @Override
    public List<SelfTask> getBoxDate() throws ParseException{
        List<SelfTask> result = null;
        if(datesourceTasks != null && datesourceTasks.size() > 0){
            result = new ArrayList<>();
            for(SelfTask selfTask : datesourceTasks){
                if(Integer.parseInt(selfTask.getIsTmp()) == 1){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    @Override
    public boolean isBoxTaskOrOvertimeOrCompleteOrDelete(SelfTask selfTask){
        if(Integer.parseInt(selfTask.getIsTmp()) == 1)
            return true;
        if(!selfTask.getState().equals(TodoHelper.TaskState.get("noComplete")))
            return true;
        if(selfTask.isdelete().equals("1"))
            return true;

        return false;
    }

    @Override
    public Date getDateToday() throws ParseException{
        Date date = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdt.parse(date.getYear()+1900+"-"
                + (date.getMonth()+1) + "-" + date.getDate());
        return today;
    }

    @Override
    public Date getNextDay(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }

    @Override
    public void addSelfTask(Context context){
        Intent addSelfTask = new Intent(context, AddEditSelfTaskActivity.class);
        selfTaskView.showAddTask(addSelfTask);
    }

    @Override
    public void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public SelfTask getCurrentTask(int position){
        return datesourceCurrent.get(position);
    }

    @Override
    public String getTaskGoalTitle(String goalId) {
        String goalTitle = null;
        if(goalId != null){
            for(Goal g : goalList){
                if(g.getSelfId().equals(goalId))
                    goalTitle = g.getTitle();
            }
        }
        return goalTitle;
    }

    @Override
    public String getTaskProjectTitle(String projectId){
        String projectTitle = null;
        if(projectId != null){
            for(Project p : projectList){
                if(p.getSelfId().equals(projectId))
                    projectTitle = p.getTitle();
            }
        }
        return projectTitle;
    }

    @Override
    public String getTaskSightTitle(String sightId){
        String sightTitle = null;
        if(sightId != null){
            for(Sight s : sightList){
                if(s.getSelfId().equals(sightId))
                    sightTitle = s.getTitle();
            }
        }
        return sightTitle;
    }

    @Override
    public SwipeMenuCreator getSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem EditItem = new SwipeMenuItem(TodoHelper.getInstance());
                EditItem.setBackground(new ColorDrawable(Color.parseColor("#C7C6CC")));
                EditItem.setWidth(200);
                EditItem.setTitle("编辑");
                EditItem.setTitleSize(18);
                EditItem.setTitleColor(Color.WHITE);
                EditItem.setId(0);
                menu.addMenuItem(EditItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(TodoHelper.getInstance());
                deleteItem.setId(1);
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FF2730")));
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem completeItem = new SwipeMenuItem(TodoHelper.getInstance());
                completeItem.setBackground(new ColorDrawable(Color.parseColor("#FF9700")));
                completeItem.setWidth(200);
                completeItem.setId(2);
                completeItem.setTitle("完成");
                completeItem.setTitleSize(18);
                completeItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(completeItem);
            }
        };
        return creator;
    }

    @Override
    public void editTask(int position, Context context){
        SelfTask selfTask = datesourceCurrent.get(position);
        Intent intent = new Intent(context, AddEditSelfTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", selfTask);
        intent.putExtra("bundle", bundle);
        selfTaskView.showEditTask(intent);
    }

    @Override
    public void deleteTask(final int position, final String menuName, final Context context) {
        AlertDialog.Builder adDel = new AlertDialog.Builder(context);
        adDel.setMessage("是否要删除?");
        adDel.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adDel.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SelfTask().deleteOne(datesourceCurrent.get(position).getId(), context);
                LocalDateSource.updateSelfTasks(context);
                notifyTaskDateSourceChanged(menuName);
            }
        });
        adDel.create();
        adDel.show();
    }

    @Override
    public void completedTask(final int position, final String menuName, final Context context) {
        AlertDialog.Builder adCom = new AlertDialog.Builder(context);
        adCom.setMessage("是否已经完成?");
        adCom.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adCom.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TeamTask.completed(datesourceCurrent.get(position).getId(), context);
                LocalDateSource.updateTeamTasks(context);
            }
        });
        adCom.create();
        adCom.show();
    }
}
