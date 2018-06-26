package com.ux7.fullhyve.unit;

import com.ux7.fullhyve.services.Models.ContributorSet;
import com.ux7.fullhyve.services.Models.ContributorType;
import com.ux7.fullhyve.services.Models.Team;
import com.ux7.fullhyve.services.Models.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admin on 6/22/2018.
 */
public class ContributorSetTest {
    private ContributorSet mContributorSet;

    @Before
    public void setUp(){
        mContributorSet = new ContributorSet();
    }

    @Test
    public void Remove_Test(){
        User user = new User();
        user.setId(1);
        Team team = new Team(1,"","","", 34);
        Object[] objs = new Object[]{user, team};
        mContributorSet.add(objs);
        mContributorSet.remove(1, ContributorType.Individual);
        mContributorSet.remove(1,ContributorType.Team);
        assertEquals(0, mContributorSet.individuals.size());
        assertEquals(0, mContributorSet.individuals.size());
    }

    @Test
    public void Add_Test(){
        User user = new User();
        user.setId(1);
        Team team = new Team(1,"","","", 34);
        Object[] objs = new Object[]{user, team};
        mContributorSet.add(objs);
        assertEquals(user, mContributorSet.individuals.get(0));
        assertEquals(team, mContributorSet.teams.get(0));
    }

}