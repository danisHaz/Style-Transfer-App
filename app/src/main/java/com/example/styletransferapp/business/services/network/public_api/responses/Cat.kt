package com.example.styletransferapp.business.services.network.public_api.responses

import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("url")
    val url: String,
)
