package dev.matyaqubov.instagramclone.utils

import android.app.Application
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import dev.matyaqubov.instagramclone.model.ScreenSize

object Utils {
    fun fireToast(context: Context,msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun screenSize(context: Application):ScreenSize{
        val displayMetrics=DisplayMetrics()
        val windowManager=context.getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth=displayMetrics.widthPixels
        val deviceHeight=displayMetrics.heightPixels
        return ScreenSize(deviceWidth,deviceHeight)
    }
}