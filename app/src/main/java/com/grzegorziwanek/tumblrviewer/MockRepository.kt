package com.grzegorziwanek.tumblrviewer

import com.grzegorziwanek.tumblrviewer.model.data.entity.Blog
import com.grzegorziwanek.tumblrviewer.model.data.entity.Photo
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.model.data.entity.Tumblelog

object MockRepository {

    fun mockBlog(): Blog {
        return Blog(mockTumblelog(),
            List(4) { Post("", "url", List(4) { mockPhoto()}, List(4) {"tag"}) })
    }

    private fun mockTumblelog(): Tumblelog {
        return Tumblelog("title", "description", "name", "cname")
    }

    private fun mockPhoto(): Photo {
        return Photo("url")
    }
}

