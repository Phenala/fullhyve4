package com.ux7.fullhyve.services.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Announcement{
    public TeamMessage mainMessage;
    public List<TeamMessage> replies;

    public Announcement() {
        replies = new ArrayList<>();
    }

    public void addReplies(List<TeamMessage> replies){
        this.replies.addAll(0,replies);
    }

    public void loadReplies(List<TeamMessage> replies){
        this.replies.addAll(replies);
    }

    public void addReply(TeamMessage reply){
        this.replies.add(0,reply);
    }

    public void removeReply(int replyId){
        for(int i=0;i<this.replies.size();i++){
            if(replies.get(i).getId()==replyId){
                replies.remove(replyId);
            }
        }
    }
}
