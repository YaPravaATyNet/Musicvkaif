package com.example.musicvkaif74.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.musicvkaif74.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPreferencesHelper {
    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String CURRENT_NUMBER_KEY = "CURRENT_NUMBER_KEY";
    public static final String NUMBERS_KEY = "NUMBERS_KEY";
    public static final Type USERS_TYPE = new TypeToken<List<User>>(){}.getType();
    public static final String USERS_KEY = "USERS_KEY";
    public static final String TOKEN_KEY = "TOKEN_KEY";

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public Set<String> getNumbers() {
        Set<String> numbers = sharedPreferences.getStringSet(NUMBERS_KEY, new HashSet<String>());
        return numbers;
    }

    public void addNumber(String number) {
        Set<String> numbers = getNumbers();
        numbers.add(number);
        sharedPreferences.edit().putStringSet(NUMBERS_KEY, numbers).apply();
    }

    public String getCurrentNumber() {
        return sharedPreferences.getString(CURRENT_NUMBER_KEY, "");
    }

    public void setCurrentNumber(String number) {
        sharedPreferences.edit().putString(CURRENT_NUMBER_KEY, number).apply();
    }

    public String getToken(){
        return  sharedPreferences.getString(TOKEN_KEY, "");
    }

    public void setToken(String token) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public List<User> getUsers() {
        List<User> users = gson.fromJson(sharedPreferences.getString(USERS_KEY, ""), USERS_TYPE);
        return users == null ? new ArrayList<User>() : users;
    }

    public void addUser(User user) {
        List<User> users = getUsers();
        users.add(user);
        sharedPreferences.edit().putString(USERS_KEY, gson.toJson(users, USERS_TYPE)).apply();
    }

    public void setUsers(List<User> users) {
        sharedPreferences.edit().putString(USERS_KEY, gson.toJson(users, USERS_TYPE)).apply();
    }
}
