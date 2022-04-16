package com.example.final_project.trash_models

data class Attraction(
    val _links: Links,
    val aliases: List<String>,
    val classifications: List<Classification>,
    val externalLinks: ExternalLinks,
    val id: String,
    val images: List<Image>,
    val locale: String,
    val name: String,
    val test: Boolean,
    val type: String,
    val upcomingEvents: UpcomingEvents,
    val url: String
)