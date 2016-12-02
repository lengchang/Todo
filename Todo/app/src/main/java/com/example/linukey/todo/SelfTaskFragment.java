package com.example.linukey.todo;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linukey.BLL.SelfTaskBLL;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.DAL.LocalDateSource;
import com.example.linukey.Model.SelfTask;
import com.example.linukey.Model.SelfTaskView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by linukey on 12/1/16.
 */

public class SelfTaskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_selftask, container, false);

        Button btnToday = (Button)view.findViewById(R.id.btnToday);
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TodayActivity");
                startActivity(intent);
            }
        });

        return view;
    }
}
