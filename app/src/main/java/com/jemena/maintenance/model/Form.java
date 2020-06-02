package com.jemena.maintenance.model;

public class Form {
    private String name;
    private String desc;
    private long id;

    public Form(String name, String description, int id) {
        this.name = name;
        this.desc = description;
        this.id = id;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return desc;
    }

    long getId() {
        return id;
    }
}
