package dev.matyaqubov.instagramclone.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.adapter.SearchAdapter
import dev.matyaqubov.instagramclone.manager.AuthManager
import dev.matyaqubov.instagramclone.manager.DatabaseManager
import dev.matyaqubov.instagramclone.manager.handler.DBFollowHandler
import dev.matyaqubov.instagramclone.manager.handler.DBUserHandler
import dev.matyaqubov.instagramclone.manager.handler.DBUsersHandler
import dev.matyaqubov.instagramclone.model.FirebaseRequest
import dev.matyaqubov.instagramclone.model.FirebaseResponse
import dev.matyaqubov.instagramclone.model.Notification
import dev.matyaqubov.instagramclone.model.User
import dev.matyaqubov.instagramclone.network.ApiClient
import dev.matyaqubov.instagramclone.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
                    override fun onSuccess(following: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid, users, following))
                        refreshAdapter(items)
                    }

                    override fun onError(e: Exception) {

                    }
                })

            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun mergedUsers(
        uid: String,
        users: ArrayList<User>,
        following: ArrayList<User>
    ): ArrayList<User> {
        var items = ArrayList<User>()

        for (u in users) {
            val user = u
            for (f in following) {
                if (u.uid == f.uid) {
                    user.isFollowed = true
                    break
                }
            }
            if (uid != user.uid) items.add(user)
        }

        return items
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

    fun followOrUnfollow(to: User) {
        val uid = AuthManager.currentUser()!!.uid
        if (!to.isFollowed) followUser(uid, to)
        else unFollowUser(uid, to)
    }

    private fun followUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.followUser(me!!, to, object : DBFollowHandler {
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = true
                        if (to.device_tokens.size != 0) {
                            sendFollowNotification(to.device_tokens, me)
                        }
                        DatabaseManager.storePostToMyFeed(uid, to)
                    }

                    override fun onError(e: java.lang.Exception) {

                    }

                })
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun unFollowUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.unFollowUser(me!!, to, object : DBFollowHandler {
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = false
                        if (to.device_tokens.size != 0) {
                            sendUnFollowNotification(to.device_tokens, me)
                        }
                        DatabaseManager.removePostsFromMyFeed(uid, to)
                    }

                    override fun onError(e: Exception) {

                    }

                })
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun sendFollowNotification(token: ArrayList<String>, user: User) {
        var notification = Notification("${user.fullname} started following you", "Instagram clone")
        ApiClient.apiService.sendNotification(FirebaseRequest(notification, token))
            .enqueue(object : Callback<FirebaseResponse> {
                override fun onResponse(
                    call: Call<FirebaseResponse>,
                    response: Response<FirebaseResponse>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<FirebaseResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.localizedMessage}")
                }

            })
    }

    private fun sendUnFollowNotification(token: ArrayList<String>, user: User) {
        var notification =
            Notification("${user.fullname} stopping following you", "Instagram clone")
        ApiClient.apiService.sendNotification(FirebaseRequest(notification, token))
            .enqueue(object : Callback<FirebaseResponse> {
                override fun onResponse(
                    call: Call<FirebaseResponse>,
                    response: Response<FirebaseResponse>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<FirebaseResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.localizedMessage}")
                }

            })
    }

}