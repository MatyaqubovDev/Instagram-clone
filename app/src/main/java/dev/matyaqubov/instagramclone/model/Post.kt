package dev.matyaqubov.instagramclone.model

class Post {
    var caption: String = ""
    var image: String = ""

    constructor(image: String) {
        this.image = image
    }
}