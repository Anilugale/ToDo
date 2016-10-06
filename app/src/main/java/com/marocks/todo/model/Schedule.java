package com.marocks.todo.model;

/**
   Created by anil on 20/9/16.
 */

public class Schedule
{
    private String id;

    /*private String day_of_week;*/

    private String time;

    private String count;

    private String rule;

    private String[] day;

    private String[] upcoming_10;

    private String date;

    private String[] past_10;


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

/*    public String getDay_of_week ()
    {
        return day_of_week;
    }

    public void setDay_of_week (String day_of_week)
    {
        this.day_of_week = day_of_week;
    }*/

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getRule ()
    {
        return rule;
    }

    public void setRule (String rule)
    {
        this.rule = rule;
    }

    public String[] getDay ()
    {
        return day;
    }

    public void setDay (String[] day)
    {
        this.day = day;
    }

    public String[] getUpcoming_10 ()
    {
        return upcoming_10;
    }

    public void setUpcoming_10 (String[] upcoming_10)
    {
        this.upcoming_10 = upcoming_10;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String[] getPast_10 ()
    {
        return past_10;
    }

    public void setPast_10 (String[] past_10)
    {
        this.past_10 = past_10;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", day_of_week = , time = "+time+", count = "+count+", rule = "+rule+", day = "+day+", upcoming_10 = "+upcoming_10+", date = "+date+", past_10 = "+past_10+", until = "+""+"]";
    }
}