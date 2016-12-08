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

public class AddEditSelfTaskActivity extends Activity {
    final Context context = this;
    ViewHolder viewHolder;
    boolean isEdit = false;
    List<Project> projectList = LocalDateSource.projects;
    List<Goal> goalList = LocalDateSource.goals;
    List<Sight> sightList = LocalDateSource.sights;
    int preId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselftask);
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
            for (int i = 0; i < projectList.size(); i++) {
                if (projectList.get(i).getSelfId().equals(selfTask.getProjectId())) {
                    viewHolder.projects.setSelection(i + 1);
                }
            }
        }
        if (selfTask.getGoalId() != null) {
            for (int i = 0; i < goalList.size(); i++) {
                if (goalList.get(i).getSelfId().equals(selfTask.getGoalId())) {
                    viewHolder.goals.setSelection(i + 1);
                }
            }
        }
        if (selfTask.getSightId() != null) {
            for (int i = 0; i < sightList.size(); i++) {
                if (sightList.get(i).getSelfId().equals(selfTask.getSightId())) {
                    viewHolder.sights.setSelection(i + 1);
                }
            }
        }
    }

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

    public void initControl() {
        Date date = new Date();
        TextView starttime = (TextView) findViewById(R.id.starttime);
        starttime.setText(date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate());

        TextView endtime = (TextView) findViewById(R.id.endtime);
        endtime.setText(date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate());

        TextView clocktime = (TextView) findViewById(R.id.clocktime);
        clocktime.setText("0:0");

        if (projectList != null)
            initProjectSpiner(projectList);
        if (goalList != null)
            initGoalSpiner(goalList);
        if (sightList != null)
            initSightSpiner(sightList);
    }

    public void initProjectSpiner(List<Project> projects){
        List<String> projectNames = new ArrayList<>();
        projectNames.add("");
        for(Project project : projects){
            projectNames.add(project.getTitle());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                projectNames);
        viewHolder.projects.setAdapter(arrayAdapter);
    }

    public void initGoalSpiner(List<Goal> goals){
        List<String> goalNames = new ArrayList<>();
        goalNames.add("");
        for(Goal goal : goals){
            goalNames.add(goal.getTitle());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                goalNames);
        viewHolder.goals.setAdapter(arrayAdapter);
    }

    public void initSightSpiner(List<Sight> sights){
        List<String> sightNames = new ArrayList<>();
        sightNames.add("");
        for(Sight sight : sights){
            sightNames.add(sight.getTitle());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                sightNames);
        viewHolder.sights.setAdapter(arrayAdapter);
    }

    public void onClick_dateSelect(View view) {
        final TextView textView = (TextView) view;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(textView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year + "-" + ++month + "-" + dayOfMonth;
                        textView.setText(date);
                    }
                }, date.getYear() + 1900, date.getMonth(), date.getDate());
        datePickerDialog.show();
    }

    public void onClick_timeSelect(View view) {
        final TextView textView = (TextView) view;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(textView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        textView.setText(time);
                    }
                }, date.getHours(), date.getMinutes(), true);
        timePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            return MenuChoice(item);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void CreateMenu(Menu menu) {
        MenuItem taskAdd = menu.add(0, 0, 0, "cancel");
        taskAdd.setTitle("取消");
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem setting = menu.add(0, 1, 1, "ok");
        setting.setTitle("保存");
        setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    private boolean MenuChoice(MenuItem item) throws ParseException {
        switch (item.getItemId()) {
            case 0:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case 1:
                if (checkInput()) {
                    if (saveTask()) {
                        LocalDateSource.updateSelfTasks(this);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
                return true;
        }
        return false;
    }

    private boolean saveTask() {
        String title = viewHolder.title.getText().toString().trim();
        String content = viewHolder.content.getText().toString().trim();
        String starttime = viewHolder.starttime.getText().toString();
        String endtime = viewHolder.endtime.getText().toString();
        String clocktime = viewHolder.clocktime.getText().toString();
        String projectId = null;
        if (viewHolder.projects.getSelectedItem() != null
                && !viewHolder.projects.getSelectedItem().toString().equals(""))
            projectId = projectList.get(((int) viewHolder.projects.getSelectedItemId()) - 1).getSelfId();
        String goalId = null;
        if (viewHolder.goals.getSelectedItem() != null
                && !viewHolder.goals.getSelectedItem().toString().equals(""))
            goalId = goalList.get(((int) viewHolder.goals.getSelectedItemId()) - 1).getSelfId();
        String sightId = null;
        if (viewHolder.sights.getSelectedItem() != null
                && !viewHolder.sights.getSelectedItem().toString().equals(""))
            sightId = sightList.get(((int) viewHolder.sights.getSelectedItem()) - 1).getSelfId();
        String userId = TodoHelper.UserId;
        String state = TodoHelper.TaskState.get("noComplete");
        String isdelete = "0";
        String istmp = "0";
        if (viewHolder.istmp.isChecked())
            istmp = "1";

        SelfTask selfTask = new SelfTask(preId, title, content, starttime, endtime, clocktime, projectId,
                goalId, sightId, userId, state, isdelete, istmp);

        if (isEdit) {
            return new SelfTask().updateTaskInfo(selfTask, this);
        } else {
            return new SelfTask().saveTaskInfo(selfTask, this);
        }
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

    private boolean checkInput() throws ParseException {
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}