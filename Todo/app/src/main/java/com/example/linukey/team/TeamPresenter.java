package com.example.linukey.team;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.linukey.addedit_selftask.AddEditSelfTaskActivity;
import com.example.linukey.addedit_team.AddEditTeamActivity;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Team;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.todo.SwipeMenu.SwipeMenuItem;
import com.example.linukey.util.TodoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/9/16.
 */

public class TeamPresenter implements TeamContract.TeamPTPresenter {

    TeamContract.TeamPTView view = null;
    List<Team> datasourceTeams = null;

    public TeamPresenter(TeamContract.TeamPTView view){
        this.view = view;
    }

    @Override
    public void notifyDataSourceTeamsChanged(Context context){
        LocalDateSource.updateTeams(context);
        datasourceTeams = LocalDateSource.teams;
        if(datasourceTeams != null)
            view.showDataSourceChangedView(datasourceTeams);
        else
            view.showDataSourceChangedView(new ArrayList<Team>());
    }

    @Override
    public void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "创建小组");
        taskAdd.setTitle("创建小组");
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public SwipeMenuCreator getSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem EditItem = new SwipeMenuItem(TodoHelper.getInstance());
                EditItem.setBackground(new ColorDrawable(Color.parseColor("#C7C6CC")));
                EditItem.setWidth(200);
                EditItem.setTitle("编辑");
                EditItem.setTitleSize(18);
                EditItem.setTitleColor(Color.WHITE);
                EditItem.setId(0);
                menu.addMenuItem(EditItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(TodoHelper.getInstance());
                deleteItem.setId(1);
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FF2730")));
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        return creator;
    }

    @Override
    public void addTeam(String menuName, Context context){
        Intent intent = new Intent(context, AddEditTeamActivity.class);
        intent.putExtra("menuname", menuName);
        view.showAddTeam(intent);
    }

    @Override
    public void editTask(int position, Context context){
        Team team = datasourceTeams.get(position);
        Intent intent = new Intent(context, AddEditTeamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", team);
        intent.putExtra("bundle", bundle);
        view.showEditTask(intent);
    }

    @Override
    public void deleteOne(final int position, final Context context){
        AlertDialog.Builder adCom = new AlertDialog.Builder(context);
        adCom.setMessage("是否已经完成?");
        adCom.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adCom.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Team.deleteOne(datasourceTeams.get(position).getId(), context);
                notifyDataSourceTeamsChanged(context);
            }
        });
        adCom.create();
        adCom.show();
    }
}
