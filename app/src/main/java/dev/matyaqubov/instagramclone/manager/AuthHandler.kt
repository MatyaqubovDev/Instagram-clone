package dev.matyaqubov.instagramclone.manager

interface AuthHandler {
    fun onSuccess(uid:String)
    fun onError(exception: Exception?)
}