package dev.matyaqubov.instagramclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.PrefsManager
import dev.matyaqubov.instagramclone.utils.Logger

/**
 * In SplashActivity , user can visit to SinginActivity or MainActivity
 */

class SplashActivity : BaseActivity() {
    val TAG = javaClass.simpleName.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initViews()

    }

    private fun initViews() {
        countDownTimer()
        loadFCMToken()
    }

    private fun countDownTimer() {
        object : CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (AuthManager.isSignedIn()) callMainActivity(this@SplashActivity)
                else callSignInActivity(this@SplashActivity)
            }
        }.start()
    }

    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.d(TAG, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Logger.d(TAG, token.toString())
//            PrefsManager(this).storeDeviceToken(token.toString())
        })
    }

}