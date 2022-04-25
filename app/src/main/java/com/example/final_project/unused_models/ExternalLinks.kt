package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalLinks(
    val facebook: List<Facebook>,
    val homepage: List<Homepage>,
    val lastfm: List<Lastfm>,
    val musicbrainz: List<Musicbrainz>,
    val twitter: List<Twitter>,
    val wiki: List<Wiki>,
    val youtube: List<Youtube>
) : Parcelable