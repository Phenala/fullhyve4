package com.ux7.fullhyve.ui.data;

/**
 * Created by hp on 4/17/2018.
 */

public class ListMessage {

    public String id = "32453";
    public String message = "This is very good. No amount. Additionally, an analysis of the pros and cons of using this style is included.Hopefully, this architecture can prove a useful tool in the creation of actual applicationsand be developed and improved throughout time.";
    public String time = "2:45 PM";
    public boolean sent = true;

    public boolean spinner = false;

    public ListMessage setValues(String message, String time, boolean sent) {
        this.message = message;
        this.time = time;
        this.sent = sent;

        return this;
    }

    public static ListMessage getSpinnerValue() {
        ListMessage l = new ListMessage();
        l.spinner = true;
        return l;
    }

}
