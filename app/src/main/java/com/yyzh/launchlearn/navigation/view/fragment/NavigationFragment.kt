package com.yyzh.launchlearn.navigation.view.fragment

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yyzh.commenlibrary.base.BaseVMFragment
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.navigation.adapter.NavigationBodyAdapter
import com.yyzh.launchlearn.navigation.adapter.NavigationTabAdapter
import com.yyzh.launchlearn.navigation.viewModel.NavigationViewModel
import com.yyzh.mylibrary.utils.dp2px
import kotlinx.android.synthetic.main.fragment_navation.*

class NavigationFragment:BaseVMFragment<NavigationViewModel>(){
    override fun providerVMClass(): Class<NavigationViewModel>? = NavigationViewModel::class.java

    private val tabLayoutManger by lazy { LinearLayoutManager(activity) }
    private val bodyLayoutManger by lazy { LinearLayoutManager(activity) }
    private val mTabAdapter by lazy { NavigationTabAdapter() }
    private val mBodyAdapter by lazy { NavigationBodyAdapter() }

    override fun getLayoutResId(): Int = R.layout.fragment_navation

    override fun initView() {
        tabRv.apply {
            layoutManager=tabLayoutManger
            adapter=mTabAdapter
        }
        bodyRv.apply {
            layoutManager=bodyLayoutManger
            adapter=mBodyAdapter
            addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom=dp2px(10f)
                    outRect.left=dp2px(5f)
                    outRect.right=dp2px(5f)
                }
            })
        }

        mTabAdapter.apply {
            onItemClickListener= BaseQuickAdapter.OnItemClickListener{ _,_,position->
                data.forEachIndexed { index, _ ->
                    data[index].nameIsSelect=index==position
                }
                scrollToPosition(position)
                notifyDataSetChanged()
            }
        }

    }
    private fun scrollToPosition(position: Int) {
        val firstPotion = bodyLayoutManger.findFirstVisibleItemPosition()
        val lastPosition = bodyLayoutManger.findLastVisibleItemPosition()
        when {
            position <= firstPotion || position >= lastPosition -> bodyRv.smoothScrollToPosition(position)
            else -> bodyRv.run {
                smoothScrollBy(0, this.getChildAt(position - firstPotion).top - this.dp2px(8f))
            }
        }
    }
    override fun initData() {
        mViewModel.getNavigation()
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mNavigationList.observe(this@NavigationFragment, Observer {
                if(it.isNotEmpty()){
                    it.forEachIndexed{ index, _ ->
                        it[index].nameIsSelect = index==0
                    }
                }
                mTabAdapter.replaceData(it)
                mBodyAdapter.replaceData(it)
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)

    }

}