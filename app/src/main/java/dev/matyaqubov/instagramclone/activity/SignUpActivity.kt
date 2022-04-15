package dev.matyaqubov.instagramclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.manager.AuthHandler
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.handler.DBUserHandler
import dev.matyaqubov.instagramclone.model.User
import dev.matyaqubov.instagramclone.utils.Extentions.toast

/**
 * In SignUpActivity, user can signup using fullname , email,password
 */

class SignUpActivity : BaseActivity() {
    val TAG = javaClass.simpleName.toString()
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    lateinit var et_cpassword: EditText
    lateinit var et_fullname: EditText
    lateinit var tv_signin: TextView
    lateinit var btn_signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_cpassword)
        tv_signin = findViewById(R.id.tv_signin)
        btn_signup = findViewById(R.id.btn_signup)

        tv_signin.setOnClickListener { finish() }
        btn_signup.setOnClickListener {
            val fullname = et_fullname.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty() && password == et_cpassword.text.toString()
                    .trim()
            ) {
                val user = User(fullname, email, password, "")
                firebaseSignUp(user)
            }

        }
    }

    private fun firebaseSignUp(user: User) {
        showLoading(this)
        AuthManager.singUp(user.email, user.password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                user.uid = uid
                storeUserDB(user)
                toast(getString(R.string.str_signup_success))
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signup_failed))
            }

        })

    }

    private fun storeUserDB(user: User) {
        DatabaseManager.storeUser(user, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                dismissLoading()
                callMainActivity(context)
            }

            override fun onError(e: Exception) {

            }

        })

    }
}