package com.grzegorziwanek.tumblrviewer

import com.grzegorziwanek.tumblrviewer.model.data.entity.*
import java.util.*

object MockRepository {

    fun mockBlog(): Blog {
        return Blog(mockTumblelog(),
            List(4) { Post("", "url", "photo_medium", "photo_large",
                List(4) { mockPhoto()}, List(4) {"tag"},  "photo", mockTumblelogInner(),
                "photo caption", "regular body", "question", "answer") })
    }

    private fun mockTumblelog(): Tumblelog {
        return Tumblelog("title", "description", "name", "cname")
    }

    private fun mockTumblelogInner(): TumblelogInner {
        return TumblelogInner("title", "description", "name", "cname",
            "avatar_small"
        )
    }

    private fun mockPhoto(): Photo {
        return Photo("url")
    }

    fun mockFavouriteBlogName(): String {
        val rand = Random(System.currentTimeMillis())
        return when(rand.nextInt()%8) {
            0 -> "memecage"
            1 -> "sphor-art"
            2 -> "jen-iii"
            3 -> "catscenter"
            4 -> "the-fungeon-of-lady-lazarus"
            5 -> "water-aesthetics"
            6 -> "memehumor"
            7 -> "cerealbis"
            else -> "memecage"
        }
    }
}

