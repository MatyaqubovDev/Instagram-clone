package dev.matyaqubov.instagramclone.network


import dev.matyaqubov.instagramclone.model.FirebaseRequest
import dev.matyaqubov.instagramclone.model.FirebaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Authorization:$ACCESS_KEY")
    @POST("send")
    fun sendNotification(@Body firebaseRequest: FirebaseRequest): Call<FirebaseResponse>


    companion object {
        const val ACCESS_KEY =
            "key=AAAAf-zCNII:APA91bHymfIa1wYifRUdYeDw0nC_jkR5elRucLzbAbT4dr0k7BauvyuCta6wGNSnAaw1-3pQMlzmsPvRskaJ5I494yODFMLCWtQejdjjXUxo1j-HFOX-rU7Dk8VlPW_vhQ4ttkSlg2Gt"
    }
}