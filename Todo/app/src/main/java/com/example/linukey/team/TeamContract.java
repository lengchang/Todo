package com.example.linukey.team;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import com.example.linukey.data.model.Team;
import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;

import java.util.List;

/**
 * Created by linukey on 12/9/16.
 */

public class TeamContract {
    interface TeamPTView{
        void showAddTeam(Intent intent);

        void showEditTask(Intent intent);

        void showDataSourceChangedView(List<Team> datasourceTeams);
    }
    interface TeamPTPresenter{
        SwipeMenuCreator getSwipeMenuCreator();

        void notifyDataSourceTeamsChanged(Context context);

        void CreateMenu(Menu menu);
        void addTeam(String menuName, Context context);

        void editTask(int position, Context context);

        void deleteOne(int position, Context context);
    }
}
