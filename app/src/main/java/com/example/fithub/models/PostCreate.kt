package com.example.fithub.models

data class PostCreate(
    val title: String,
    val content: String,
    val titleImageId: Int?,
)
