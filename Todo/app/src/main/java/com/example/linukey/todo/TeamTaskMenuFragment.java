package com.example.linukey.todo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by linukey on 12/5/16.
 */

public class TeamTaskMenuFragment extends Fragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teamtask, container, false);

        Button btnToday = (Button)view.findViewById(R.id.btnToday);
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TeamTaskActivity");
                intent.putExtra("menuname", "today");
                startActivity(intent);
            }
        });
        Button btnTomorrow = (Button)view.findViewById(R.id.btnTomorrow);
        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TeamTaskActivity");
                intent.putExtra("menuname", "tomorrow");
                startActivity(intent);
            }
        });
        Button btnNext = (Button)view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TeamTaskActivity");
                intent.putExtra("menuname", "next");
                startActivity(intent);
            }
        });
        Button btnProject = (Button)view.findViewById(R.id.btnProject);
        btnProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TeamPTActivity");
                intent.putExtra("menuname", "project");
                startActivity(intent);
            }
        });
        Button btnTeamname = (Button)view.findViewById(R.id.btnTeamname);
        btnTeamname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TeamPTActivity");
                intent.putExtra("menuname", "teamname");
                startActivity(intent);
            }
        });
        Button btnMyTeam = (Button)view.findViewById(R.id.btnMyTeam);
        btnMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.linukey.Todo.TeamPTActivity");
                intent.putExtra("menuname", "myteam");
                startActivity(intent);
            }
        });

        return view;
    }
}
