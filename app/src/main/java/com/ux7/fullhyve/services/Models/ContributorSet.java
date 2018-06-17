package com.ux7.fullhyve.services.Models;

import java.io.Serializable;
import java.util.ArrayList;


public class ContributorSet implements Serializable{
    public ArrayList<User> individuals;
    public ArrayList<Team> teams;

    public void remove(int id, ContributorType type){
        if(type.equals(ContributorType.Individual)){
            for (int i = 0; i < individuals.size(); i++) {
                if(individuals.get(i).getId() == id){
                    individuals.remove(i);
                }
            }
        } else if(type.equals(ContributorType.Team)){
            for (int i = 0; i < teams.size(); i++) {
                if(teams.get(i).id == id){
                    teams.remove(i);
                }
            }
        }
    }

    public void add(Object[] contributors){
        for (Object contributor: contributors) {
            if(contributor instanceof User){
                individuals.add((User)contributor);
            } else if (contributor instanceof Team){
                teams.add((Team)contributor);
            }
        }
    }
}
