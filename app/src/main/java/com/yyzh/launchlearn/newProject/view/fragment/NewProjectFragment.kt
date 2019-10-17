package com.yyzh.launchlearn.newProject.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yyzh.commenlibrary.base.BaseVMFragment
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.newProject.adapter.NewProjectAdapter
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.launchlearn.main.view.BrowserNormalActivity
import com.yyzh.launchlearn.newProject.viewModel.NewProjectViewModel
import com.yyzh.launchlearn.utils.Preference
import com.yyzh.launchlearn.login.view.LoginActivity
import com.yyzh.launchlearn.widget.CustomLoadMoreView
import com.yyzh.mylibrary.utils.dp2px
import com.yyzh.mylibrary.utils.startKtxActivity
import kotlinx.android.synthetic.main.fragment_new_project.*

class NewProjectFragment : BaseVMFragment<NewProjectViewModel>() {
    override fun providerVMClass(): Class<NewProjectViewModel>? = NewProjectViewModel::class.java
    private var currentPage: Int = 0
    private val cid by lazy { arguments?.getInt(CID) }
    private val isLast by lazy { arguments?.getBoolean(ISLAST) }
    private val mAdapter by lazy { NewProjectAdapter() }
    private var isLogin:Boolean by Preference(Preference.IS_LOGIN,false)

    override fun getLayoutResId(): Int = R.layout.fragment_new_project

    override fun initView() {
        projectRefresh.apply {
            isRefreshing = true
            setOnRefreshListener { refreshFuntion() }
        }
        projectRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom = dp2px(10f)
                }
            })
        }
        mAdapter.apply {
            setOnItemClickListener { _, _, position ->
                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to mAdapter.data[position].link)
            }

            onItemChildClickListener = collectClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                loadMore()
            }, projectRv)
        }
        refreshFuntion()
    }

    private val collectClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if(isLogin){
                    mAdapter.run {
                        data[position].run {
                            collect=!collect
                            mViewModel.collectArticle(id,collect)
                        }
                        notifyDataSetChanged()
                    }
                }else{
                    startKtxActivity<LoginActivity>()
                }

            }
        }
    }

    //加载更多请求
    private fun loadMore() {
        isLast?.run {
            if (this) {
                mViewModel.getNewProjectList(currentPage)
            } else {
                cid?.let {
                    mViewModel.getProjectTypeDetailList(currentPage, it)
                }

            }
        }
    }

    //刷新请求
    private fun refreshFuntion() {
        currentPage = 0
        isLast?.run {
            if (this) {
                mViewModel.getNewProjectList(currentPage)
            } else {
                cid?.let {
                    mViewModel.getProjectTypeDetailList(currentPage, it)
                }

            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            projectList.observe(this@NewProjectFragment, Observer {
                Log.e("callback:", it.toString())
                it?.let { setAdapterList(it) }

            })
            error.observe(this@NewProjectFragment, Observer {
                if(projectRefresh.isRefreshing) projectRefresh.isRefreshing=false
            })

        }
    }

    private fun setAdapterList(articleList: ArticleList) {

        if (currentPage == 0) {
            if (projectRefresh.isRefreshing) projectRefresh.isRefreshing = false
            mAdapter.apply {
                replaceData(articleList.datas)
            }
            mAdapter.setEnableLoadMore(true)
            currentPage++
        } else {
            if (articleList.offset >= articleList.total) {
                mAdapter.loadMoreEnd()
                return
            }
            mAdapter.apply {
                addData(articleList.datas)
                loadMoreComplete()
            }
            currentPage++
        }

    }

    companion object {
        private const val CID: String = "cid"
        private const val ISLAST: String = "isLast"
        fun getInstance(cid: Int, isLast: Boolean): NewProjectFragment {
            val fragment = NewProjectFragment()
            var bundle = Bundle()
            bundle.putInt(CID, cid)
            bundle.putBoolean(ISLAST, isLast)
            fragment.arguments = bundle
            return fragment

        }
    }

}