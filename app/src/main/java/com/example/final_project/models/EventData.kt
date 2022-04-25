package com.example.final_project.models

import android.os.Parcelable
import com.example.final_project.unused_models.LinksXXX
import com.example.final_project.unused_models.Page
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventData(
    val _embedded: Embedded,
    val _links: LinksXXX,
    val page: Page
) : Parcelable