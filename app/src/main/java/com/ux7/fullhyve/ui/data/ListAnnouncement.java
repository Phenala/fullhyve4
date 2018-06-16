package com.ux7.fullhyve.ui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 6/3/2018.
 */

public class ListAnnouncement implements Serializable {

    public int id = 344;
    public String message = "This is a warning to the coalition of hackers attempting to breach this secure and wooden system. No remissions will be tolerated.";
    public int senderId = 2342;
    public String senderImage = "http://0.gravatar.com/avatar/c77b7988df1396d40ed4a62be4e55565?s=64&d=mm&r=g";
    public String senderName = "Watonica Paddymont Junior";
    public String sentTime = "14:32 PM";
    public List<ListReply> listReplies;
    public int replies = 14;
    public boolean sent = false;

    public ListReply getAnnouncementInReplyForm() {
        ListReply reply = new ListReply();
        reply.pAnnouncement = true;
        reply.id = id;
        reply.senderName = senderName;
        reply.senderImage = senderImage;
        reply.time = sentTime;
        reply.reply = message;

        return reply;
    }

}
