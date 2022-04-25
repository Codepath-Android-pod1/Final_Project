package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubGenre(
    val id: String,
    val name: String
) : Parcelable