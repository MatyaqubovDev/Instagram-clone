package dev.matyaqubov.instagramclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.fragment.HomeFragment
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.model.Post

class HomeAdapter(var fragment: HomeFragment, var items: ArrayList<Post>) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_home, parent, false)

        return PostViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = items[position]
        if (holder is PostViewHolder) {
            holder.apply {
                tv_fullname.text = post.fullname
                tv_caption_fullname.text = post.fullname
                tv_caption.text = post.caption
                tv_time.text = post.currentDate
                Glide.with(fragment).load(post.postImg).into(iv_post)
                Glide.with(fragment).load(post.userImg).into(iv_profile)
                iv_like.setOnClickListener {
                    if (post.isLiked) {
                        post.isLiked = false
                        iv_like.setImageResource(R.drawable.ic_unliked)
                    } else {
                        post.isLiked = true
                        iv_like.setImageResource(R.drawable.ic_liked)
                    }
                    fragment.likeOrUnlike(post)
                }
                if (post.isLiked) {
                    iv_like.setImageResource(R.drawable.ic_liked)
                } else {
                    iv_like.setImageResource(R.drawable.ic_unliked)
                }

                val uid = AuthManager.currentUser()!!.uid
                if (uid == post.uid) {
                    iv_more.visibility = View.VISIBLE
                } else {
                    iv_more.visibility = View.GONE
                }
                iv_more.setOnClickListener {
                    fragment.showDeleteDialog(post)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_profile: ShapeableImageView
        var iv_post: ShapeableImageView
        var tv_fullname: TextView
        var tv_caption_fullname: TextView
        var tv_time: TextView
        var tv_caption: TextView
        var iv_more: ImageView
        var iv_like: ImageView
        var iv_share: ImageView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            iv_post = view.findViewById(R.id.iv_post)
            tv_fullname = view.findViewById(R.id.tv_fullname)
            tv_caption_fullname = view.findViewById(R.id.tv_caption_fullname)
            tv_time = view.findViewById(R.id.tv_time)
            tv_caption = view.findViewById(R.id.tv_caption)
            iv_more = view.findViewById(R.id.iv_more)
            iv_like = view.findViewById(R.id.iv_like)
            iv_share = view.findViewById(R.id.iv_share)
        }

    }
}