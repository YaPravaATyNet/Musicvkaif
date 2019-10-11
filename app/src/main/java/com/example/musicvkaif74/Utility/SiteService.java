package com.example.musicvkaif74.Utility;

import com.example.musicvkaif74.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SiteService {
    private static SiteService service;
    private static final String BASE_URL = "your server";
    private static final long CACHE_SIZE = 8 * 1024 * 1024;
    private Retrofit retrofit;

    private SiteService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(new File(MainActivity.context.getCacheDir(), "cache"), CACHE_SIZE);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(1, TimeUnit.MINUTES)
                        .build();
                Request request = chain.request();
                response.newBuilder().header("Cache-Control", cacheControl.toString()).build();
                return  response;
            }
        };

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(interceptor)
                .readTimeout(20, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static synchronized SiteService getInstance() {
        if (service == null) {
            service = new SiteService();
        }
        return service;
    }

    public SiteApi getSiteApi () {
        return retrofit.create(SiteApi.class);
    }
}
