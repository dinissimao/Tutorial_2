package com.example.imagegalleryapp.model

data class ImageItem(
    val id: String,
    val url: String,
    val isFavorite: Boolean = false
)
