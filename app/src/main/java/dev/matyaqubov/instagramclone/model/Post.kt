package dev.matyaqubov.instagramclone.model

import java.text.SimpleDateFormat
import java.util.*

class Post {
    var id: String = ""
    var caption: String = ""
    var postImg: String = ""
    var currentDate: String = ""

    var uid: String = ""
    var fullname: String = ""
    var userImg: String = ""

    constructor(caption: String, postImg: String) {
        this.caption = caption
        this.postImg = postImg

    }

    constructor(id: String, caption: String, postImg: String) {
        this.id = id
        this.caption = caption
        this.postImg = postImg
    }

    constructor()


    fun currrentTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        return sdf.format(Date())
    }
}