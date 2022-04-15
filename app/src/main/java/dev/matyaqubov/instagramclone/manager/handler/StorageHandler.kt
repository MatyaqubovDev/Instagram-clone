package dev.matyaqubov.instagramclone.manager.handler

interface StorageHandler {
    fun onSuccess(imgUrl:String)
    fun onError(e:Exception)
}