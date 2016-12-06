package com.example.linukey.todo;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

/**
 * Created by linukey on 12/6/16.
 */

public class SelfTaskDialogFragment extends DialogFragment {
    private String title;
    private String content;
    private String starttime;
    private String endtime;
    private String clocktime;
    private String project;
    private String goal;
    private String sight;

    ViewHolder viewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_selftask, container, false);
        initViewHolder(view);
        initView();
        return view;
    }

    public void initView(){
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
        viewHolder.starttime.setText(starttime);
        viewHolder.endtime.setText(endtime);
        viewHolder.clocktime.setText(clocktime);
        viewHolder.project.setText(project);
        viewHolder.goal.setText(goal);
        viewHolder.sight.setText(sight);
    }

    public void initViewHolder(View view){
        viewHolder = new ViewHolder();
        viewHolder.title = (TextView)view.findViewById(R.id.title);
        viewHolder.content = (TextView)view.findViewById(R.id.content);
        viewHolder.starttime = (TextView)view.findViewById(R.id.starttime);
        viewHolder.endtime = (TextView)view.findViewById(R.id.endtime);
        viewHolder.clocktime = (TextView)view.findViewById(R.id.clocktime);
        viewHolder.project = (TextView)view.findViewById(R.id.project);
        viewHolder.goal = (TextView)view.findViewById(R.id.goal);
        viewHolder.sight = (TextView)view.findViewById(R.id.sight);
    }

    public void initDate(String title, String content, String starttime, String endtime,
                         String clocktime, String project, String goal, String sight){
        this.title = title;
        this.content = content;
        this.starttime = starttime;
        this.endtime = endtime;
        this.clocktime = clocktime;
        this.project = project;
        this.goal = goal;
        this.sight = sight;
    }

    private class ViewHolder {
        public TextView title;
        public TextView content;
        public TextView starttime;
        public TextView endtime;
        public TextView clocktime;
        public TextView project;
        public TextView goal;
        public TextView sight;
    }
}
