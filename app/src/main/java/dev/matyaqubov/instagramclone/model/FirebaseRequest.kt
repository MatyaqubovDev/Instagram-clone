package dev.matyaqubov.instagramclone.model

data class FirebaseRequest(
    val notification: Notification,
    val registration_ids: List<String>
)

data class Notification(
    val body: String,
    val title: String
)