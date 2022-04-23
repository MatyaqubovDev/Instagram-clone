package dev.matyaqubov.instagramclone.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.activity.BaseActivity
import dev.matyaqubov.instagramclone.adapter.HomeAdapter
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.handler.DBPostHandler
import dev.matyaqubov.instagramclone.manager.handler.DBPostsHandler
import dev.matyaqubov.instagramclone.model.Post
import dev.matyaqubov.instagramclone.utils.DialogListener
import dev.matyaqubov.instagramclone.utils.Utils
import dev.matyaqubov.instagramclone.utils.Utils.dialogDouble
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG = javaClass.simpleName.toString()
    private lateinit var recyclerView: RecyclerView

    //    var base = requireActivity() as BaseActivity
    var listener: HomeListener? = null
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        return initViews(inflater.inflate(R.layout.fragment_home, container, false))
    }

    /*
   * onAttach is for communication of Fragments
   */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener) context
        else throw RuntimeException("$context must implement HomeListener")
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size > 0) {
            Log.d(TAG, "setUserVisibleHint: keldi")
            loadMyFeeds()
        }


    }


    private fun initViews(view: View): View {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            listener!!.scrollToUpload()
        }
        loadMyFeeds()
        return view
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
               dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }

    fun likeOrUnlike(post: Post) {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)
    }

    fun showDeleteDialog(post: Post) {
        Utils.dialogDouble(requireContext(),getString(R.string.str_delete_post),object : DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if (isChosen){
                    deletePost(post)
                }
            }

        })
    }

    fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                loadMyFeeds()
            }

            override fun onError(e: Exception) {

            }
        })
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener {
        fun scrollToUpload()
    }

}