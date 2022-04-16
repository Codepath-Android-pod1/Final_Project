package com.example.final_project.models

import com.example.final_project.unused_models.Start
import com.example.final_project.unused_models.Status

data class Dates(
    val spanMultipleDays: Boolean,
    val start: Start,
    val status: Status,
    val timezone: String
)