package com.grzegorziwanek.tumblrviewer

import com.grzegorziwanek.tumblrviewer.model.data.entity.*

object MockRepository {

    fun mockBlog(): Blog {
        return Blog(mockTumblelog(),
            List(4) { Post("", "url", "photo_medium", "photo_large",
                List(4) { mockPhoto()}, List(4) {"tag"},  "photo", mockTumblelogInner()) })
    }

    private fun mockTumblelog(): Tumblelog {
        return Tumblelog("title", "description", "name", "cname")
    }

    private fun mockTumblelogInner(): TumblelogInner {
        return TumblelogInner("title", "description", "name", "cname",
            "avatar_small", "avatar_large", List(3) {"tag"})
    }

    private fun mockPhoto(): Photo {
        return Photo("url")
    }
}

