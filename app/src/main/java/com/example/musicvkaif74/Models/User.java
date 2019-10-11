package com.example.musicvkaif74.Models;

import java.io.Serializable;
import java.util.List;

public class User  implements Serializable {
    private long id;
    private String name;
    private String email;
    private long phone;
    private String createdAt;
    private int balans;
    private long responsibleId;
    private long advSourceId;
    private long createSourceId;
    private List<Attribute> attributes;

    @Override
    public String toString() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPhone() {
        return phone;
    }
}
