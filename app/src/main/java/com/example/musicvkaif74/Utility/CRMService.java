package com.example.musicvkaif74.Utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CRMService {
    private static CRMService service;
    private static final String BASE_URL = "your crm";
    private Retrofit retrofit;

    private CRMService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(20, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static synchronized CRMService getInstance() {
        if (service == null) {
            service = new CRMService();
        }
        return service;
    }

    public synchronized CRMApi getCRMApi() {
        return retrofit.create(CRMApi.class);
    }
}
