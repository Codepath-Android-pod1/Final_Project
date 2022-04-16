package com.example.final_project.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/discovery/v2/events")
    fun getEvents(): Call<Events>

    @GET("/discovery/v2/events")
    fun getEventsbyLocation(@Query("latlong") latlong: String): Call<Events>

    @GET("/discovery/v2/events")
    fun getEventsbyCity(@Query("city") city: String): Call<Events>
}