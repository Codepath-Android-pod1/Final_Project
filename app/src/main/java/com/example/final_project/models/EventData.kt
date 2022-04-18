package com.example.final_project.models

import com.example.final_project.unused_models.LinksXXX
import com.example.final_project.unused_models.Page

data class EventData(
    val _embedded: Embedded,
    val _links: LinksXXX,
    val page: Page
)