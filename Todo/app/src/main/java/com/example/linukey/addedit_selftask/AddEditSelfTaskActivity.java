package com.example.linukey.addedit_selftask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.linukey.todo.R;
import com.example.linukey.util.TodoHelper;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Sight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by linukey on 11/25/16.
 */

public class AddEditSelfTaskActivity extends Activity implements AddEditSelfTaskContract.AddEditSelfTaskView {
    final Context context = this;
    ViewHolder viewHolder;
    boolean isEdit = false;
    AddEditSelfTaskContract.AddEditSelfTaskPresenter presenter = null;
    int preId;

    class ViewHolder {
        TextView starttime;
        TextView endtime;
        TextView clocktime;
        CheckBox istmp;
        EditText title;
        EditText content;
        LinearLayout time;
        LinearLayout classes;
        Spinner projects;
        Spinner goals;
        Spinner sights;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselftask);

        init();
    }

    private void init(){
        presenter = new AddEditSelfTaskPresenter(this);
        initViewHolder();
        initControl();
        Intent edit = getIntent();
        Bundle bundle = edit.getBundleExtra("bundle");
        if (bundle != null) {
            SelfTask selfTask = (SelfTask) bundle.getSerializable("date");
            initEdit(selfTask);
            preId = selfTask.getId();
            isEdit = true;
        }
    }

    @Override
    public void initEdit(SelfTask selfTask) {
        viewHolder.title.setText(selfTask.getTitle());
        viewHolder.content.setText(selfTask.getContent());
        viewHolder.starttime.setText(selfTask.getStarttime());
        viewHolder.endtime.setText(selfTask.getEndtime());
        viewHolder.clocktime.setText(selfTask.getClocktime());
        if (Integer.parseInt(selfTask.getIsTmp()) == 1) {
            viewHolder.istmp.setChecked(true);
            onClick_selectTmp(null);
        }
        if (selfTask.getProjectId() != null) {
            viewHolder.projects.setSelection(presenter.getSpinerProjectSelection(selfTask));
        }
        if (selfTask.getGoalId() != null) {
            viewHolder.goals.setSelection(presenter.getSpinerGoalSelection(selfTask));
        }
        if (selfTask.getSightId() != null) {
            viewHolder.sights.setSelection(presenter.getSpinerSightSelection(selfTask));
        }
    }

    @Override
    public void initViewHolder() {
        viewHolder = new ViewHolder();
        viewHolder.starttime = (TextView) findViewById(R.id.starttime);
        viewHolder.endtime = (TextView) findViewById(R.id.endtime);
        viewHolder.istmp = (CheckBox) findViewById(R.id.istmp);
        viewHolder.title = (EditText) findViewById(R.id.title);
        viewHolder.content = (EditText) findViewById(R.id.content);
        viewHolder.clocktime = (TextView) findViewById(R.id.clocktime);
        viewHolder.time = (LinearLayout) findViewById(R.id.time);
        viewHolder.classes = (LinearLayout) findViewById(R.id.classes);
        viewHolder.projects = (Spinner) findViewById(R.id.projects);
        viewHolder.goals = (Spinner) findViewById(R.id.goals);
        viewHolder.sights = (Spinner) findViewById(R.id.sights);
    }

    @Override
    public void initControl() {
        Date date = new Date();
        String today = date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate();

        TextView starttime = (TextView) findViewById(R.id.starttime);
        starttime.setText(today);

        TextView endtime = (TextView) findViewById(R.id.endtime);
        endtime.setText(today);

        TextView clocktime = (TextView) findViewById(R.id.clocktime);
        clocktime.setText("0:0");

        presenter.initGoalSpiner();
        presenter.initSightSpiner();
        presenter.initSpinerProjects();
    }

    @Override
    public void setSpinerProjectsAdapter(List<String> projectNames){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, projectNames);
        viewHolder.projects.setAdapter(arrayAdapter);
    }

    @Override
    public void setSpinerGoalsAdapter(List<String> goalNames){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, goalNames);
        viewHolder.goals.setAdapter(arrayAdapter);
    }

    @Override
    public void setSpinerSightsAdapter(List<String> sightNames){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                sightNames);
        viewHolder.sights.setAdapter(arrayAdapter);
    }

    public void onClick_dateSelect(View view) {
        presenter.getDateSeleteDialog(view, this).show();
    }

    public void onClick_timeSelect(View view) {
        presenter.getTimeSelectDialog(view, this).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        presenter.CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            return presenter.MenuChoice(item, this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveTask() {
        String title = viewHolder.title.getText().toString().trim();
        String content = viewHolder.content.getText().toString().trim();
        String starttime = viewHolder.starttime.getText().toString();
        String endtime = viewHolder.endtime.getText().toString();
        String clocktime = viewHolder.clocktime.getText().toString();
        String projectId = null;
        if (viewHolder.projects.getSelectedItem() != null
                && !viewHolder.projects.getSelectedItem().toString().equals(""))
            projectId = presenter.getProjectId(((int) viewHolder.projects.getSelectedItemId()) - 1);
        String goalId = null;
        if (viewHolder.goals.getSelectedItem() != null
                && !viewHolder.goals.getSelectedItem().toString().equals(""))
            goalId = presenter.getGoalId(((int) viewHolder.goals.getSelectedItemId()) - 1);
        String sightId = null;
        if (viewHolder.sights.getSelectedItem() != null
                && !viewHolder.sights.getSelectedItem().toString().equals(""))
            sightId = presenter.getSightId(((int) viewHolder.sights.getSelectedItem()) - 1);
        String userId = TodoHelper.UserId;
        String state = TodoHelper.TaskState.get("noComplete");
        String isdelete = "0";
        String istmp = "0";
        if (viewHolder.istmp.isChecked())
            istmp = "1";

        SelfTask selfTask = new SelfTask(preId, title, content, starttime, endtime, clocktime, projectId,
                goalId, sightId, userId, state, isdelete, istmp);

        return presenter.saveTask(selfTask, isEdit, this);
    }

    public void onClick_selectTmp(View view) {
        if (viewHolder.istmp.isChecked()) {
            viewHolder.time.setVisibility(View.INVISIBLE);
            viewHolder.classes.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.time.setVisibility(View.VISIBLE);
            viewHolder.classes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean checkInput() throws ParseException {
        if (((EditText) findViewById(R.id.title)).getText().toString().isEmpty()) {
            Toast.makeText(context, "请输入标题!", Toast.LENGTH_LONG).show();
            return false;
        } else if (((EditText) findViewById(R.id.content)).getText().toString().isEmpty()) {
            Toast.makeText(context, "请输入内容!", Toast.LENGTH_LONG).show();
            return false;
        } else if (new SimpleDateFormat("yyyy-MM-dd")
                .parse(viewHolder.starttime.getText().toString()).getTime() >
                new SimpleDateFormat("yyyy-MM-dd")
                        .parse(viewHolder.endtime.getText().toString()).getTime()) {
            Toast.makeText(context, "请输入有效时间范围!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}