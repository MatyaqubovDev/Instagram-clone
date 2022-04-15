package dev.matyaqubov.instagramclone.fragment

import android.content.Context
import android.os.Bundle
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
import dev.matyaqubov.instagramclone.manager.handler.DBPostsHandler
import dev.matyaqubov.instagramclone.model.Post
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG = javaClass.simpleName.toString()
    private lateinit var recyclerView: RecyclerView
    lateinit var base: BaseActivity
    var listener: HomeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        base = requireActivity() as BaseActivity
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
        base.showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                base.dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                base.dismissLoading()
            }

        })
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }


    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener {
        fun scrollToUpload()
    }

}