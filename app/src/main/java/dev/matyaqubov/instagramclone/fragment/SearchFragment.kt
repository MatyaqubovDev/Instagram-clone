package dev.matyaqubov.instagramclone.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.adapter.SearchAdapter
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.handler.DBUsersHandler
import dev.matyaqubov.instagramclone.model.User

/**
 * In SearchFragment , all registered users can be found by Searching keyword and followed
 */
class SearchFragment : BaseFragment() {
    val TAG = javaClass.simpleName.toString()
    lateinit var recyclerView: RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_search, container, false))
    }

    private fun initViews(view: View): View {

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)
        val et_search = view.findViewById<EditText>(R.id.et_search)
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = s.toString().trim()
                userByKeyword(keyword)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        loadUsers()
        refreshAdapter(items)
        return view
    }

    private fun refreshAdapter(items: ArrayList<User>) {
        val adapter = SearchAdapter(this, items)
        recyclerView.adapter = adapter

    }

    private fun loadUsers() {
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                items.clear()
                items.addAll(users)
                refreshAdapter(items)
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun userByKeyword(keyword: String) {
        if (keyword.isEmpty()) refreshAdapter(items)

        users.clear()
        for (user in items) {
            if (user.fullname.lowercase().startsWith(keyword.lowercase()))
                users.add(user)
        }
        refreshAdapter(users)
    }


}