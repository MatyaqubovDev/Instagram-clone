package dev.matyaqubov.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.adapter.FavoriteAdapter
import dev.matyaqubov.instagramclone.adapter.HomeAdapter
import dev.matyaqubov.instagramclone.model.Post

class FavouriteFragment : BaseFragment() {
    val TAG = javaClass.simpleName.toString()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_favourite, container, false))
    }

    private fun initViews(view: View): View {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        refreshAdapter(loadPosts())
        return view
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavoriteAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun loadPosts(): ArrayList<Post> {
        val items = ArrayList<Post>()
//        items.add(Post("https://images.unsplash.com/photo-1523345863760-5b7f3472d14f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"))
//        items.add(Post("https://images.unsplash.com/photo-1624862300786-fcdbd20ba0c3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=886&q=80"))
//        items.add(Post("https://images.unsplash.com/photo-1520186994231-6ea0019d8d51?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=543&q=80"))
        return items
    }

}