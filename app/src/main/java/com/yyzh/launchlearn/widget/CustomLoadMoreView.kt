package com.yyzh.launchlearn.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.yyzh.launchlearn.R

class CustomLoadMoreView:LoadMoreView() {
    override fun getLayoutId(): Int = R.layout.view_load_more

    override fun getLoadingViewId(): Int=R.id.load_more_loading_view

    override fun getLoadEndViewId(): Int =R.id.load_more_load_end_view

    override fun getLoadFailViewId(): Int =R.id.load_more_load_fail_view
}