package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Classification(
    val family: Boolean,
    val genre: Genre,
    val primary: Boolean,
    val segment: Segment,
    val subGenre: SubGenre,
    val subType: SubType,
    val type: Type
) : Parcelable