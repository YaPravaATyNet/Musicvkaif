package com.example.musicvkaif74.Models;

import java.io.Serializable;

public class Post implements Serializable {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title;
    }
}
