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
import dev.matyaqubov.instagramclone.model.Post
import org.w3c.dom.Text

class HomeAdapter(var fragment:HomeFragment,var items:ArrayList<Post>):BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_post_home,parent,false)

        return PostViewHolder(view)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post=items[position]
        if (holder is PostViewHolder){
            holder.apply {
                Glide.with(fragment).load(post.image).into(iv_post)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_profile:ShapeableImageView
        var iv_post:ShapeableImageView
        var tv_fullname:TextView
        var tv_time:TextView
        var tv_caption:TextView
        var iv_more:ImageView
        var iv_like:ImageView
        var iv_share:ImageView

        init {
            iv_profile=view.findViewById(R.id.iv_profile)
            iv_post=view.findViewById(R.id.iv_post)
            tv_fullname=view.findViewById(R.id.tv_fullname)
            tv_time=view.findViewById(R.id.tv_time)
            tv_caption=view.findViewById(R.id.tv_caption)
            iv_more=view.findViewById(R.id.iv_more)
            iv_like=view.findViewById(R.id.iv_like)
            iv_share=view.findViewById(R.id.iv_share)
        }

    }
}