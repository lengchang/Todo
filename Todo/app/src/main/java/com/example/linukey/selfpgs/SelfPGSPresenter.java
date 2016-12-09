package com.example.linukey.selfpgs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.linukey.addedit_selfpgs.AddEditSelfpgsActivity;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.todo.SwipeMenu.SwipeMenuItem;
import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.Sight;
import com.example.linukey.data.model.TaskClassify;
import com.example.linukey.data.source.local.LocalDateSource;
import com.example.linukey.todo.R;
import com.example.linukey.util.TodoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/8/16.
 */

public class SelfPGSPresenter implements SelfPGSContract.SelfPGSActivityPresenter {
    SelfPGSContract.SelfPGSActivityView selfPGSActivityView;
    List<Project> dataSourceProjects = null;
    List<Goal> dataSourceGoal = null;
    List<Sight> dataSourceSight = null;
    List<TaskClassify> dataSourcePGS = null;

    public SelfPGSPresenter(SelfPGSContract.SelfPGSActivityView view){
        this.selfPGSActivityView = view;
    }

    @Override
    public void notifyPGSDateSourceChanged(String menuName, Context context) {
        switch (menuName) {
            case "project":
                LocalDateSource.updateProjects(TodoHelper.getInstance());
                dataSourceProjects = LocalDateSource.projects;
                dataSourcePGS = getProjectDate();
                break;
            case "goal":
                LocalDateSource.updateGoals(TodoHelper.getInstance());
                dataSourceGoal = LocalDateSource.goals;
                dataSourcePGS = getGoalsDate();
                break;
            case "sight":
                LocalDateSource.updateSights(TodoHelper.getInstance());
                dataSourceSight = LocalDateSource.sights;
                dataSourcePGS = getSightDate();
                break;
            default:
                break;
        }

        if(dataSourcePGS != null)
            selfPGSActivityView.showDataSourceChanged(dataSourcePGS);
        else
            selfPGSActivityView.showDataSourceChanged(new ArrayList<TaskClassify>());
    }

    @Override
    public List<TaskClassify> getGoalsDate() {
        List<TaskClassify> result = null;
        if (dataSourceGoal != null && dataSourceGoal.size() > 0) {
            result = new ArrayList<>();
            for (Goal goal : dataSourceGoal) {
                if (goal.getState().equals(TodoHelper.PGS_State.get("noComplete"))) {
                    result.add(goal);
                }
            }
        }
        return result;
    }

    @Override
    public List<TaskClassify> getSightDate() {
        List<TaskClassify> result = null;
        if (dataSourceSight != null && dataSourceSight.size() > 0) {
            result = new ArrayList<>();
            for (Sight sight : dataSourceSight) {
                result.add(sight);
            }
        }
        return result;
    }

    @Override
    public List<TaskClassify> getProjectDate() {
        List<TaskClassify> result = null;
        if (dataSourceProjects != null && dataSourceProjects.size() > 0) {
            result = new ArrayList<>();
            for (Project project : dataSourceProjects) {
                if (project.getState().equals(TodoHelper.PGS_State.get("noComplete"))) {
                    result.add(project);
                }
            }
        }
        return result;
    }

    @Override
    public void addSelfPGS(String menuName, Context context){
        Intent intent = new Intent(context, AddEditSelfpgsActivity.class);
        intent.putExtra("menuname", menuName);
        selfPGSActivityView.showAddSelfPGS(intent);
    }

    @Override
    public void CreateMenu(Menu menu) {
        MenuItem taskAdd = menu.add(0, 0, 0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public SwipeMenuCreator getSwipeMenuCreator(final String menuName){
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem EditItem = new SwipeMenuItem(
                        TodoHelper.getInstance());
                EditItem.setBackground(new ColorDrawable(Color.parseColor("#C7C6CC")));
                EditItem.setWidth(200);
                EditItem.setTitle("编辑");
                EditItem.setTitleSize(18);
                EditItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(EditItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(TodoHelper.getInstance());
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#FF2730")));
                deleteItem.setWidth(200);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);

                if (!menuName.equals("sight")) {
                    SwipeMenuItem completeItem = new SwipeMenuItem(TodoHelper.getInstance());
                    completeItem.setBackground(new ColorDrawable(Color.parseColor("#FF9700")));
                    completeItem.setWidth(200);
                    completeItem.setTitle("完成");
                    completeItem.setTitleSize(18);
                    completeItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(completeItem);
                }
            }
        };
        return creator;
    }

    @Override
    public void editPGS(String menuName, int position, Context context){
        TaskClassify taskClassify = dataSourcePGS.get(position);
        Intent intent = new Intent(context, AddEditSelfpgsActivity.class);
        intent.putExtra("menuname", menuName);
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", taskClassify);
        intent.putExtra("bundle", bundle);
        selfPGSActivityView.showEditPGS(intent);
    }

    @Override
    public void deletePGS(final String menuName, final int position, final Context context){
        AlertDialog.Builder adDel = new AlertDialog.Builder(context);
        adDel.setMessage("是否要删除?");
        adDel.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adDel.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (menuName){
                    case "project":
                        Project.deleteOne(dataSourcePGS.get(position).getId(), TodoHelper.getInstance());
                        notifyPGSDateSourceChanged(menuName, context);
                        break;
                    case "goal":
                        Goal.deleteOne(dataSourcePGS.get(position).getId(), TodoHelper.getInstance());
                        notifyPGSDateSourceChanged(menuName, context);
                        break;
                    case "sight":
                        Sight.deleteOne(dataSourcePGS.get(position).getId(), TodoHelper.getInstance());
                        notifyPGSDateSourceChanged(menuName, context);
                        break;
                    default:
                        break;
                }
            }
        });
        adDel.create();
        adDel.show();
    }

    @Override
    public void completedPGS(final String menuName, final int position, final Context context){
        AlertDialog.Builder adDel = new AlertDialog.Builder(context);
        adDel.setMessage("是否要删除?");
        adDel.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adDel.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (menuName){
                    case "project":
                        Project.completed(dataSourcePGS.get(position).getId(), TodoHelper.getInstance());
                        notifyPGSDateSourceChanged(menuName, context);
                        break;
                    case "goal":
                        Goal.completed(dataSourcePGS.get(position).getId(), TodoHelper.getInstance());
                        notifyPGSDateSourceChanged(menuName, context);
                        break;
                    default:
                        break;
                }
            }
        });
        adDel.create();
        adDel.show();
    }
}
