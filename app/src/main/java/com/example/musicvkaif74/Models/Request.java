package com.example.musicvkaif74.Models;

public class Request {
    private long userId;
    private long classId;
    private long statusId;
    private String comment = "Новая необработанная заявка";

    public Request(long userId, long classId, long statusId) {
        this.userId = userId;
        this.classId = classId;
        this.statusId = statusId;
    }

    @Override
    public String
    toString() {
        return "Request{" +
                "userId=" + userId +
                ", classId=" + classId +
                ", statusId=" + statusId +
                '}';
    }
}
