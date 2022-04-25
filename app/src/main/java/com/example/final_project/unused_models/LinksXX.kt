package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksXX(
    val attractions: List<AttractionX>,
    val self: SelfXX,
    val venues: List<VenueX>
) : Parcelable