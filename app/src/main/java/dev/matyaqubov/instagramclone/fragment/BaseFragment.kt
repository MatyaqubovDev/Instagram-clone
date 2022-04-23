package dev.matyaqubov.instagramclone.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.activity.SignInActivity


open class BaseFragment:Fragment() {
    var progressDialog: AppCompatDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

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

    fun callSignInActivity(activity: Activity){
        val intent = Intent(context, SignInActivity::class.java)
        startActivity(intent)
        activity.finish()
    }
}