package com.metlin.mavenfxmysql.People;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class User {


    private int numberunit;
    private String last;
    private String name;
    private String middle;
    private String birth;
    private String personalnumber;
    private String militaryrank;
    private String nomrspiner;
    private String nomrdate;
    private String nomrnumber;
    private String militarypost;





    public User( Integer numberunit, String last,
                 String name, String middle, String birth,
                 String personalnumber, String militaryrank,
                 String nomrspiner, String nomrdate, String nomrnumber, String militarypost) {

        this.numberunit = numberunit;
        this.last = last;
        this.name = name;
        this.middle = middle;
        this.birth = birth;
        this.personalnumber = personalnumber;
        this.militaryrank = militaryrank;
        this.nomrspiner = nomrspiner;
        this.nomrdate = nomrdate;
        this.nomrnumber = nomrnumber;
        this.militarypost = militarypost;

    }
    public User(){

    }

    public int getNumberunit() {
        return numberunit;
    }

    public void setNumberunit(int numberunit) {
        this.numberunit = numberunit;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNomrspiner() {
        return nomrspiner;
    }

    public void setNomrspiner(String nomrspiner) {
        this.nomrspiner = nomrspiner;
    }

    public String getNomrdate() {
        return nomrdate;
    }

    public void setNomrdate(String nomrdate) {
        this.nomrdate = nomrdate;
    }

    public String getNomrnumber() {
        return nomrnumber;
    }

    public void setNomrnumber(String nomrnumber) {
        this.nomrnumber = nomrnumber;
    }

    public String getMilitarypost() {
        return militarypost;
    }

    public void setMilitarypost(String militarypost) {
        this.militarypost = militarypost;
    }
}