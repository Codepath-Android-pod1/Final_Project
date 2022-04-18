package com.example.final_project.models

import com.example.final_project.unused_models.*

data class Venue(
    val _links: LinksX,
    val address: Address,
    val city: City,
    val country: Country,
    val distance: Double,
    val dmas: List<Dma>,
    val id: String,
    val locale: String,
    val location: Location,
    val name: String,
    val postalCode: String,
    val state: State,
    val test: Boolean,
    val timezone: String,
    val type: String,
    val units: String,
    val upcomingEvents: UpcomingEventsX
)