package dev.matyaqubov.instagramclone.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.manager.AuthHandler
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.PrefsManager
import dev.matyaqubov.instagramclone.manager.handler.DBUserHandler
import dev.matyaqubov.instagramclone.model.User
import dev.matyaqubov.instagramclone.utils.Extentions.toast

/**
 * In SignInActivity , user can login using email
 */

class SignInActivity : BaseActivity() {
    val TAG = javaClass.simpleName.toString()
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    private fun initViews() {
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        val b_signIn = findViewById<Button>(R.id.btn_signin)
        b_signIn.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty())
                firebaseSignIn(email, password)
        }
        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }
    }

    private fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.singIn(email, password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                dismissLoading()
                toast(getString(R.string.str_signin_success))
                storeDeviceTokenToUser()
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signin_failed))
            }

        })
    }

    private fun storeDeviceTokenToUser() {
        val deviceToken = PrefsManager(this).loadDeviceToken()
        var uid = AuthManager.currentUser()!!.uid
        DatabaseManager.addMyDeviceToken(uid, deviceToken, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                callMainActivity(this@SignInActivity)
            }

            override fun onError(e: Exception) {
                callMainActivity(this@SignInActivity)
            }

        })
    }

    private fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }


}