package com.example.linukey.addedit_selftask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Sight;

import java.text.ParseException;
import java.util.List;

/**
 * Created by linukey on 12/9/16.
 */

public interface AddEditSelfTaskContract {
    interface AddEditSelfTaskView{
        void initEdit(SelfTask selfTask);
        void initViewHolder();

        void initControl();

        void setSpinerProjectsAdapter(List<String> projectNames);

        void setSpinerGoalsAdapter(List<String> goalNames);

        void setSpinerSightsAdapter(List<String> sightNames);

        boolean saveTask();

        boolean checkInput() throws ParseException;
    }
    interface AddEditSelfTaskPresenter{

        void initSpinerProjects();

        void initGoalSpiner();

        void initSightSpiner();

        int getSpinerProjectSelection(SelfTask selfTask);

        int getSpinerGoalSelection(SelfTask selfTask);

        int getSpinerSightSelection(SelfTask selfTask);

        DatePickerDialog getDateSeleteDialog(View view, Context context);

        TimePickerDialog getTimeSelectDialog(View view, Context context);

        void CreateMenu(Menu menu);

        boolean MenuChoice(MenuItem item, Activity activity) throws ParseException;

        boolean saveTask(SelfTask selfTask, boolean isEdit, Context context);

        String getProjectId(int index);

        String getGoalId(int index);

        String getSightId(int index);
    }
}
