package dev.matyaqubov.instagramclone.model

class User {
    var uid: String = ""
    var fullname: String = ""
    var email: String = ""
    var password = ""
    var userImg = ""

    var device_id = ""
    var device_type = "A"
    var device_tokens: ArrayList<String> = ArrayList()


    var isFollowed: Boolean = false

    constructor(fullname: String, email: String) {
        this.fullname = fullname
        this.email = email
    }

    constructor(fullname: String, email: String, image: String) {
        this.fullname = fullname
        this.email = email
        this.userImg = image
    }


    constructor(fullname: String, email: String, password: String, image: String) {
        this.fullname = fullname
        this.email = email
        this.password = password
        this.userImg = image
    }

    constructor(fullname: String, email: String, device_tokens: ArrayList<String>) {
        this.fullname = fullname
        this.email = email
        this.device_tokens = device_tokens
    }


}