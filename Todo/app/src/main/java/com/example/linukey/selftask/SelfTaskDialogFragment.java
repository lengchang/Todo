package com.example.linukey.selftask;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import com.example.linukey.todo.R;
import com.example.linukey.util.ViewHolder;

/**
 * Created by linukey on 12/6/16.
 */

public class SelfTaskDialogFragment extends DialogFragment implements SelfTaskContract.SelfTaskDialogView {
    private String title;
    private String content;
    private String starttime;
    private String endtime;
    private String clocktime;
    private String projectTitle;
    private String goalTitle;
    private String sightTitle;

    SelfTaskContract.SelfTaskDialogPresenter presenter = new SelfTaskDialogPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_selftask, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view){
        TextView title = (TextView) ViewHolder.get(view, R.id.title);
        title.setText(this.title);
        TextView content = (TextView) ViewHolder.get(view, R.id.content);
        content.setText(this.content);
        TextView starttime = (TextView) ViewHolder.get(view, R.id.starttime);
        starttime.setText(this.starttime);
        TextView endtime = (TextView)ViewHolder.get(view, R.id.endtime);
        endtime.setText(this.endtime);
        TextView clocktime = (TextView)ViewHolder.get(view, R.id.clocktime);
        clocktime.setText(this.clocktime);
        TextView project = (TextView)ViewHolder.get(view, R.id.project);
        project.setText(this.projectTitle);
        TextView goal = (TextView)ViewHolder.get(view, R.id.goal);
        goal.setText(this.goalTitle);
        TextView sight = (TextView) ViewHolder.get(view, R.id.sight);
        sight.setText(this.sightTitle);
    }

    @Override
    public void initDate(String title, String content, String starttime, String endtime,
                         String clocktime, String projectTitle, String goalTitle, String sightTitle){
        if(title != null)
            this.title = title;
        if(content != null)
            this.content = content;
        if(starttime != null)
            this.starttime = starttime;
        if(endtime != null)
            this.endtime = endtime;
        if(clocktime != null)
            this.clocktime = clocktime;
        if(projectTitle != null)
            this.projectTitle = projectTitle;
        if(goalTitle != null)
            this.goalTitle = goalTitle;
        if(sightTitle != null)
            this.sightTitle = sightTitle;
    }
}
