package com.marocks.todo.model;

/**
  Created by anil on 23/8/16.
 */

public class ToDoItemUpload
{
    private String id;

    private Schedule schedule_attributes;

    private String desc;

    private String[] tag_list;

    private String schedule_name;

    private String i_cal;

    private String state;
    int priority;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Schedule getSchedule_attributes ()
    {
        return schedule_attributes;
    }

    public void setSchedule_attributes (Schedule schedule)
    {
        this.schedule_attributes = schedule;
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
        return "ClassPojo [id = "+id+", schedule = "+schedule_attributes+", desc = "+desc+", tag_list = "+tag_list+", schedule_name = "+schedule_name+", i_cal = "+i_cal+", state = "+state+"]";
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
