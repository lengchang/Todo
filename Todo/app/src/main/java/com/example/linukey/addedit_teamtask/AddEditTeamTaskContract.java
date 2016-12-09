package com.example.linukey.addedit_teamtask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.linukey.data.model.TeamTask;

import java.text.ParseException;

/**
 * Created by linukey on 12/9/16.
 */

public interface AddEditTeamTaskContract {
    interface AddEditTeamTaskView{

        void initEdit(TeamTask teamTask);

        void initControl();

        boolean checkInput() throws ParseException;

        boolean saveTask();
    }
    interface AddEditTeamTaskPresenter{

        DatePickerDialog getDateSeleteDialog(View view, Context context);

        void CreateMenu(Menu menu);

        boolean MenuChoice(MenuItem item, Activity activity) throws ParseException;

        boolean saveTask(boolean isEdit, int preId, String title, String content, String starttime, String endtime,
                         String projectId, String teamId);
    }
}
