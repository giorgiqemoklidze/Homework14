package com.example.homework14

import com.google.gson.annotations.SerializedName

data class NewsData(
    val id: Int = 0,
    val descriptionEN: String,
    val descriptionKA: String,
    val descriptionRU: String,
    val titleEN: String,
    val titleKA: String,
    val titleRU: String,

    @SerializedName("created_at")
    val createdAt: Long = 0,

    @SerializedName("updated_at")
    val updatedAt: Long = 0,
    val cover: String,
    var isLast: Boolean = false,
)