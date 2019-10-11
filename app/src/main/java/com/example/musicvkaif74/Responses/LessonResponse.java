package com.example.musicvkaif74.Responses;

import com.example.musicvkaif74.Models.Lesson;
import com.example.musicvkaif74.Models.Stats;

import java.util.List;

public class LessonResponse {
    private List<Lesson> lessons;
    private Stats stats;

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return "LessonResponse{" +
                "lessons=" + lessons +
                '}';
    }
}
