package com.example.linukey.todo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.linukey.BLL.ListViewSelfTaskAdapter;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.SelfTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class SelfMenuTaskActivity extends Activity{
    final Context context = this;
    List<SelfTask> datesourceTask;
    ListView listViewTask;
    String menuName = null;
    final int addSelfTask_ResultCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfmenu_task);

        try {
            initDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initActionBar();
    }

    public void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initDate() throws ParseException {
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menuname");
        listViewTask = (ListView)findViewById(R.id.listview_selfmenu_task);

        switch (menuName){
            case "today":
                datesourceTask = getTodayDate();
                break;
            case "tomorrow":
                datesourceTask = getTomorrowDate();
                break;
            case "next":
                datesourceTask = getNextDate();
                break;
            case "box":
                datesourceTask = getBoxDate();
                break;
            default:
                break;
        }

        if(datesourceTask != null){
            ListViewSelfTaskAdapter lva = new ListViewSelfTaskAdapter(this, datesourceTask);
            listViewTask.setAdapter(lva);
            listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SelfTask selfTask = datesourceTask.get(position);

                    Intent intent = new Intent("com.linukey.Todo.AddSelfTaskActivity");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("date", selfTask);
                    intent.putExtra("bundle", bundle);

                    startActivityForResult(intent, addSelfTask_ResultCode);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == addSelfTask_ResultCode) {
            try {
                notifyTaskDateSourceChanged(menuName);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyTaskDateSourceChanged(String menuName) throws ParseException {
        ListViewSelfTaskAdapter lva;

        switch (menuName) {
            case "today":
                datesourceTask = getTodayDate();
                break;
            case "tomorrow":
                datesourceTask = getTomorrowDate();
                break;
            case "next":
                datesourceTask = getNextDate();
                break;
            case "box":
                datesourceTask = getBoxDate();
                break;
            default:
                break;
        }

        if(datesourceTask != null){
            lva = new ListViewSelfTaskAdapter(this, datesourceTask);
            listViewTask.setAdapter(lva);
        }
    }

    private List<SelfTask> getTodayDate() throws ParseException {
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() >0){
            result = new ArrayList<>();
            Date today = getDateToday();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrNotUser(selfTask) &&
                        today.getTime() >= sdt.parse(selfTask.getStarttime()).getTime()
                        && today.getTime() <= sdt.parse(selfTask.getEndtime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private List<SelfTask> getTomorrowDate() throws ParseException{
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() > 0){
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrNotUser(selfTask) &&
                        tomorrow.getTime() >= sdt.parse(selfTask.getStarttime()).getTime()
                        && tomorrow.getTime() <= sdt.parse(selfTask.getEndtime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private List<SelfTask> getNextDate() throws ParseException{
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() >0){
            result = new ArrayList<>();
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date tomorrow = getNextDay(getDateToday());
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(!isBoxTaskOrOvertimeOrCompleteOrNotUser(selfTask) &&
                        tomorrow.getTime() < sdt.parse(selfTask.getStarttime()).getTime()){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private List<SelfTask> getBoxDate() throws ParseException{
        List<SelfTask> result = null;
        if(LocalDateSource.selfTasks != null && LocalDateSource.selfTasks.size() > 0){
            result = new ArrayList<>();
            for(SelfTask selfTask : LocalDateSource.selfTasks){
                if(Integer.parseInt(selfTask.getIsTmp()) == 1){
                    result.add(selfTask);
                }
            }
        }
        return result;
    }

    private boolean isBoxTaskOrOvertimeOrCompleteOrNotUser(SelfTask selfTask){
        if(Integer.parseInt(selfTask.getIsTmp()) == 1)
            return true;
        if(!selfTask.getState().equals(TodoHelper.TaskState.get("noComplete")))
            return true;
        if(!selfTask.getUserId().equals(TodoHelper.UserId))
            return true;

        return false;
    }

    private Date getDateToday() throws ParseException{
        Date date = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdt.parse(date.getYear()+1900+"-"
                + (date.getMonth()+1) + "-" + date.getDate());
        return today;
    }

    public Date getNextDay(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }

    private void addSelfTask(){
        Intent addSelfTask = new Intent("com.linukey.Todo.AddSelfTaskActivity");
        startActivityForResult(addSelfTask, addSelfTask_ResultCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case 0:
                addSelfTask();
                break;
        }
        return true;
    }

    private void CreateMenu(Menu menu){
        MenuItem taskAdd = menu.add(0,0,0, "添加任务");
        taskAdd.setIcon(R.mipmap.add);
        taskAdd.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
}