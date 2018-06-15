package com.ux7.fullhyve.services.Models;

import java.io.Serializable;
import java.util.List;

public class Announcement implements Serializable{
    public TeamMessage mainMessage;
    public List<TeamMessage> replies;
}
