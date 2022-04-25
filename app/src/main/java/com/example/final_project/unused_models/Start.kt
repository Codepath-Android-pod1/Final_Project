package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Start(
    val dateTBA: Boolean,
    val dateTBD: Boolean,
    val dateTime: String?,
    val localDate: String?,
    val localTime: String?,
    val noSpecificTime: Boolean,
    val timeTBA: Boolean
) : Parcelable