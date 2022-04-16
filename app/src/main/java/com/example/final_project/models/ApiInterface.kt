package com.example.final_project.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://stackoverflow.com/questions/70556055/how-to-convert-geohash-to-latitude-and-longitude-in-kotlin
interface ApiInterface {
    @GET("/discovery/v2/events")
    fun getEvents(
        @Query("city") city: String,
        @Query("keyword") keyword: String,
        @Query("geoPoint") geoHash: String,
    ): Call<Events>
}