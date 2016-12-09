package com.example.linukey.addedit_selftask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Sight;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.todo.IMainContract;
import com.example.linukey.util.TodoHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by linukey on 12/9/16.
 */

public class AddEditSelfTaskPresenter implements AddEditSelfTaskContract.AddEditSelfTaskPresenter {
    AddEditSelfTaskContract.AddEditSelfTaskView view = null;

    List<Project> projectList = LocalDateSource.projects;
    List<Goal> goalList = LocalDateSource.goals;
    List<Sight> sightList = LocalDateSource.sights;

    public AddEditSelfTaskPresenter(AddEditSelfTaskContract.AddEditSelfTaskView view) {
        this.view = view;
    }

    @Override
    public void initSpinerProjects() {
        List<String> projectNames = new ArrayList<>();
        projectNames.add("");
        if (projectList != null) {
            for (Project project : projectList) {
                projectNames.add(project.getTitle());
            }
            view.setSpinerProjectsAdapter(projectNames);
        }
    }

    @Override
    public void initGoalSpiner() {
        List<String> goalNames = new ArrayList<>();
        goalNames.add("");
        if (goalList != null) {
            for (Goal goal : goalList) {
                goalNames.add(goal.getTitle());
            }
            view.setSpinerGoalsAdapter(goalNames);
        }
    }

    @Override
    public void initSightSpiner() {
        List<String> sightNames = new ArrayList<>();
        sightNames.add("");
        if (sightList != null) {
            for (Sight sight : sightList) {
                sightNames.add(sight.getTitle());
            }
            view.setSpinerSightsAdapter(sightNames);
        }
    }

    @Override
    public int getSpinerProjectSelection(SelfTask selfTask) {
        if (projectList != null)
            for (int i = 0; i < projectList.size(); i++) {
                if (projectList.get(i).getSelfId().equals(selfTask.getProjectId())) {
                    return i + 1;
                }
            }
        return 0;
    }

    @Override
    public int getSpinerGoalSelection(SelfTask selfTask) {
        if (goalList != null)
            for (int i = 0; i < goalList.size(); i++) {
                if (goalList.get(i).getSelfId().equals(selfTask.getGoalId())) {
                    return i + 1;
                }
            }
        return 0;
    }

    @Override
    public int getSpinerSightSelection(SelfTask selfTask) {
        if (sightList != null)
            for (int i = 0; i < sightList.size(); i++) {
                if (sightList.get(i).getSelfId().equals(selfTask.getSightId())) {
                    return i + 1;
                }
            }
        return 0;
    }

    @Override
    public DatePickerDialog getDateSeleteDialog(View view, Context context) {
        final TextView textView = (TextView) view;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(textView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year + "-" + ++month + "-" + dayOfMonth;
                        textView.setText(date);
                    }
                }, date.getYear() + 1900, date.getMonth(), date.getDate());

        return datePickerDialog;
    }

    @Override
    public TimePickerDialog getTimeSelectDialog(View view, Context context) {
        final TextView textView = (TextView) view;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(textView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        textView.setText(time);
                    }
                }, date.getHours(), date.getMinutes(), true);
        return timePickerDialog;
    }

    @Override
    public void CreateMenu(Menu menu) {
        MenuItem taskAdd = menu.add(0, 0, 0, "cancel");
        taskAdd.setTitle("取消");
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem setting = menu.add(0, 1, 1, "ok");
        setting.setTitle("保存");
        setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean MenuChoice(MenuItem item, Activity activity) throws ParseException {
        switch (item.getItemId()) {
            case 0:
                activity.setResult(RESULT_CANCELED);
                activity.finish();
                return true;
            case 1:
                if (view.checkInput()) {
                    if (view.saveTask()) {
                        LocalDateSource.updateSelfTasks(activity);
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean saveTask(SelfTask selfTask, boolean isEdit, Context context) {
        if (isEdit) {
            return SelfTask.updateTaskInfo(selfTask, context);
        } else {
            return SelfTask.saveTaskInfo(selfTask, context);
        }
    }

    @Override
    public String getProjectId(int index) {
        return projectList.get(index).getSelfId();
    }

    @Override
    public String getGoalId(int index) {
        return goalList.get(index).getSelfId();
    }

    @Override
    public String getSightId(int index) {
        return sightList.get(index).getSelfId();
    }
}
