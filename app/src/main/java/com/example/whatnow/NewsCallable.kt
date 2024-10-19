package com.example.whatnow


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {

    @GET("/v2/top-headlines?apiKey=3be5b491ae3d4dca8522454d878c8b89&pageSize=30")
    fun getNews(@Query("category") c: String, @Query("country") code: String): Call<News>



}