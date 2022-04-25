package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksXXX(
    val first: First,
    val last: Last,
    val next: Next,
    val self: SelfXXX
) : Parcelable