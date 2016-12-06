package com.example.linukey.todo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by linukey on 12/6/16.
 */

public class SelfPGSDialogFragment extends DialogFragment {
    private String title;
    private String content;


    ViewHolder viewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_selfpgs, container, false);
        initViewHolder(view);
        initView();
        return view;
    }

    public void initView(){
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
    }

    public void initViewHolder(View view){
        viewHolder = new ViewHolder();
        viewHolder.title = (TextView)view.findViewById(R.id.title);
        viewHolder.content = (TextView)view.findViewById(R.id.content);
    }

    public void initDate(String title, String content){
        this.title = title;
        this.content = content;
    }

    private class ViewHolder {
        public TextView title;
        public TextView content;
    }
}
