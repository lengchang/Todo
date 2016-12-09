package com.example.linukey.teampt;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import com.example.linukey.todo.SwipeMenu.SwipeMenuCreator;

/**
 * Created by linukey on 12/9/16.
 */

public class TeamPTContract {
    interface TeamPTView{
        void showAddTeamPTA(Intent intent);
    }
    interface TeamPTPresenter{
        SwipeMenuCreator getSwipeMenuCreator();
        void CreateMenu(Menu menu);
        void addTeamPTA(String menuName, Context context);
    }
}
