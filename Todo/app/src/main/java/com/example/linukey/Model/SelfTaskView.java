package com.example.linukey.Model;

/**
 * Created by linukey on 11/29/16.
 */

public class SelfTaskView extends SelfTask {
    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    private String taskGroup;

    public SelfTaskView(String title, String content, String starttime, String endtime,
                        String clocktime, String projectId, String goalId, String sightId,
                        String userId, String state, String isdelete, String isTmp) {
    }
}
