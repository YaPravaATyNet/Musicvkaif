package com.example.musicvkaif74.Responses;

import com.example.musicvkaif74.Models.Stats;
import com.example.musicvkaif74.Models.User;

import java.util.List;

public class UserResponse {
    private List<User> users;
    private Stats stats;

    public List<User> getUsers() {
        return users;
    }
}
