package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpcomingEvents(
    val _filtered: Int,
    val _total: Int,
    val ticketmaster: Int,
    val tmr: Int
) : Parcelable