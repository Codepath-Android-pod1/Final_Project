package com.example.final_project.trash_models

data class ClassificationX(
    val family: Boolean,
    val genre: GenreX,
    val primary: Boolean,
    val segment: SegmentX,
    val subGenre: SubGenreX,
    val subType: SubTypeX,
    val type: TypeX
)