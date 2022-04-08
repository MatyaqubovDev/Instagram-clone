package dev.matyaqubov.instagramclone.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import dev.matyaqubov.instagramclone.R
import java.lang.RuntimeException

class HomeFragment : Fragment() {
    var listener: HomeListener? = null

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
        listener=if (context is HomeListener) context
        else throw RuntimeException("$context must implement HomeListener")
    }

    override fun onDetach() {
        listener=null
        super.onDetach()
    }


    private fun initViews(view: View): View {


        val iv_camera=view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            listener!!.scrollToUpload()
        }
        return view
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener {
        fun scrollToUpload()
    }

}