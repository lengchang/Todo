package com.example.linukey.selfpgs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;
import com.example.linukey.data.model.TaskClassify;

import java.util.List;

/**
 * Created by linukey on 12/8/16.
 */

public interface SelfPGSContract {
    interface SelfPGSActivityView{
        void showDataSourceChanged(List<TaskClassify> dateSource);
        void showAddSelfPGS(Intent intent);
        void showEditPGS(Intent intent);
    }

    interface SelfPGSActivityPresenter{
        void notifyPGSDateSourceChanged(String menuName, Context context);

        List<TaskClassify> getGoalsDate();

        List<TaskClassify> getSightDate();

        List<TaskClassify> getProjectDate();

        void addSelfPGS(String menuName, Context context);

        void CreateMenu(Menu menu);

        SwipeMenuCreator getSwipeMenuCreator(String menuName);

        void editPGS(String menuName, int position, Context context);

        void deletePGS(final String menuName, final int position, Context context);

        void completedPGS(final String menuName, final int position, final Context context);
    }
}
