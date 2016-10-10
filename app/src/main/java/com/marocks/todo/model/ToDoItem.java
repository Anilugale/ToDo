package com.marocks.todo.model;

import com.marocks.todo.Utile;

import java.util.Date;

/**
  Created by anil on 23/8/16.
 */

public class ToDoItem implements Comparable<ToDoItem>
{
    private String id;

    private Schedule schedule;

    private String desc;

    private String[] tag_list;

    private String schedule_name;

    private String i_cal;

    private String state;

    int priority;

    long miliSecound;
    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Schedule getSchedule()
    {
        return schedule;
    }

    public void setSchedule(Schedule schedule)
    {
        this.schedule = schedule;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc (String desc)
    {
        this.desc = desc;
    }

    public String[] getTag_list ()
    {
        return tag_list;
    }

    public void setTag_list (String[] tag_list)
    {
        this.tag_list = tag_list;
    }

    public String getSchedule_name ()
    {
        return schedule_name;
    }

    public void setSchedule_name (String schedule_name)
    {
        this.schedule_name = schedule_name;
    }

    public String getI_cal ()
    {
        return i_cal;
    }

    public void setI_cal (String i_cal)
    {
        this.i_cal = i_cal;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", schedule = "+schedule+", desc = "+desc+", tag_list = "+tag_list+", schedule_name = "+schedule_name+", i_cal = "+i_cal+", state = "+state+"]";
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void calTime() {
        miliSecound = Utile.getDateParse(schedule.getDate(),schedule.getTime()).getTime();
    }

    @Override
    public int compareTo(ToDoItem o) {
        return miliSecound>o.miliSecound?0:-1;
    }
}
