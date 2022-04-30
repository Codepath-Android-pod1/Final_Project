package com.example.final_project.models

import android.os.Parcelable
import com.example.final_project.unused_models.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val _embedded: EmbeddedX,
    val _links: LinksXX,
    val classifications: List<ClassificationX>,
    val dates: Dates,
    val distance: Double,
    val id: String,
    val images: List<ImageX>,
    val locale: String,
    val name: String,
    val outlets: List<Outlet>,
    val promoter: Promoter,
    val promoters: List<Promoter>,
    val sales: Sales,
    val seatmap: Seatmap,
    val test: Boolean,
    val type: String,
    val units: String?,
    val url: String
) : Parcelable