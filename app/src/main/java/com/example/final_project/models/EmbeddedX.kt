package com.example.final_project.models

import android.os.Parcelable
import com.example.final_project.unused_models.Attraction
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmbeddedX(
    val attractions: List<Attraction>,
    val venues: List<Venue>
) : Parcelable