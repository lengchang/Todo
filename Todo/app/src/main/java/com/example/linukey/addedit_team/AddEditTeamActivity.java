package com.example.linukey.addedit_team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Team;
import com.example.linukey.todo.R;
import com.example.linukey.util.TodoHelper;
import com.example.linukey.util.ViewHolder;

import java.text.ParseException;
import java.util.UUID;

/**
 * Created by linukey on 12/9/16.
 */

public class AddEditTeamActivity extends Activity implements AddEditTeamContract.AddEditTeamView{

    AddEditTeamContract.AddEditTeamPresenter presenter = null;
    ViewHolder viewHolder = null;
    boolean isEdit = false;
    int preId = 0;

    class ViewHolder{
        EditText title;
        EditText content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteam);

        init();
    }

    public void init(){
        presenter = new AddEditTeamPresenter(this);
        initViewHolder();
        Intent edit = getIntent();
        Bundle bundle = edit.getBundleExtra("bundle");
        if (bundle != null) {
            Team team = (Team) bundle.getSerializable("date");
            initEdit(team);
            preId = team.getId();
            isEdit = true;
        }
    }

    @Override
    public void initEdit(Team team){
        viewHolder.title.setText(team.getTeamname());
        viewHolder.content.setText(team.getContent());
    }

    public void initViewHolder(){
        viewHolder = new ViewHolder();
        viewHolder.title = (EditText)findViewById(R.id.title);
        viewHolder.content = (EditText)findViewById(R.id.content);
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
    public boolean checkInput() {
        if(viewHolder.title.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入小组名称!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(viewHolder.content.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入小组描述!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean saveTeam() {
        String title = viewHolder.title.getText().toString().trim();
        String content = viewHolder.content.getText().toString().trim();
        String leaderId = TodoHelper.UserId;
        String teamId = UUID.randomUUID().toString();

        Team team = new Team(preId, title, content, leaderId, teamId);

        return presenter.saveTeam(team, this, isEdit);
    }
}
