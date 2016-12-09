package com.example.linukey.addedit_teamtask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.linukey.addedit_selfpgs.AddEditSelfpgsPresenter;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.util.TodoHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by linukey on 12/9/16.
 */

public class AddEditTeamTaskPresenter implements AddEditTeamTaskContract.AddEditTeamTaskPresenter {
    AddEditTeamTaskContract.AddEditTeamTaskView view = null;

    public AddEditTeamTaskPresenter(AddEditTeamTaskContract.AddEditTeamTaskView view){
        this.view = view;
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
                        LocalDateSource.updateTeamTasks(activity);
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean saveTask(boolean isEdit, int preId, String title, String content, String starttime, String endtime,
                            String projectId, String teamId){
        TeamTask teamTask = new TeamTask(preId, title, content, starttime, endtime, "null", projectId,
                teamId, TodoHelper.TaskState.get("noComplete"), "0");
        if(isEdit)
            return TeamTask.updateTeamTask(teamTask, TodoHelper.getInstance());
        return TeamTask.saveTeamTask(teamTask, TodoHelper.getInstance());
    }
}
