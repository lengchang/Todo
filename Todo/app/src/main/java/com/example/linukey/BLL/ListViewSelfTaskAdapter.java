package com.example.linukey.BLL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linukey.Model.SelfTask;
import com.example.linukey.todo.R;

import java.util.List;

/**
 * Created by linukey on 11/17/16.
 */

public class ListViewSelfTaskAdapter extends BaseAdapter {
    Context context;
    List<SelfTask> sourceDate;

    class ViewHolder{
        ImageView image;
        TextView title;
        TextView time;
    }

    public ListViewSelfTaskAdapter(Context context, List<SelfTask> sourceDate){
        this.context = context;
        this.sourceDate = sourceDate;
    }

    @Override
    public int getCount() {
        return sourceDate.size();
    }

    @Override
    public Object getItem(int position) {
        return sourceDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View resultView;
        ViewHolder viewHolder;

        if(convertView != null)
            resultView = convertView;
        else{
            resultView = LayoutInflater.from(context).inflate(R.layout.listview_selftask, parent, false);
        }

        viewHolder = (ViewHolder)resultView.getTag();

        if(viewHolder == null){
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)resultView.findViewById(R.id.taskImage);
            viewHolder.time = (TextView)resultView.findViewById(R.id.time);
            viewHolder.title = (TextView)resultView.findViewById(R.id.title);
        }

        viewHolder.image.setImageResource(R.mipmap.deleted);
        viewHolder.title.setText(sourceDate.get(position).getTitle());
        viewHolder.time.setText(sourceDate.get(position).getStarttime() + "  " +
        sourceDate.get(position).getEndtime());

        resultView.setTag(viewHolder);

        return resultView;
    }
}
