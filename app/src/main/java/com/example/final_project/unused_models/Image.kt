package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val fallback: Boolean,
    val height: Int,
    val ratio: String,
    val url: String,
    val width: Int
) : Parcelable