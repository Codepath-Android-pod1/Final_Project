package com.example.final_project.trash_models

data class EventX(
    val _embedded: Embedded,
    val _links: LinksXX,
    val accessibility: Accessibility,
    val ageRestrictions: AgeRestrictions,
    val classifications: List<ClassificationX>,
    val code: String,
    val dates: Dates,
    val id: String,
    val images: List<ImageXX>,
    val info: String,
    val locale: String,
    val name: String,
    val pleaseNote: String,
    val priceRanges: List<PriceRange>,
    val promoter: Promoter,
    val promoters: List<Promoter>,
    val sales: Sales,
    val seatmap: Seatmap,
    val test: Boolean,
    val ticketLimit: TicketLimit,
    val type: String,
    val url: String
)