package com.yyzh.launchlearn.main.view

import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.yyzh.commenlibrary.base.BaseActivity
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.home.view.fragment.HomeFragment
import com.yyzh.launchlearn.navigation.view.fragment.NavigationFragment
import com.yyzh.launchlearn.newProject.view.fragment.NewProjectFragment
import com.yyzh.launchlearn.systemParent.view.fragment.SystemFragment
import kotlinx.android.synthetic.main.new_main_activity.*

class NewMainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var lastTime:Long =0;
    private var tableTitles= arrayListOf<String>("首页", "最新项目", "体系", "导航")
    private val fragmnets by lazy {
        arrayListOf<Fragment>()
    }
    private val homeFragment by lazy { HomeFragment() }
    private val newProjectFragment by lazy { NewProjectFragment.getInstance(0,true) }
    private val systemFragment by lazy { SystemFragment() }
    private val navigationFragment by lazy { NavigationFragment() }
    init {
        fragmnets.apply {
            add(homeFragment)
            add(newProjectFragment)
            add(systemFragment)
            add(navigationFragment)
        }
    }

    override fun getLayoutResId(): Int = R.layout.new_main_activity

    override fun initView() {
        mainToolBar.setNavigationOnClickListener { mainDrawerLayout.openDrawer(Gravity.LEFT) }
        navigationView.setNavigationItemSelectedListener(this)
        viewPager.adapter=object :FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment =fragmnets[position]

            override fun getCount(): Int =tableTitles.size

            override fun getPageTitle(position: Int): CharSequence? =tableTitles[position]

        }
        viewPager.offscreenPageLimit=tableTitles.size
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun initData() {


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_blog -> Toast.makeText(this, "公众号", Toast.LENGTH_SHORT).show()
            R.id.nav_project_type -> Toast.makeText(this, "项目分类", Toast.LENGTH_SHORT).show()
            R.id.nav_tool -> Toast.makeText(this, "工具", Toast.LENGTH_SHORT).show()
            R.id.nav_collect -> Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show()
            R.id.nav_about -> Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show()
            R.id.nav_exit -> Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if(currentTime-lastTime>500){
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show()
            lastTime=currentTime
            return
        }
        super.onBackPressed()
    }
}