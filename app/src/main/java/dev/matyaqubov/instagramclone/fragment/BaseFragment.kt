package dev.matyaqubov.instagramclone.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import dev.matyaqubov.instagramclone.R

open class BaseFragment : Fragment() {

    var progressDialog: AppCompatDialog? = null

    fun showLoading(activity: Activity?) {
        if (activity == null) return

        if (progressDialog != null && progressDialog!!.isShowing()) {
//            progressDialog.dismiss();
        } else {
            progressDialog = AppCompatDialog(activity, R.style.CustomDialog)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.custom_progress_dialog)
            val iv_progress = progressDialog!!.findViewById<ImageView>(R.id.iv_progress)
            val animationDrawable = iv_progress!!.getDrawable() as AnimationDrawable
            animationDrawable.start()
            if (!activity.isFinishing) progressDialog!!.show()
        }
    }

    protected fun dismissLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }
}