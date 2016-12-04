package com.example.linukey.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.linukey.BLL.AddSelfTaskBLL;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.SelfTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linukey on 11/25/16.
 */

public class AddSelfTaskActivity extends Activity {
    final Context context = this;
    AddSelfModel addSelfModel;
    boolean isEdit = false;
    int preId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselftask);
        initAddSelfModel();
        initControl();

        Intent edit = getIntent();
        Bundle bundle = edit.getBundleExtra("bundle");
        if(bundle != null) {
            SelfTask selfTask = (SelfTask) bundle.getSerializable("date");
            initEdit(selfTask);
            preId = selfTask.getId();
            isEdit = true;
        }
    }

    class AddSelfModel{
        TextView starttime;
        TextView endtime;
        TextView clocktime;
        CheckBox istmp;
        EditText title;
        EditText content;
        LinearLayout time;
        LinearLayout classes;
        Spinner projectId;
        Spinner goalId;
        Spinner sightId;
    }

    public void initEdit(SelfTask selfTask){
        addSelfModel.title.setText(selfTask.getTitle());
        addSelfModel.content.setText(selfTask.getContent());
        addSelfModel.starttime.setText(selfTask.getStarttime());
        addSelfModel.endtime.setText(selfTask.getStarttime());
        addSelfModel.clocktime.setText(selfTask.getClocktime());
        if(Integer.parseInt(selfTask.getIsTmp()) == 1){
            addSelfModel.istmp.setChecked(true);
            onClick_selectTmp(null);
        }
    }

    public void initAddSelfModel(){
        addSelfModel = new AddSelfModel();
        addSelfModel.starttime = (TextView)findViewById(R.id.starttime);
        addSelfModel.endtime = (TextView)findViewById(R.id.endtime);
        addSelfModel.istmp = (CheckBox)findViewById(R.id.istmp);
        addSelfModel.title = (EditText)findViewById(R.id.title);
        addSelfModel.content = (EditText)findViewById(R.id.content);
        addSelfModel.clocktime = (TextView)findViewById(R.id.clocktime);
        addSelfModel.time = (LinearLayout)findViewById(R.id.time);
        addSelfModel.classes = (LinearLayout)findViewById(R.id.classes);
        addSelfModel.projectId = (Spinner)findViewById(R.id.project);
        addSelfModel.goalId = (Spinner)findViewById(R.id.goal);
        addSelfModel.sightId = (Spinner)findViewById(R.id.sight);
    }

    public void initControl() {
        Date date = new Date();
        TextView starttime = (TextView) findViewById(R.id.starttime);
        starttime.setText(date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate());

        TextView endtime = (TextView) findViewById(R.id.endtime);
        endtime.setText(date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate());

        TextView clocktime = (TextView) findViewById(R.id.clocktime);
        clocktime.setText("0:0");
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
                    if(saveTask()) {
                        LocalDateSource.updateSelfTasks(this, TodoHelper.UserId);
                        setResult(RESULT_OK);
                        finish();
                    }else {
                        Toast.makeText(this, "系统错误!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return false;
    }

    private boolean saveTask() {
        String title = addSelfModel.title.getText().toString().trim();
        String content = addSelfModel.content.getText().toString().trim();
        String starttime = addSelfModel.starttime.getText().toString();
        String endtime = addSelfModel.endtime.getText().toString();
        String clocktime = addSelfModel.clocktime.getText().toString();
        String projectId = "1";
        String goalId = "2";
        String sightId = "3";
        String userId = TodoHelper.UserId;
        String state = TodoHelper.TaskState.get("noComplete");
        String isdelete = "0";
        String istmp = "0";
        if (addSelfModel.istmp.isChecked())
            istmp = "1";

        SelfTask selfTask = new SelfTask(preId, title, content, starttime, endtime, clocktime, projectId,
                goalId, sightId, userId, state, isdelete, istmp);

        if(isEdit){
            return new AddSelfTaskBLL().updateTaskInfo(selfTask, this);
        }else {
            return new AddSelfTaskBLL().saveTaskInfo(selfTask, this);
        }
    }

    public void onClick_selectTmp(View view){
        if(addSelfModel.istmp.isChecked()) {
            addSelfModel.time.setVisibility(View.INVISIBLE);
            addSelfModel.classes.setVisibility(View.INVISIBLE);
        }else {
            addSelfModel.time.setVisibility(View.VISIBLE);
            addSelfModel.classes.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkInput() throws ParseException {
        if (((EditText) findViewById(R.id.title)).getText().toString().isEmpty()) {
            Toast.makeText(context, "请输入标题!", Toast.LENGTH_LONG).show();
            return false;
        } else if (((EditText) findViewById(R.id.content)).getText().toString().isEmpty()) {
            Toast.makeText(context, "请输入内容!", Toast.LENGTH_LONG).show();
            return false;
        } else if(new SimpleDateFormat("yyyy-MM-dd")
                .parse(addSelfModel.starttime.getText().toString()).getTime() >
                new SimpleDateFormat("yyyy-MM-dd")
                        .parse(addSelfModel.endtime.getText().toString()).getTime()) {
            Toast.makeText(context, "请输入有效时间范围!", Toast.LENGTH_LONG).show();
            return false;
        }else{
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