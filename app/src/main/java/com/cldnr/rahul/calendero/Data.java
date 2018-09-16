package com.cldnr.rahul.calendero;

public class Data {

    public Data(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String title,desc,time,date;

    public void setDate(String date){this.date=date;}

    public String getDate(){return date;}

    public Data(String title, String desc, String time,String date) {
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.date=date;
    }
}
