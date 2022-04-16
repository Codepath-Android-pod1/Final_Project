package com.example.final_project.trash_models

import com.example.final_project.trash_models.Start
import com.example.final_project.trash_models.Status

data class Dates(
    val spanMultipleDays: Boolean,
    val start: Start,
    val status: Status,
    val timezone: String
)