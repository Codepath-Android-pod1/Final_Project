package com.example.final_project.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TMApi {
    @GET("events")
    fun getEvents(@QueryMap parameters: Map<String, String>): Call<EventData>
}