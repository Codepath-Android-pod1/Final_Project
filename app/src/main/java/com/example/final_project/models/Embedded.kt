package com.example.final_project.models

import com.example.final_project.unused_models.Attraction

data class Embedded(
    val attractions: List<Attraction>,
    val venues: List<Venue>
)