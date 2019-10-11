package com.example.musicvkaif74.Models;

import java.util.List;

public class Contacts {
    private String address;
    private List<String> phones;
    private String site;
    private String vk;
    private String instagram;

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < phones.size() - 1; i++) {
            res.append(phones.get(i)).append(", ");
        }
        res.append(phones.get(phones.size() - 1));
        return res.toString();
    }

    public String getSite() {
        return site;
    }

    public String getVk() {
        return vk;
    }

    public String getInstagram() {
        return instagram;
    }
}
