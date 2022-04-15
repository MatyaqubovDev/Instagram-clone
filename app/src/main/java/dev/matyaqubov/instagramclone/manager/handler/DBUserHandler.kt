package dev.matyaqubov.instagramclone.manager.handler

import dev.matyaqubov.instagramclone.model.User

interface DBUserHandler {
    fun onSuccess(user: User?=null)
    fun onError(e:Exception)
}