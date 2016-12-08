package com.example.linukey.selftask;

/**
 * Created by linukey on 12/8/16.
 */

public class SelfTaskDialogPresenter implements SelfTaskContract.SelfTaskDialogPresenter {
    SelfTaskContract.SelfTaskDialogView selfTaskDialogView;

    public SelfTaskDialogPresenter(SelfTaskContract.SelfTaskDialogView view) {
        this.selfTaskDialogView = view;
    }

    @Override
    public void setData(String title, String content, String starttime, String endtime, String clocktime, String projectTitle, String goalTitle, String sightTitle) {
        selfTaskDialogView.initDate(title, content, starttime, endtime, clocktime, projectTitle, goalTitle, sightTitle);
    }
}
