package dev.matyaqubov.instagramclone.fragment

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.util.Util
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.activity.BaseActivity
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.StorageManager
import dev.matyaqubov.instagramclone.manager.handler.DBPostHandler
import dev.matyaqubov.instagramclone.manager.handler.DBUserHandler
import dev.matyaqubov.instagramclone.manager.handler.StorageHandler
import dev.matyaqubov.instagramclone.model.Post
import dev.matyaqubov.instagramclone.model.User
import dev.matyaqubov.instagramclone.utils.Logger
import dev.matyaqubov.instagramclone.utils.Utils
import java.lang.RuntimeException

/**
 * In UploadFragment, user can upload
 * a post with photo and caption
 */
class UploadFragment : BaseFragment() {
    val TAG = javaClass.simpleName.toString()
    lateinit var fl_photo: FrameLayout
    lateinit var iv_photo: ImageView
    lateinit var base: BaseActivity
    lateinit var et_caption: EditText
    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()
    var listener: UploadListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        base = requireActivity() as BaseActivity
        return initViews(inflater.inflate(R.layout.fragment_upload, container, false))
    }

    /*
    * onAttach is for communication of Fragments
    */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is UploadListener) context
        else throw RuntimeException("$context must implement UploadListener")
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    private fun initViews(view: View): View {
        val fl_view = view.findViewById<FrameLayout>(R.id.fl_view)
        setViewHeight(fl_view)
        et_caption = view.findViewById(R.id.et_caption)
        fl_photo = view.findViewById(R.id.fl_photo)
        iv_photo = view.findViewById(R.id.iv_photo)

        val iv_close = view.findViewById<ImageView>(R.id.iv_close)
        val iv_pick = view.findViewById<ImageView>(R.id.iv_pick)
        val iv_upload = view.findViewById<ImageView>(R.id.iv_upload)

        iv_pick.setOnClickListener {
            pickFishBunPhoto()
        }
        iv_close.setOnClickListener {
            hidePeckedPhoto()
        }

        iv_upload.setOnClickListener {
            uploadNewPost()
        }

        return view
    }


    private fun uploadNewPost() {

        listener!!.scrollToHome()
        val caption = et_caption.text.toString().trim()
        if (caption.isNotEmpty() && pickedPhoto != null) {
            uploadPostPhoto(caption, pickedPhoto!!)
        }
    }

    private fun uploadPostPhoto(caption: String, pickedPhoto: Uri) {
        base.showLoading(requireActivity())
        StorageManager.uploadPostPhoto(pickedPhoto, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                val post = Post(caption, imgUrl)
                val uid = AuthManager.currentUser()!!.uid

                DatabaseManager.loadUser(uid, object : DBUserHandler {
                    override fun onSuccess(user: User?) {
                        post.uid = uid
                        post.fullname = user!!.fullname
                        post.userImg = user!!.userImg
                        storePostToDB(post)
                    }

                    override fun onError(e: Exception) {

                    }

                })
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun storePostToDB(post: Post) {
        DatabaseManager.storePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                storeFeedToDB(post)
            }

            override fun onError(e: Exception) {
                base.dismissLoading()
            }

        })
    }

    private fun storeFeedToDB(post: Post) {
        DatabaseManager.storeFeeds(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                base.dismissLoading()
                resetAll()
                listener!!.scrollToHome()
            }

            override fun onError(e: Exception) {
                base.dismissLoading()
            }

        })
    }

    private fun resetAll() {
        allPhotos.clear()
        et_caption.text.clear()
        pickedPhoto = null
        fl_photo.visibility = View.GONE
    }

    private fun hidePeckedPhoto() {
        pickedPhoto = null
        fl_photo.visibility = View.GONE
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
                showPickedPhoto()
            }
        }

    private fun showPickedPhoto() {
        fl_photo.visibility = View.VISIBLE
        iv_photo.setImageURI(pickedPhoto)
    }

    /**
     * Set view height as screen width
     */

    private fun setViewHeight(view: View) {
        val params = view.layoutParams
        params.height = Utils.screenSize(requireActivity().application).width
        view.layoutParams = params
    }

    /**
     * This interface is created for communication with HomeFragment
     */

    interface UploadListener {
        fun scrollToHome()
    }

}