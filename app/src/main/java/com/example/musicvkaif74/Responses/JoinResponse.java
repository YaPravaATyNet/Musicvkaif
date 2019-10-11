package com.example.musicvkaif74.Responses;

import com.example.musicvkaif74.Models.Request;

import java.util.List;

public class JoinResponse {
    List<Request> joins;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Request request : joins) {
            string.append(request.toString()).append("\n");
        }
        return string.toString();
    }
}
