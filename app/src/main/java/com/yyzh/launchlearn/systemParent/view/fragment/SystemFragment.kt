package com.yyzh.launchlearn.systemParent.view.fragment

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yyzh.commenlibrary.base.BaseVMFragment
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.systemParent.adapter.SystemAdapter
import com.yyzh.launchlearn.systemParent.view.activity.SystemTypeActivity
import com.yyzh.launchlearn.systemParent.viewModel.SystemViewModel
import com.yyzh.launchlearn.utils.toast
import com.yyzh.myapplication.base.App
import com.yyzh.mylibrary.utils.dp2px
import com.yyzh.mylibrary.utils.startKtxActivity
import kotlinx.android.synthetic.main.fragment_system.*

class SystemFragment : BaseVMFragment<SystemViewModel>() {
    override fun providerVMClass(): Class<SystemViewModel>? = SystemViewModel::class.java
    private val mAdapter by lazy{ SystemAdapter() }

    override fun getLayoutResId(): Int = R.layout.fragment_system

    override fun initView() {
        systemRefresh.apply {
            setOnRefreshListener {
                initData()
            }
        }
        systemRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter=mAdapter
            addItemDecoration(itemDecoration)
        }
        mAdapter.apply {
            setOnItemClickListener { _, _, position -> //条目点击事件
                startKtxActivity<SystemTypeActivity>(value = SystemTypeActivity.ARTICLE_LIST to data[position])
            }
        }
    }

    private val itemDecoration= object :RecyclerView.ItemDecoration(){
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom=systemRv.dp2px(10f)
        }
    }

    override fun initData() {
        mViewModel.getSystemTypes()
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            systemList.observe(this@SystemFragment, Observer {
                mAdapter.run {
                    if(systemRefresh.isRefreshing) systemRefresh.isRefreshing=false
                    replaceData(it)
                }
            })
            error.observe(this@SystemFragment, Observer {
               App.CONTEXT.toast(it.toString())
            })
        }
    }

}