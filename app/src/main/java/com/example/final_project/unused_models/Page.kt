package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Page(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
) : Parcelable