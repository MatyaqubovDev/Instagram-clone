package dev.matyaqubov.instagramclone.manager.handler

import dev.matyaqubov.instagramclone.model.User

interface DBUsersHandler {
    fun onSuccess(users: ArrayList<User>)
    fun onError(e: Exception)
}