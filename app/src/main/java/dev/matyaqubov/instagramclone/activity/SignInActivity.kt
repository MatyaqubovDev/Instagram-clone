package dev.matyaqubov.instagramclone.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dev.matyaqubov.instagramclone.R

/**
 * In SignInActivity , user can login using email
 */

class SignInActivity : BaseActivity() {
    val TAG = javaClass.simpleName.toString()
    lateinit var et_email:EditText
    lateinit var et_password:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    private fun initViews() {
        et_email=findViewById(R.id.et_email)
        et_password=findViewById(R.id.et_password)
        val b_signIn=findViewById<Button>(R.id.btn_signin)
        b_signIn.setOnClickListener { callMainActivity() }
        val tv_signup=findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }
    }

    private fun callSignUpActivity() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }


     fun callMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}