package dev.matyaqubov.instagramclone.activity


import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.matyaqubov.instagramclone.R
import dev.matyaqubov.instagramclone.adapter.ViewPagerAdapter
import dev.matyaqubov.instagramclone.fragment.*

/**
 * It contains viewPager with 5 fragment in MainActivity,
 * and pages can be controlled by BottomNavigationView
 */

class MainActivity : BaseActivity(), UploadFragment.UploadListener, HomeFragment.HomeListener {

    val TAG = javaClass.simpleName.toString()
    var index = 0
    lateinit var homeFragment: HomeFragment
    lateinit var uploadFragment: UploadFragment
    lateinit var viewPager: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun scrollToUpload() {
        index = 2
        scrollByIndex(index)
    }

    override fun scrollToHome() {
        index = 0
        scrollByIndex(index)
    }

    private fun scrollByIndex(index: Int) {
        viewPager.currentItem = index
        bottomNavigationView.menu.getItem(index).isChecked = true
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewPager.currentItem = 0
                }
                R.id.navigation_search -> {
                    viewPager.currentItem = 1
                }
                R.id.navigation_upload -> {
                    viewPager.currentItem = 2
                }
                R.id.navigation_favourite -> {
                    viewPager.currentItem = 3
                }
                R.id.navigation_profile -> {
                    viewPager.currentItem = 4
                }
            }
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                index = position
                bottomNavigationView.menu.getItem(index).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        //Home and Upload Fragments are global for communication purpose
        homeFragment = HomeFragment()
        uploadFragment = UploadFragment()
        setupViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(homeFragment)
        adapter.addFragment(SearchFragment())
        adapter.addFragment(uploadFragment)
        adapter.addFragment(FavouriteFragment())
        adapter.addFragment(ProfileFragment())
        viewPager.adapter = adapter
    }


}