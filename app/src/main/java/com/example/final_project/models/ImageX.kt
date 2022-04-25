package com.example.final_project.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageX(
    val fallback: Boolean,
    val height: Int,
    val ratio: String,
    val url: String,
    val width: Int
) : Parcelable