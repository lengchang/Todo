package com.example.linukey.teamtask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.linukey.addedit_selftask.AddEditSelfTaskActivity;
import com.example.linukey.addedit_teamtask.AddEditTeamTaskActivity;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.todo.R;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.todo.SwipeMenu.SwipeMenuItem;
import com.example.linukey.util.TodoHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by linukey on 12/9/16.
 */

public class TeamTaskPresenter implements TeamTaskContract.TeamTaskActivityPresenter {
    TeamTaskContract.TeamTaskActivityView view;
    List<TeamTask> datasourceTeamTasks = null;
    List<TeamTask> datasourceCurrent = null;

    public TeamTaskPresenter(TeamTaskContract.TeamTaskActivityView view) {
        this.view = view;
    }

    @Override
    public void CreateMenu(Menu menu) {
        MenuItem taskAdd = menu.add(0, 0, 0, "添加");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public void addTeamTask(Context context) {
        Intent intent = new Intent(context, AddEditTeamTaskActivity.class);
        view.showAddTeamTask(intent);
    }

    @Override
    public void notifyTeamTaskDataChanged(String menuName, Context context) {
        LocalDateSource.updateTeamTasks(TodoHelper.getInstance());
        datasourceTeamTasks = LocalDateSource.teamTasks;

        switch (menuName) {
            case "today":
                try {
                    datasourceCurrent = getTodayDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "tomorrow":
                try {
                    datasourceCurrent = getTomorrowDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "next":
                try {
                    datasourceCurrent = getNextDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        if (datasourceCurrent != null)
            view.showAfterDataSourceChanged(datasourceCurrent);
        else
            view.showAfterDataSourceChanged(new ArrayList<TeamTask>());
    }

    @Override
    public List<TeamTask> getTomorrowDate() throws ParseException {
        List<TeamTask> result = null;
        if (datasourceTeamTasks != null && datasourceTeamTasks.size() > 0) {
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for (TeamTask teamTask : datasourceTeamTasks) {
                if (!isOvertimeOrCompleteOrDelete(teamTask) &&
                        tomorrow.getTime() >= sdt.parse(teamTask.getStarttime()).getTime()
                        && tomorrow.getTime() <= sdt.parse(teamTask.getEndtime()).getTime()) {
                    result.add(teamTask);
                }
            }
        }
        return result;
    }

    @Override
    public List<TeamTask> getTodayDate() throws ParseException {
        List<TeamTask> result = null;
        if (datasourceTeamTasks != null && datasourceTeamTasks.size() > 0) {
            result = new ArrayList<>();
            Date today = null;
            try {
                today = getDateToday();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            for (TeamTask teamTask : datasourceTeamTasks) {
                if (!isOvertimeOrCompleteOrDelete(teamTask) &&
                        today.getTime() >= sdt.parse(teamTask.getStarttime()).getTime()
                        && today.getTime() <= sdt.parse(teamTask.getEndtime()).getTime()) {
                    result.add(teamTask);
                }
            }
        }
        return result;
    }

    @Override
    public List<TeamTask> getNextDate() throws ParseException {
        List<TeamTask> result = null;
        if (datasourceTeamTasks != null && datasourceTeamTasks.size() > 0) {
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for (TeamTask teamTask : datasourceTeamTasks) {
                if (!isOvertimeOrCompleteOrDelete(teamTask) &&
                        tomorrow.getTime() < sdt.parse(teamTask.getStarttime()).getTime()) {
                    result.add(teamTask);
                }
            }
        }
        return result;
    }

    @Override
    public boolean isOvertimeOrCompleteOrDelete(TeamTask teamTask) {
        if (!teamTask.getState().equals(TodoHelper.TaskState.get("noComplete")))
            return true;
        if (teamTask.getIsdelete().equals("1"))
            return true;
        return false;
    }

    @Override
    public Date getDateToday() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdt.parse(date.getYear() + 1900 + "-"
                + (date.getMonth() + 1) + "-" + date.getDate());
        return today;
    }

    @Override
    public Date getNextDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
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
    public void editTask(int position, Context context) {
        TeamTask teamTask = datasourceCurrent.get(position);
        Intent intent = new Intent(context, AddEditTeamTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", teamTask);
        intent.putExtra("bundle", bundle);
        view.showEditTask(intent);
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
                TeamTask.deleteOne(datasourceCurrent.get(position).getId(), context);
                LocalDateSource.updateTeamTasks(context);
                notifyTeamTaskDataChanged(menuName, context);
            }
        });
        adDel.create();
        adDel.show();
    }

    @Override
    public void completedTask(final int position, final String menuName, final Context context) {
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
                TeamTask.completed(datasourceCurrent.get(position).getId(), context);
                LocalDateSource.updateTeamTasks(context);
                notifyTeamTaskDataChanged(menuName, context);
            }
        });
        adDel.create();
        adDel.show();
    }
}
