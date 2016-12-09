package com.example.linukey.addedit_selfpgs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linukey.todo.R;
import com.example.linukey.util.TodoHelper;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.Sight;
import com.example.linukey.data.model.TaskClassify;

import java.text.ParseException;
import java.util.UUID;

/**
 * Created by linukey on 12/3/16.
 */

public class AddEditSelfpgsActivity extends Activity implements AddEditSelfpgsContract.AddEditSelfpgsView {
    private boolean isEdit = false;
    private String menuName = null;
    private int preId;
    AddEditSelfpgsContract.AddEditSelfpgsPresenter presenter = null;

    class ViewHolder {
        TextView title;
        TextView content;
    }

    ViewHolder viewHolder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselfpgs);

        init();
    }

    private void init(){
        presenter = new AddEditSelfpgsPresenter(this);
        initViewHolder();
        initDate();
    }

    private void initDate() {
        Intent edit = getIntent();
        menuName = edit.getStringExtra("menuname");
        Bundle bundle = edit.getBundleExtra("bundle");
        if (bundle != null) {
            TaskClassify taskClassify = (TaskClassify) bundle.getSerializable("date");
            preId = taskClassify.getId();
            initEdit(taskClassify);
            isEdit = true;
        }
    }

    @Override
    public void initEdit(TaskClassify taskClassify) {
        viewHolder.title.setText(taskClassify.getTitle());
        viewHolder.content.setText(taskClassify.getContent());
    }

    private void initViewHolder() {
        viewHolder = new ViewHolder();
        viewHolder.title = (TextView) findViewById(R.id.title);
        viewHolder.content = (TextView) findViewById(R.id.content);
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
            return presenter.MenuChoice(item, this, menuName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkInput() {
        if (viewHolder.title.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入标题!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (viewHolder.content.getText().toString().isEmpty()) {
            Toast.makeText(this, "请输入内容!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean saveProject() {
        String title = viewHolder.title.getText().toString();
        String content = viewHolder.content.getText().toString();
        String selfId = UUID.randomUUID().toString();
        String state = TodoHelper.PGS_State.get("noComplete");
        String userId = TodoHelper.UserId;
        return presenter.saveProject(isEdit, this, title, content, selfId, state, userId, preId);
    }

    @Override
    public boolean saveGoal() {
        String title = viewHolder.title.getText().toString();
        String content = viewHolder.content.getText().toString();
        String selfId = UUID.randomUUID().toString();
        String state = TodoHelper.PGS_State.get("noComplete");
        String userId = TodoHelper.UserId;
        return presenter.saveGoal(isEdit, this, title, content, selfId, state, userId, preId);
    }

    @Override
    public boolean saveSight() {
        String title = viewHolder.title.getText().toString();
        String content = viewHolder.content.getText().toString();
        String selfId = UUID.randomUUID().toString();
        String userId = TodoHelper.UserId;

        return presenter.saveSight(isEdit, this, title, content, selfId, userId, preId);
    }

    @Override
    public boolean savePGS(String menuName) {
        switch (menuName) {
            case "project":
                return saveProject();
            case "goal":
                return saveGoal();
            case "sight":
                return saveSight();
            default:
                return false;
        }
    }
}