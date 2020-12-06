package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
public class Medicine {
    int _id;
    String _name;
    String _day;
    String _time;
    public Medicine(){   }
    public Medicine(int id, String name, String _day, String _time){
        this._id = id;
        this._name = name;
        this._day = _day;
        this._time = _time;

    }

    public Medicine(String name, String _day, String _time){
        this._name = name;
        this._day = _day;
        this._time = _time;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getday(){
        return this._day;
    }

    public void setday(String day){
        this._day = day;
    }

    public String gettime(){
        return this._time;
    }

    public void settime(String time){
        this._time = time;
    }
}