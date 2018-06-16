package com.ux7.fullhyve.services.Models;


import com.ux7.fullhyve.services.Utility.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTeam extends Team{
    public ArrayList<Announcement> announcements = new ArrayList<>();
    public User leader;
    public HashMap<Integer,User> members = new HashMap<>();
    public int unseenAnnounements;
    public HashMap<Integer, Project> projects = new HashMap<>();

    public MyTeam(int id, String name, String image, String description, int memberCount) {
        super(id, name, image, description, memberCount);
        announcements = new ArrayList<>();
        members = new HashMap<>();
        projects = new HashMap<>();
    }

    public void updateTeamProfile(Team team){
        this.name = team.name;
        this.focus = team.focus;
        this.description = team.description;
        this.image = team.image;
    }

    public Announcement getAnnouncement(int annId){
        for(int i=0;i<announcements.size();i++){
            if(announcements.get(i).mainMessage.getId()==annId){
                return announcements.remove(i);
            }
        }
        return null;
    }

    public List<Announcement> getAnnouncements(int offset, int limit){
        if(announcements != null){
            return Util.sliceArray(announcements, offset, limit);
        }
        return null;
    }

    public void loadAnnouncements(List<Announcement> announcements){
        this.announcements.addAll(announcements);
    }

    public void addNewAnnouncements(List<Announcement> announcements){
        this.announcements.addAll(0,announcements);
    }

    public void addAnnouncement(Announcement announcement){
        this.announcements.add(0,announcement);
    }

    public void removeAnnouncement(int annId){
        for(int i=0;i<announcements.size();i++){
            if(announcements.get(i).mainMessage.getId()==annId){
                announcements.remove(i);
                return;
            }
        }
    }

    public void editAnnouncement(int annId, String newAnnouncement){
        for(Announcement announcement:announcements){
            if(announcement.mainMessage.getId()==annId){
                announcement.mainMessage.setMessage(newAnnouncement);
                return;
            }
        }
    }

    public void addProjects(List<Project> projects){
        for(Project project:projects){
            this.projects.put(project.id, project);
        }
    }

    public List<Project> getProjects(int offset, int limit){
        if(projects != null){
            return Util.sliceArray(new ArrayList<>(projects.values()), offset, limit);
        }
        return null;
    }

    public void addMembers(List<User> users){
        if(members==null){
            members = new HashMap<>();
        }

        for(User user:users){
            this.members.put(user.getId(), user);
        }
    }

    public void removeMember(int[] memberIds){
        for(int memberId:memberIds){
            if(this.members !=null && this.members.containsKey(memberId)){
                this.members.remove(memberId);
            }
        }

    }

    public List<User> getMembers(int offset, int limit){
        if(members != null){
            return Util.sliceArray(new ArrayList<>(members.values()),offset, limit);
        }
        return null;
    }

}
