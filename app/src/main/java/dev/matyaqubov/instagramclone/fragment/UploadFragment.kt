package dev.matyaqubov.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.matyaqubov.instagramclone.R

class UploadFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_upload, container, false))
    }

    private fun initViews(view: View?): View? {
        return view
    }

}