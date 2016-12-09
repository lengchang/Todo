package com.example.linukey.todo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.linukey.data.model.TeamTask;

import java.util.List;

/**
 * Created by linukey on 12/9/16.
 */

public class ListViewTeamTaskAdapter extends BaseAdapter {
    final Context context;
    List<TeamTask> datasource = null;

    public ListViewTeamTaskAdapter(Context context, List<TeamTask> teamTasks){
        this.context = context;
        this.datasource = teamTasks;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
