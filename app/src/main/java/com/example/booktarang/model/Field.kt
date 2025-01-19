package com.example.booktarang.model;

public class Field {
    private Integer id;
    private String name;
    private Data sport_type;
    private String open_time;
    private String close_time;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public String getType() {
//        return sport_type.getName();
//    }

    public String getOpenTime() {
        return open_time;
    }

    public String getCloseTime() {
        return close_time;
    }
}
