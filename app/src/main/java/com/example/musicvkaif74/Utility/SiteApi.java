package com.example.musicvkaif74.Utility;

import com.example.musicvkaif74.Models.Contacts;
import com.example.musicvkaif74.Models.Post;
import com.example.musicvkaif74.Models.Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SiteApi {
    @Headers("Content-Type: application/json")
    @GET("news")
    Call<List<Post>> getNews();

    @Headers("Content-Type: application/json")
    @GET("post")
    Call<Post> getPost(@Query("url") String url);

    @Headers("Content-Type: application/json")
    @GET("teachers")
    Call<Post> getTeachers();

    @Headers("Content-Type: application/json")
    @GET("contacts")
    Call<Contacts> getContacts();

    @Headers("Content-Type: application/json")
    @GET("join")
    Call<Request> join(@Query("phone") String phone, @Query("group") String group);
}
