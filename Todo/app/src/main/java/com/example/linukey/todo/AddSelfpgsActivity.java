package com.example.linukey.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linukey.BLL.AddSelfPGSBLL;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.Goal;
import com.example.linukey.Model.Project;
import com.example.linukey.Model.SelfTask;
import com.example.linukey.Model.Sight;

import java.text.ParseException;
import java.util.UUID;

/**
 * Created by linukey on 12/3/16.
 */

public class AddSelfpgsActivity extends Activity {
    private boolean isEdit = false;
    private String menuName = null;
    private int preId;

    class ViewHolder{
        EditText title;
        EditText content;
    }

    ViewHolder viewHolder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addselfpgs);
        initDate();
        initViewHolder();
    }

    private void initDate(){
        Intent edit = getIntent();
        menuName = edit.getStringExtra("menuname");
        Bundle bundle = edit.getBundleExtra("bundle");
        if(bundle != null) {
            SelfTask selfTask = (SelfTask) bundle.getSerializable("date");
            initEdit(selfTask);
            preId = selfTask.getId();
            isEdit = true;
        }
    }

    private void initEdit(SelfTask selfTask){

    }

    private void initViewHolder(){
        viewHolder = new ViewHolder();
        viewHolder.title = (EditText)findViewById(R.id.title);
        viewHolder.content = (EditText)findViewById(R.id.content);
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
                    if(savePGS(menuName)) {
                        switch (menuName){
                            case "project":
                                LocalDateSource.updateProjects(this, TodoHelper.UserId);
                                break;
                            case "goal":
                                LocalDateSource.updateGoals(this, TodoHelper.UserId);
                                break;
                            case "sight":
                                LocalDateSource.updateSights(this, TodoHelper.UserId);
                                break;
                        }
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

    private boolean checkInput(){
        if(viewHolder.title.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入标题!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(viewHolder.content.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入内容!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean saveProject(){
        Project project = new Project();
        project.setTitle(viewHolder.title.getText().toString());
        project.setContent(viewHolder.content.getText().toString());
        project.setProjectId(UUID.randomUUID().toString());
        project.setState(TodoHelper.PGS_State.get("noComplete"));
        project.setUserId(TodoHelper.UserId);

        if(isEdit)
            return new AddSelfPGSBLL().updateProject(project, this);
        return new AddSelfPGSBLL().saveProject(project, this);
    }

    private boolean saveGoal(){
        Goal goal = new Goal();
        goal.setTitle(viewHolder.title.getText().toString());
        goal.setContent(viewHolder.content.getText().toString());
        goal.setGoalId(UUID.randomUUID().toString());
        goal.setState(TodoHelper.PGS_State.get("noComplete"));
        goal.setUserId(TodoHelper.UserId);

        if(isEdit)
            return new AddSelfPGSBLL().updateGoal(goal, this);
        return new AddSelfPGSBLL().saveGoal(goal, this);
    }

    private boolean saveSight(){
        Sight sight = new Sight();
        sight.setTitle(viewHolder.title.getText().toString());
        sight.setContent(viewHolder.content.getText().toString());
        sight.setSightId(UUID.randomUUID().toString());
        sight.setUserId(TodoHelper.UserId);

        if(isEdit)
            return new AddSelfPGSBLL().updateSight(sight, this);
        return new AddSelfPGSBLL().saveSight(sight, this);
    }

    private boolean savePGS(String menuName){
        switch (menuName){
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