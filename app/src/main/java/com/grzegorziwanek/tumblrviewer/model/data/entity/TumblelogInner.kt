package com.grzegorziwanek.tumblrviewer.model.data.entity

import com.google.gson.annotations.SerializedName

class TumblelogInner(title: String,
                     description: String,
                     name: String,
                     cname: String,
                     @SerializedName("avatar_url_128")
                     val avatarSmall: String) : Tumblelog(title, description, name, cname)