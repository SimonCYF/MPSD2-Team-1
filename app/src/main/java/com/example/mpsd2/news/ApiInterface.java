package com.example.mpsd2.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<GetNews> getNews(
            @Query("country")String country,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey

    );

    @GET("top-headlines")
    Call<GetNews> getCategoryNews(
            @Query("country")String country,
            @Query("category")String category,
            @Query("pageSize")int pageSize,
            @Query("apiKey")String apiKey

    );
}
