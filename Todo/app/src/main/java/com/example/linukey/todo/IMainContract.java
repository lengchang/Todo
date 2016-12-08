package com.example.linukey.todo;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by linukey on 12/8/16.
 */

public interface IMainContract {
    interface View{
        void addSelfTask(Intent intent);
        void changeIcon();
    }
    interface Presenter{
        void CreateMenu(Menu menu);
        boolean MenuChoice(MenuItem item);
        void addSelfTask();
    }
}
