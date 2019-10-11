package com.example.musicvkaif74.Models;

import java.util.List;

public class Lesson {
    private long id;
    private String date;
    private String beginTime;
    private String endTime;
    private String createdAt;
    private long filialId;
    private long roomId;
    private long classId;
    private String className = "";
    private int status;
    private String comment;
    private int maxStudents;
    private String topic;
    private String description;
    private List<Long> teacherIds;

    @Override
    public String toString() {
        return className + "\n" + date + "\n" + beginTime;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDate() {
        return date;
    }
}
