package com.example.final_project.unused_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class State(
    val name: String,
    val stateCode: String
) : Parcelable