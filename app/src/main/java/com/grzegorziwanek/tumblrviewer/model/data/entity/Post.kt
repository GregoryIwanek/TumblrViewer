package com.grzegorziwanek.tumblrviewer.model.data.entity

data class Post(val id: String,
                val url: String,
                val photos: List<Photo>,
                val tags: List<String>)