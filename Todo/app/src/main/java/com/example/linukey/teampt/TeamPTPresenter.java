package com.example.linukey.teampt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;

import com.example.linukey.addedit_teampt.AddEditTeamPTActivity;
import com.example.linukey.data.model.Project;
import com.example.linukey.todo.R;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.todo.SwipeMenu.SwipeMenuItem;
import com.example.linukey.util.TodoHelper;

import java.util.List;

/**
 * Created by linukey on 12/9/16.
 */

public class TeamPTPresenter implements TeamPTContract.TeamPTPresenter {

    TeamPTContract.TeamPTView view = null;

    public TeamPTPresenter(TeamPTContract.TeamPTView view){
        this.view = view;
    }

    @Override
    public void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
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

                SwipeMenuItem completeItem = new SwipeMenuItem(TodoHelper.getInstance());
                completeItem.setBackground(new ColorDrawable(Color.parseColor("#FF9700")));
                completeItem.setWidth(200);
                completeItem.setId(2);
                completeItem.setTitle("完成");
                completeItem.setTitleSize(18);
                completeItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(completeItem);
            }
        };
        return creator;
    }

    @Override
    public void addTeamPTA(String menuName, Context context){
        Intent intent = new Intent(context, AddEditTeamPTActivity.class);
        intent.putExtra("menuname", menuName);
        view.showAddTeamPTA(intent);
    }
}
