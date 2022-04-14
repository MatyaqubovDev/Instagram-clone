package dev.matyaqubov.instagramclone.utils

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

object Extentions {
    fun Activity.toast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.toast(msg:String){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}