package com.example.linukey.addedit_selfpgs;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.Sight;
import com.example.linukey.data.model.TeamTask;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.util.TodoHelper;

import java.text.ParseException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by linukey on 12/9/16.
 */

public class AddEditSelfpgsPresenter implements AddEditSelfpgsContract.AddEditSelfpgsPresenter {

    AddEditSelfpgsContract.AddEditSelfpgsView view = null;

    public AddEditSelfpgsPresenter(AddEditSelfpgsContract.AddEditSelfpgsView view){
        this.view = view;
    }

    @Override
    public boolean saveSight(boolean isEdit, Context context, String title, String content, String selfId, String userId, int preId) {
        Sight sight = new Sight();
        sight.setTitle(title);
        sight.setContent(content);
        sight.setSelfId(selfId);
        sight.setUserId(userId);
        sight.setId(preId);

        if (isEdit)
            return Sight.updateSight(sight, context);
        return Sight.saveSight(sight, context);
    }

    @Override
    public boolean saveGoal(boolean isEdit, Context context, String title, String content, String selfId
            , String state, String userId, int preId) {
        Goal goal = new Goal();
        goal.setTitle(title);
        goal.setContent(content);
        goal.setSelfId(selfId);
        goal.setState(state);
        goal.setUserId(userId);
        goal.setId(preId);

        if (isEdit)
            return Goal.updateGoal(goal, context);
        return new Goal().saveGoal(goal, context);
    }

    @Override
    public boolean saveProject(boolean isEdit, String menuName, Context context, String title, String content, String selfId,
                               String state, String userId, int preId){
        Project project = new Project();
        project.setTitle(title);
        project.setContent(content);
        project.setSelfId(selfId);
        project.setState(state);
        project.setUserId(userId);
        project.setId(preId);
        if(menuName.equals("teamProject"))
            project.setType(TodoHelper.ProjectType.get("team"));
        else if(menuName.equals("project"))
            project.setType(TodoHelper.ProjectType.get("self"));

        if (isEdit)
            return Project.updateProject(project, context);
        return Project.saveProject(project, context);
    }

    @Override
    public boolean MenuChoice(MenuItem item, Activity activity, String menuName) throws ParseException {
        switch (item.getItemId()) {
            case 0:
                activity.setResult(RESULT_CANCELED);
                activity.finish();
                return true;
            case 1:
                if (view.checkInput()) {
                    if (view.savePGS(menuName)) {
                        switch (menuName) {
                            case "project":
                                LocalDateSource.updateProjects(TodoHelper.ProjectType.get("self"), activity);
                                break;
                            case "goal":
                                LocalDateSource.updateGoals(activity);
                                break;
                            case "sight":
                                LocalDateSource.updateSights(activity);
                                break;
                        }
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    } else {
                        Toast.makeText(activity, "系统错误!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return false;
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
}
