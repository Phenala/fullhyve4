package com.ux7.fullhyve.ui.data;

import java.io.Serializable;

/**
 * Created by hp on 6/3/2018.
 */

public class ListMember implements Serializable {

    public int id = 353425;
    public String image = "http://0.gravatar.com/avatar/c77b7988df1396d40ed4a62be4e55565?s=64&d=mm&r=g";
    public String name = "Sid Meier";
    public boolean leader = true;
    public boolean team = false;
    public UserDetail userDetail;
    public TeamDetail teamDetail;
}
