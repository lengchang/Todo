package com.example.linukey.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.linukey.addedit_selftask.AddEditSelfTaskActivity;

/**
 * Created by linukey on 12/8/16.
 */

public class MainPresenter implements IMainContract.Presenter {
    private final IMainContract.View todoView;

    public MainPresenter(IMainContract.View todoView){
        this.todoView = todoView;
    }

    @Override
    public void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem setting = menu.add(0,1,1, "系统设置");
        setting.setIcon(R.mipmap.setting);
        setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public void addSelfTask(Context context){
        Intent addSelfTask = new Intent(context, AddEditSelfTaskActivity.class);
        todoView.addSelfTask(addSelfTask);
    }

    @Override
    public boolean MenuChoice(MenuItem item, Context context){
        switch (item.getItemId()){
            case 0:
                addSelfTask(context);
                return true;
            case 1:
                return true;
        }
        return false;
    }
}
