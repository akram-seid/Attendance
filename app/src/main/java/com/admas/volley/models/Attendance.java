package com.admas.volley.models;

import java.util.ArrayList;
import java.util.List;

public class Attendance {
    List<String> preList= new ArrayList<>();
    List<String> perList= new ArrayList<>();
    List<String> seslist= new ArrayList<>();

    public List<String> getPreList() {
        return preList;
    }

    public void setPreList(List<String> preList) {
        this.preList = preList;
    }

    public List<String> getPerList() {
        return perList;
    }

    public void setPerList(List<String> perList) {
        this.perList = perList;
    }

    public List<String> getSeslist() {
        return seslist;
    }

    public void setSeslist(List<String> seslist) {
        this.seslist = seslist;
    }
}
