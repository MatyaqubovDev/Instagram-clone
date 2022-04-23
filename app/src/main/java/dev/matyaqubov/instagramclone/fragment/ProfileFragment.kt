package dev.matyaqubov.instagramclone.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.activity.BaseActivity
import dev.matyaqubov.instagramclone.adapter.ProfileAdapter
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.StorageManager
import dev.matyaqubov.instagramclone.manager.handler.DBPostsHandler
import dev.matyaqubov.instagramclone.manager.handler.DBUserHandler
import dev.matyaqubov.instagramclone.manager.handler.DBUsersHandler
import dev.matyaqubov.instagramclone.manager.handler.StorageHandler
import dev.matyaqubov.instagramclone.model.Post
import dev.matyaqubov.instagramclone.model.User
import dev.matyaqubov.instagramclone.utils.Extentions.toast
import dev.matyaqubov.instagramclone.utils.Logger

/**
 * In ProfileFragment , posts that user uploaded can be seen and user is able to change his/her profile photo
 */
class ProfileFragment : BaseFragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var iv_profile: ImageView
    lateinit var tv_fullname: TextView
    lateinit var tv_posts: TextView
    lateinit var tv_followers: TextView
    lateinit var tv_following: TextView
    lateinit var tv_email: TextView
    lateinit var base: BaseActivity

    val TAG = javaClass.simpleName.toString()

    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        base = requireActivity() as BaseActivity
        return initViews(inflater.inflate(R.layout.fragment_profile, container, false))
    }

    private fun initViews(view: View): View {
        val iv_loguot = view.findViewById<ImageView>(R.id.iv_logout)
        iv_loguot.setOnClickListener {
            logout()
        }
        tv_email = view.findViewById(R.id.tv_email)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_fullname = view.findViewById(R.id.tv_fullname)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        iv_profile = view.findViewById<ImageView>(R.id.iv_profile)
        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }
        loadUserInfo()
        loadMyPosts()
        loadMyFollowers()
        loadMyFollowing()

        return view
    }

    private fun loadMyFollowing() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                tv_following.text = users.size.toString()
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun loadMyFollowers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowers(uid, object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                tv_followers.text = users.size.toString()
            }

            override fun onError(e: Exception) {

            }

        })
    }


    private fun loadUserInfo() {
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                if (user != null) {
                    showUserInfo(user)
                }
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun showUserInfo(user: User) {
        tv_fullname.text = user.fullname
        tv_email.text = user.email
        Glide.with(this).load(user.userImg)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(iv_profile)
    }

    private fun logout() {
        toast("logout")
        AuthManager.signOut()
        base.callSignInActivity(requireContext())

    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        var adapter = ProfileAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun loadMyPosts() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                tv_posts.text = posts.size.toString()

                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {

            }

        })
    }

    /**
     * pick photo using FishBun library
     */
    private fun pickFishBunPhoto() {

        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                allPhotos =
                    it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedPhoto = allPhotos[0]
                uploadUserPhoto()
            }
        }

    private fun uploadUserPhoto() {
        if (pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                DatabaseManager.updateUserImage(imgUrl)
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(e: Exception) {
                Log.d(TAG, "onError: ${e.localizedMessage}")
            }

        })
    }


}