package com.example.final_project.models

import com.example.final_project.trash_models.EventX
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/discovery/v2/events")
    fun getEvents(): Call<MutableList<EventX>>

    @GET("/discovery/v2/events")
    fun getEventsbyLocation(@Query("latlong") latlong: String): Call<MutableList<EventX>>

    @GET("/discovery/v2/events")
    fun getEventsbyCity(@Query("city") city: String): Call<MutableList<EventX>>
}