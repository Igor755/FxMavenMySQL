package com.metlin.mavenfxmysql.People;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class User {


    private String name;
    private String last;
    private String middle;
    private String birth;
    private String personalnumber;
    private String militaryrank;




    public User(String name, String last, String middle, String birth, String personalnumber, String militaryrank) {

        this.name = name;
        this.last = last;
        this.middle = middle;
        this.birth = birth;
        this.personalnumber = personalnumber;
        this.militaryrank = militaryrank;
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

    public String getPersonalnumber() {
        return personalnumber;
    }

    public void setPersonalnumber(String personalnumber) {
        this.personalnumber = personalnumber;
    }

    public String getMilitaryrank() {
        return militaryrank;
    }

    public void setMilitaryrank(String militaryrank) {
        this.militaryrank = militaryrank;
    }
}