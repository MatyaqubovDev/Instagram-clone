package dev.matyaqubov.instagramclone.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.matyaqubov.instagramclone.utils.Logger


class FCMService : FirebaseMessagingService() {
    val TAG = javaClass.simpleName.toString()
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.i(TAG, "Refreshing token ::$token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Logger.i(TAG,"onMessageReceived :: $message")
    }
}