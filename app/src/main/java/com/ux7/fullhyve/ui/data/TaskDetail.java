package com.ux7.fullhyve.ui.data;

import com.ux7.fullhyve.ui.enums.TaskStatus;

import java.io.Serializable;

/**
 * Created by hp on 06/08/18.
 */

public class TaskDetail implements Serializable{

    public String id = "3492857";
    public String name = "Rigging";
    public int number = 1;
    public String assignedOn = "14/06/2018";
    public String deadline = "18/06/2018";
    public String inProgress = "4d 7h";
    public String assigneeId = "142342";
    public String assigneeImage = "http://0.gravatar.com/avatar/c77b7988df1396d40ed4a62be4e55565?s=64&d=mm&r=g";
    public String assigneeName = "Umber Defter";
    public String assignerId = "142342";
    public String assignerImage = "http://0.gravatar.com/avatar/c77b7988df1396d40ed4a62be4e55565?s=64&d=mm&r=g";
    public String assignerName = "Tokyo Drift";
    public boolean team = true;
    public String teamId = "142342";
    public String teamImage = "http://0.gravatar.com/avatar/c77b7988df1396d40ed4a62be4e55565?s=64&d=mm&r=g";
    public String teamName = "Tokyo Drift";
    public TaskStatus status = TaskStatus.WAITING;
    public String instructions = "A hunter was on a hunt for a hun was what he hunted, hunters dont hunt hunted huns as often as hunters would like to hunt hunted huns.";
    public String results = "The hun shit himself.";

}
