package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassificationX(
    val family: Boolean,
    val genre: GenreX,
    val primary: Boolean,
    val segment: SegmentX,
    val subGenre: SubGenreX
) : Parcelable