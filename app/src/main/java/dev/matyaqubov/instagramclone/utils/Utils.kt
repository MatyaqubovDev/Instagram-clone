package dev.matyaqubov.instagramclone.utils

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.model.ScreenSize

object Utils {
    fun toast(context: Context,msg:String){
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

    fun dialogDouble(context: Context?, title: String?, callback: DialogListener) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_double)
        dialog.setCanceledOnTouchOutside(true)
        val d_title = dialog.findViewById<TextView>(R.id.d_title)
        val d_confirm = dialog.findViewById<TextView>(R.id.d_confirm)
        val d_cancel = dialog.findViewById<TextView>(R.id.d_cancel)
        d_title.text = title
        d_confirm.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(true)
        }
        d_cancel.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(false)
        }
        dialog.show()
    }

    fun dialogSingle(context: Context?, title: String?, callback: DialogListener) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_single)
        dialog.setCanceledOnTouchOutside(true)
        val d_title = dialog.findViewById<TextView>(R.id.d_title)
        val d_confirm = dialog.findViewById<TextView>(R.id.d_confirm)
        d_title.text = title
        d_confirm.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(true)
        }
        dialog.show()
    }
}

interface DialogListener {
    fun onCallback(isChosen: Boolean)
}
