package com.example.final_project.models

import android.os.Parcelable
import com.example.final_project.unused_models.Start
import com.example.final_project.unused_models.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dates(
    val spanMultipleDays: Boolean,
    val start: Start,
    val status: Status
) : Parcelable