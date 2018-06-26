package com.ux7.fullhyve.unit;

import com.ux7.fullhyve.services.Models.Announcement;
import com.ux7.fullhyve.services.Models.TeamMessage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Admin on 6/22/2018.
 */
public class AnnouncementTest {
    private Announcement mAnnouncement;

    @Before
    public void setup(){
        mAnnouncement = new Announcement();
    }

    @Test
    public void AddReplies_Test(){
        ArrayList<TeamMessage> replies = new ArrayList<>();
        replies.add(new TeamMessage(1,"","",false,false));
        mAnnouncement.addReplies(replies);
        assertEquals(replies,mAnnouncement.replies.subList(0,1));
    }

    @Test
    public void AddReply_Test(){
        TeamMessage reply = new TeamMessage(1, "","", false, false);
        mAnnouncement.addReply(reply);
        assertEquals(reply, mAnnouncement.replies.get(0));
    }

    @Test
    public void RemoveReply_Test(){
        TeamMessage reply = new TeamMessage(0, "","", false, false);
        mAnnouncement.addReply(reply);
        mAnnouncement.removeReply(reply.getId());
        assertEquals(0, mAnnouncement.replies.size());

    }

}