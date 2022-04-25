package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Public(
    val endDateTime: String,
    val startDateTime: String,
    val startTBA: Boolean,
    val startTBD: Boolean
) : Parcelable