package com.metlin.mavenfxmysql.People;

import java.util.Date;

public class User {


    private String name;
    private String last;
    private String middle;
    private String birth;



    public User( String name, String last, String middle, String birth) {

        this.name = name;
        this.last = last;
        this.middle = middle;
        this.birth = birth;
    }
    public User(){

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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

}