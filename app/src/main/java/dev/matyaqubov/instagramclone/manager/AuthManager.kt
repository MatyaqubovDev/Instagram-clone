package dev.matyaqubov.instagramclone.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthManager {
    companion object {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


        fun isSignedIn(): Boolean {
            return currentUser() != null
        }

        private fun currentUser():FirebaseUser?{
            return firebaseAuth.currentUser
        }

        fun singIn(email: String, password: String, handler: AuthHandler) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid= currentUser()!!.uid
                    handler.onSuccess(uid)
                } else {

                    handler.onError(task.exception)

                }
            }
        }

        fun singUp(email: String, password: String, handler: AuthHandler) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid= currentUser()!!.uid
                    handler.onSuccess(uid)
                } else {
                    handler.onError(task.exception)
                }
            }
        }

        fun signOut(){
            firebaseAuth.signOut()
        }
    }
}