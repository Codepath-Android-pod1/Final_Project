package com.example.final_project.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Embedded(
    val events: List<Event>
) : Parcelable