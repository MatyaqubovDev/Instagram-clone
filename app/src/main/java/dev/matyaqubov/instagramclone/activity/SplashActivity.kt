package dev.matyaqubov.instagramclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.manager.AuthManager

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


}