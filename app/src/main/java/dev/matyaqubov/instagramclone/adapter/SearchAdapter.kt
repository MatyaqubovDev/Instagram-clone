package dev.matyaqubov.instagramclone.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.fragment.SearchFragment
import dev.matyaqubov.instagramclone.model.User

class SearchAdapter(var fragment: SearchFragment, var items: ArrayList<User>):BaseAdapter(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_user_search,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user=items[position]
       if (holder is UserViewHolder){
           holder.apply {
               tv_email.text=user.email
               tv_fullname.text=user.fullname
           }
       }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    inner class UserViewHolder(view: View):RecyclerView.ViewHolder(view){
        var iv_profile:ShapeableImageView
        var tv_fullname:TextView
        var tv_email:TextView
        var tv_follow:TextView

        init {
            iv_profile=view.findViewById(R.id.iv_profile)
            tv_fullname=view.findViewById(R.id.tv_fullname)
            tv_email=view.findViewById(R.id.tv_email)
            tv_follow=view.findViewById(R.id.tv_follow)
        }

    }

}