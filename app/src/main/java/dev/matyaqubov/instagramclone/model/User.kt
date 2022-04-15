package dev.matyaqubov.instagramclone.model

class User {
    var uid:String=""
    var fullname:String=""
    var email:String=""
    var password=""
    var userImg=""

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


}