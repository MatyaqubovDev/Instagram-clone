package dev.matyaqubov.instagramclone.manager.handler

import java.lang.Exception

interface DBFollowHandler {
    fun onSuccess(isFollowed: Boolean)
    fun onError(e: Exception)
}