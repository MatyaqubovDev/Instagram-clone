package dev.matyaqubov.instagramclone.utils

import android.util.Log
import android.widget.Toast

object Logger {
    val IS_TESTER = true
    fun d(TAG: String, msg: String) {
        if (IS_TESTER) {
            Log.d(TAG, msg)
        }
    }

    fun e(TAG: String, msg: String) {
        if (IS_TESTER) {
            Log.e(TAG, msg)
        }
    }

    fun i(TAG: String, msg: String) {
        if (IS_TESTER) {
            Log.i(TAG, msg)
        }
    }

    fun v(TAG: String, msg: String) {
        if (IS_TESTER) {
            Log.v(TAG, msg)
        }
    }

}