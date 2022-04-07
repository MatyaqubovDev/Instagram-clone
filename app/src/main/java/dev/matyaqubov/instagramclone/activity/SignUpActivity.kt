package dev.matyaqubov.instagramclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dev.matyaqubov.instagramclone.R

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
        et_fullname=findViewById(R.id.et_fullname)
        et_email=findViewById(R.id.et_email)
        et_password=findViewById(R.id.et_password)
        et_cpassword=findViewById(R.id.et_cpassword)
        tv_signin=findViewById(R.id.tv_signin)
        btn_signup=findViewById(R.id.btn_signup)

        tv_signin.setOnClickListener { finish() }
        btn_signup.setOnClickListener { finish() }
    }
}