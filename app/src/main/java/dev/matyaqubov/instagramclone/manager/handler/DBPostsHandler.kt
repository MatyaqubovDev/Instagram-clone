package dev.matyaqubov.instagramclone.manager.handler

import dev.matyaqubov.instagramclone.model.Post

interface DBPostsHandler {
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e: Exception)
}