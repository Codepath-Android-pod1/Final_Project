package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Outlet(
    val type: String,
    val url: String
) : Parcelable