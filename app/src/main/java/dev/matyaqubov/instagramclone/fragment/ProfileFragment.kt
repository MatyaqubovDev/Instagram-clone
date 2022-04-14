package dev.matyaqubov.instagramclone.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.adapter.ProfileAdapter
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.model.Post
import dev.matyaqubov.instagramclone.utils.Extentions.toast
import dev.matyaqubov.instagramclone.utils.Logger

/**
 * In ProfileFragment , posts that user uploaded can be seen and user is able to change his/her profile photo
 */
class ProfileFragment : BaseFragment() {
    lateinit var recyclerView: RecyclerView
    val TAG=javaClass.simpleName.toString()

    var pickedPhoto: Uri?=null
    var allPhotos=ArrayList<Uri>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_profile, container, false))
    }

    private fun initViews(view: View): View {
        val iv_loguot=view.findViewById<ImageView>(R.id.iv_logout)
        iv_loguot.setOnClickListener {
            logout()
        }
        recyclerView=view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager=GridLayoutManager(activity,2)
        val iv_profile=view.findViewById<ImageView>(R.id.iv_profile)
        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }
        refreshAdapter(loadPosts())

        return view
    }

    private fun logout() {
        toast("logout")
        AuthManager.signOut()
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        var adapter= ProfileAdapter(this,items)
        recyclerView.adapter=adapter
    }

    private fun loadPosts(): ArrayList<Post> {
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1523345863760-5b7f3472d14f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1624862300786-fcdbd20ba0c3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=886&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1520186994231-6ea0019d8d51?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=543&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1523345863760-5b7f3472d14f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1624862300786-fcdbd20ba0c3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=886&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1520186994231-6ea0019d8d51?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=543&q=80"))
        return items
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
                uploadPickedPhoto()
            }
        }

    private fun uploadPickedPhoto() {
        if (pickedPhoto!=null){
            Logger.d(TAG,pickedPhoto!!.path.toString())
        }
    }


}