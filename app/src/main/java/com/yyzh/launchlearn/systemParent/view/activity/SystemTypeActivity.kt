package com.yyzh.launchlearn.systemParent.view.activity

import com.yyzh.commenlibrary.base.BaseVMActivity
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.systemParent.model.bean.SystemParent
import com.yyzh.launchlearn.systemParent.view.fragment.SystemTypeFragment
import kotlinx.android.synthetic.main.system_type_activity.*

class SystemTypeActivity:BaseVMActivity<BaseViewModel>() {
    override fun providerVMClass(): Class<BaseViewModel>?=BaseViewModel::class.java
    private val systemParent by lazy { intent?.getSerializableExtra(ARTICLE_LIST) as SystemParent }
    companion object{
        const val ARTICLE_LIST="article_list"
    }
    override fun getLayoutResId(): Int = R.layout.system_type_activity

    override fun initView() {
        mToolbar.run {
            title = systemParent.name
            setNavigationIcon(R.drawable.arrow_back)
        }

        initViewPager()
    }

    private fun initViewPager() {
        viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int) = SystemTypeFragment.newInstance(systemParent.children[position].id, false)

            override fun getCount() = systemParent.children.size

            override fun getPageTitle(position: Int) = systemParent.children[position].name

        }
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }
}