package com.example.musicvkaif74.Utility;

import com.example.musicvkaif74.Models.ApiKey;
import com.example.musicvkaif74.Models.Course;
import com.example.musicvkaif74.Models.Group;
import com.example.musicvkaif74.Models.Request;
import com.example.musicvkaif74.Responses.JoinResponse;
import com.example.musicvkaif74.Responses.LessonResponse;
import com.example.musicvkaif74.Responses.TokenRemoveResponse;
import com.example.musicvkaif74.Responses.TokenResponse;
import com.example.musicvkaif74.Responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CRMApi {
    @Headers("Content-Type: application/json")
    @POST("v1/company/auth/getToken")
    Call<TokenResponse> getToken(@Body ApiKey apiKey);

    @Headers("Content-Type: application/json")
    @POST("v1/company/auth/revokeToken")
    Call<TokenRemoveResponse> removeToken(@Header("x-access-token") String token);

    @Headers("Content-Type: application/json")
    @GET("v1/company/users")
    Call<UserResponse> getUsers(@Header("x-access-token") String token, @Query("phone") String number);

    @Headers("Content-Type: application/json")
    @GET("v1/company/courses")
    Call<List<Course>> getCourses(@Header("x-access-token") String token, @Query("includeClasses") boolean check);

    @Headers("Content-Type: application/json")
    @GET("v1/company/classes")
    Call<List<Group>> getGroups(@Header("x-access-token") String token);

    @Headers("Content-Type: application/json")
    @GET("v1/company/lessons")
    Call<LessonResponse> getLessons(@Header("x-access-token") String token, @Query("userId") long id, @Query("date") List<String> date);

    @Headers("Content-Type: application/json")
    @POST("v1/company/joins")
    Call<Request> join(@Header("x-access-token") String token, @Body Request request);

    @Headers("Content-Type: application/json")
    @GET("v1/company/joins")
    Call<JoinResponse> getJoin(@Header("x-access-token") String token, @Query("userId") long id);
}


