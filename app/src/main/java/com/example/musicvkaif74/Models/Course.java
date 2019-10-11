package com.example.musicvkaif74.Models;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable {
    private long id;
    private String name;
    private String shortDescription;
    private String siteUrl;
    private String description;
    private String createdAt;
    private String courseType;
    private List<Group> classes;

    @Override
    public String toString() {
        if (shortDescription != null) {
            return name + "\n" + shortDescription;
        }
        return name;
    }

    public List<Group> getClasses() {
        return classes;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
