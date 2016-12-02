package com.example.linukey.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.SelfTask;

import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class TodayActivity extends Activity{
    final Context context = this;
    ListViewSelfAdapter listViewSelfAdapter;
    List<SelfTask> datesource;
    final int addSelfTask_ResultCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        initDate();
    }

    public void initDate(){
        datesource = LocalDateSource.selfTasks;
        listViewSelfAdapter = new ListViewSelfAdapter(this, datesource);

        ListView listView = (ListView)findViewById(R.id.listview_today);
        listView.setAdapter(listViewSelfAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelfTask selfTask = datesource.get(position);

                Intent intent = new Intent("com.linukey.Todo.AddSelfActivity");
                Bundle bundle = new Bundle();
                bundle.putSerializable("date", selfTask);
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, addSelfTask_ResultCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == addSelfTask_ResultCode) {
            notifyDateSourceChanged();
        }
    }

    public void notifyDateSourceChanged(){
        LocalDateSource.updateSelfTasks(this, TodoHelper.UserId);
        datesource = LocalDateSource.selfTasks;
        listViewSelfAdapter.notifyDataSetChanged();
    }
}