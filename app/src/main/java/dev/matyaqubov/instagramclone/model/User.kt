package dev.matyaqubov.instagramclone.model

class User {
    var fullname:String=""
    var email:String=""

    constructor(fullname: String, email: String) {
        this.fullname = fullname
        this.email = email
    }
}