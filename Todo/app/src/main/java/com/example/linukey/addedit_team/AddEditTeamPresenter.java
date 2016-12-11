package com.example.linukey.addedit_team;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.linukey.data.model.Team;
import com.example.linukey.data.source.local.LocalDateSource;

import java.text.ParseException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by linukey on 12/9/16.
 */

public class AddEditTeamPresenter implements AddEditTeamContract.AddEditTeamPresenter {
    AddEditTeamContract.AddEditTeamView view = null;

    public AddEditTeamPresenter(AddEditTeamContract.AddEditTeamView view){
        this.view = view;
    }

    @Override
    public void CreateMenu(Menu menu) {
        MenuItem taskAdd = menu.add(0, 0, 0, "cancel");
        taskAdd.setTitle("取消");
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem setting = menu.add(0, 1, 1, "ok");
        setting.setTitle("保存");
        setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean MenuChoice(MenuItem item, Activity activity) throws ParseException {
        switch (item.getItemId()) {
            case 0:
                activity.setResult(RESULT_CANCELED);
                activity.finish();
                return true;
            case 1:
                if (view.checkInput()) {
                    if (view.saveTeam()) {
                        LocalDateSource.updateTeams(activity);
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean saveTeam(Team team, Context context, boolean isEdit){
        if(isEdit)
            return Team.updateTeam(team, context);
        return Team.saveTeam(team, context);
    }
}
