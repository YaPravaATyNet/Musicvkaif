package com.example.musicvkaif74.Models;

import java.io.Serializable;

public class Group implements Serializable {
    private long id;
    private String name;
    private String status;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return name;
    }
}
