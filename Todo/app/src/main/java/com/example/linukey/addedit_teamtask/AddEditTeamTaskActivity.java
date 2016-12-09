package com.example.linukey.addedit_teamtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Team;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.todo.R;
import com.example.linukey.util.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linukey on 12/9/16.
 */

public class AddEditTeamTaskActivity extends Activity implements AddEditTeamTaskContract.AddEditTeamTaskView {
    AddEditTeamTaskContract.AddEditTeamTaskPresenter presenter = null;
    ViewHolder viewHolder = null;
    int preId = 0;
    boolean isEdit = false;

    class ViewHolder {
        EditText title;
        EditText content;
        TextView starttime;
        TextView endtime;
        Spinner projects;
        Spinner teamnames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteamtask);

        init();
    }

    public void init() {
        presenter = new AddEditTeamTaskPresenter(this);
        initViewHolder();
        initControl();
        Intent edit = getIntent();
        Bundle bundle = edit.getBundleExtra("bundle");
        if (bundle != null) {
            TeamTask teamTask = (TeamTask) bundle.getSerializable("date");
            initEdit(teamTask);
            preId = teamTask.getId();
            isEdit = true;
        }
    }

    @Override
    public void initEdit(TeamTask teamTask) {
        viewHolder.title.setText(teamTask.getTitle());
        viewHolder.content.setText(teamTask.getContent());
        viewHolder.starttime.setText(teamTask.getStarttime());
        viewHolder.endtime.setText(teamTask.getEndtime());
    }

    public void initViewHolder() {
        viewHolder = new ViewHolder();
        viewHolder.title = (EditText) findViewById(R.id.title);
        viewHolder.content = (EditText) findViewById(R.id.content);
        viewHolder.starttime = (TextView) findViewById(R.id.starttime);
        viewHolder.endtime = (TextView) findViewById(R.id.endtime);
        viewHolder.projects = (Spinner) findViewById(R.id.projects);
        viewHolder.teamnames = (Spinner) findViewById(R.id.teamnames);
    }

    public void onClick_dateSelect(View view) {
        presenter.getDateSeleteDialog(view, this).show();
    }

    @Override
    public void initControl() {
        Date date = new Date();
        String today = date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.getDate();

        TextView starttime = (TextView) findViewById(R.id.starttime);
        starttime.setText(today);

        TextView endtime = (TextView) findViewById(R.id.endtime);
        endtime.setText(today);
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
    public boolean checkInput() throws ParseException {
        if (viewHolder.title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "请输入任务标题!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (viewHolder.content.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "请输入任务内容!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (new SimpleDateFormat("yyyy-MM-dd")
                    .parse(viewHolder.starttime.getText().toString()).getTime() >
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(viewHolder.endtime.getText().toString()).getTime()) {
                Toast.makeText(this, "请输入有效时间范围!", Toast.LENGTH_LONG).show();
                return false;}
//        }else if(viewHolder.teamnames.getSelectedItem() == null ||
//                viewHolder.teamnames.getSelectedItem().toString().isEmpty()){
//            Toast.makeText(this, "请选择所在小组!", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }

    @Override
    public boolean saveTask() {
        String title = viewHolder.title.getText().toString().trim();
        String content = viewHolder.content.getText().toString().trim();
        String starttime = viewHolder.starttime.getText().toString().trim();
        String endtime = viewHolder.endtime.getText().toString().trim();
        String projectId = "1";
        String teamId = "2";

        return presenter.saveTask(isEdit, preId, title, content, starttime, endtime, projectId, teamId);
    }
}
