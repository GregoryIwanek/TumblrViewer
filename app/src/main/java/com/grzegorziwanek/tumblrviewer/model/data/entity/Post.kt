package com.grzegorziwanek.tumblrviewer.model.data.entity

import com.google.gson.annotations.SerializedName

data class Post(val id: String,
                val url: String,
                @SerializedName("photo-url-500")
                val photo_medium: String,
                @SerializedName("photo-url-1280")
                val photo_large: String,
                @SerializedName("gallery")
                val gallery: List<Photo>,
                val tags: List<String>,
                val type: String,
                @SerializedName("tumblelog")
                val tumbleLogInner: TumblelogInner,
                @SerializedName("photo-caption")
                val caption: String,
                @SerializedName("regular-body")
                val regularBody: String,
                val question: String,
                val answer: String)