package dev.matyaqubov.instagramclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.fragment.ProfileFragment
import dev.matyaqubov.instagramclone.model.Post
import dev.matyaqubov.instagramclone.utils.Utils

class ProfileAdapter(var fragment: ProfileFragment, var items: ArrayList<Post>) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_profile, parent, false)

        return PostViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = items[position]
        if (holder is PostViewHolder) {
            holder.apply {
                setViewHeight(iv_post)
                tv_caption.text = post.caption
                Glide.with(fragment).load(post.postImg).into(iv_post)
            }
        }
    }

    /**
     * Set ShapeableImageView height as screen width
     */
    private fun setViewHeight(view: View) {
        val params: ViewGroup.LayoutParams = view.layoutParams
        params.height = Utils.screenSize(fragment.requireActivity().application).width / 2
        view.layoutParams = params
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_post: ShapeableImageView
        var tv_caption: TextView


        init {
            iv_post = view.findViewById(R.id.iv_post)
            tv_caption = view.findViewById(R.id.tv_caption)
        }

    }
}