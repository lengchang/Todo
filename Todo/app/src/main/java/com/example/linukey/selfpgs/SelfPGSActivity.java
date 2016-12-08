package com.example.linukey.selfpgs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.linukey.todo.Adapter.ListViewSelfPGSAdapter;
import com.example.linukey.todo.SwipeMenu.SwipeMenu;
import com.example.linukey.todo.SwipeMenu.SwipeMenuListView;
import com.example.linukey.todo.R;
import com.example.linukey.data.model.TaskClassify;

import java.util.List;

/**
 * Created by linukey on 12/4/16.
 */

public class SelfPGSActivity extends Activity implements SelfPGSContract.SelfPGSActivityView {

    SwipeMenuListView listViewPGS = null;
    String menuName = null;
    final int addSelfPGS_ResultCode = 2;
    SelfPGSContract.SelfPGSActivityPresenter selfPGSActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfpgs);

        init();
        initActionBar();
    }

    public void init() {
        Intent intent = getIntent();
        menuName = intent.getStringExtra("menuname");
        selfPGSActivityPresenter = new SelfPGSPresenter(this);
        listViewPGS = (SwipeMenuListView) findViewById(R.id.listview_selfpgs);
        listViewPGS.setMenuCreator(selfPGSActivityPresenter.getSwipeMenuCreator(menuName));
        listViewPGS.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        selfPGSActivityPresenter.editPGS(menuName, position);
                        break;
                    case 1:
                        selfPGSActivityPresenter.deletePGS(menuName, position);
                        selfPGSActivityPresenter.notifyPGSDateSourceChanged(menuName);
                        break;
                    case 2:
                        selfPGSActivityPresenter.completedPGS(menuName, position);
                        selfPGSActivityPresenter.notifyPGSDateSourceChanged(menuName);
                        break;
                }
            }
        });
        selfPGSActivityPresenter.notifyPGSDateSourceChanged(menuName);
    }

    @Override
    public void showEditPGS(Intent intent){
        startActivityForResult(intent, addSelfPGS_ResultCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == addSelfPGS_ResultCode) {
            selfPGSActivityPresenter.notifyPGSDateSourceChanged(menuName);
        }
    }

    public void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showDataSourceChanged(List<TaskClassify> dateSource){
        ListViewSelfPGSAdapter listViewSelfPGSAdapter = new ListViewSelfPGSAdapter(this, dateSource);
        listViewPGS.setAdapter(listViewSelfPGSAdapter);
    }

    @Override
    public void showAddSelfPGS(Intent intent) {
        startActivityForResult(intent, addSelfPGS_ResultCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        selfPGSActivityPresenter.CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case 0:
                selfPGSActivityPresenter.addSelfPGS(menuName);
                break;
        }
        return true;
    }
}
