package com.yyzh.launchlearn.systemParent.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yyzh.commenlibrary.base.BaseVMFragment
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.home.adapter.HomeAdapter
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.launchlearn.main.view.BrowserNormalActivity
import com.yyzh.launchlearn.systemParent.viewModel.SystemTypeViewModel
import com.yyzh.launchlearn.widget.CustomLoadMoreView
import com.yyzh.mylibrary.utils.dp2px
import com.yyzh.mylibrary.utils.startKtxActivity
import kotlinx.android.synthetic.main.system_type_fragment.*

class SystemTypeFragment : BaseVMFragment<SystemTypeViewModel>() {
    override fun providerVMClass(): Class<SystemTypeViewModel>? = SystemTypeViewModel::class.java

    private val cid by lazy { arguments?.getInt("id") }
    private val b by lazy { arguments?.getBoolean("b") }
    private var currentPage: Int = 0
    private val mAdapter by lazy { HomeAdapter() }
    override fun getLayoutResId(): Int = R.layout.system_type_fragment

    override fun initView() {
        refresh.apply {
            isRefreshing=true
            mAdapter.setEnableLoadMore(false)
            setOnRefreshListener {
                currentPage = 0
                loadData()
            }
        }

        rv_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
            addItemDecoration(itemDecoration)
        }

        mAdapter.apply {
            onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to data[position].link)
            }
            onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, _, position ->
                mViewModel.apply {
                    data[position].collect = !data[position].collect
                    setCollect(data[position].id, data[position].collect)
                    notifyDataSetChanged()
                }
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                loadData()
            },rv_list)
        }
        loadData()
    }

    private val itemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = rv_list.dp2px(10f)
        }
    }

    private fun loadData() {
        mViewModel.apply {
            cid?.let {
                if (b!!) {
                    getBlogArticle(it, currentPage)//获取列表数据
                } else {
                    getSystemTypeDetail(it, currentPage)
                }

            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mArticleList.observe(this@SystemTypeFragment, Observer {
                setAdapter(it)
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        if(currentPage==0||refresh.isRefreshing) refresh.isRefreshing=false
        else mAdapter.loadMoreFail()
    }

    //设置adapter数据
    private fun setAdapter(articleList: ArticleList?) {
        mAdapter.apply {
            articleList?.let {
                if (it.offset >= it.total) {
                    loadMoreEnd()
                    return
                }
                if (refresh.isRefreshing) {
                    replaceData(it.datas)
                } else {
                    addData(it.datas)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                currentPage++
            }
            refresh.isRefreshing = false
        }

    }

    companion object {
        fun newInstance(id: Int, b: Boolean): Fragment {
            val fragment = SystemTypeFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            bundle.putBoolean("b", b)
            fragment.arguments = bundle
            return fragment
        }
    }
}