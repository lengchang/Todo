package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.SelfTaskContentProvider;
import com.example.linukey.DAL.UserContentProvider;
import com.example.linukey.Model.SelfTask;
import com.example.linukey.Model.SelfTaskView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linukey on 12/1/16.
 */

public class SelfTaskBLL {


    public void setTaskGroup(SelfTaskView selfTaskView) throws ParseException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-hh");
        Date today = simpleDateFormat.parse(date.getYear()+1900+"-"+date.getMonth()+"-"+date.getDate());
        Date tomorrow = getNextDay(today);
        if(today.getTime() >= simpleDateFormat.parse(selfTaskView.getStarttime()).getTime()
                && today.getTime() <= simpleDateFormat.parse(selfTaskView.getEndtime()).getTime()){
            selfTaskView.setTaskGroup(TodoHelper.TaskGroup.get("today"));
        }
        if(tomorrow.getTime() >= simpleDateFormat.parse(selfTaskView.getStarttime()).getTime()
                && tomorrow.getTime() <= simpleDateFormat.parse(selfTaskView.getEndtime()).getTime()){
            selfTaskView.setTaskGroup(TodoHelper.TaskGroup.get("tomorrow"));
        }
    }

    public Date getNextDay(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }
}
