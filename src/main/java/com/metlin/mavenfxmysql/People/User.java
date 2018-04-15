package com.metlin.mavenfxmysql.People;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String last;
    private String middle;
    private Date birth;

    public User(int id, String name, String last, String middle, Date birth) {
        this.id = id;
        this.name = name;
        this.last = last;
        this.middle = middle;
        this.birth = birth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

}