package dev.matyaqubov.instagramclone.manager.handler

import dev.matyaqubov.instagramclone.model.Post

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}