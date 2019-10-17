package com.yyzh.launchlearn.home.view.fragment

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.youth.banner.BannerConfig
import com.yyzh.commenlibrary.base.BaseVMFragment
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.home.adapter.HomeAdapter
import com.yyzh.launchlearn.main.view.BrowserNormalActivity
import com.yyzh.launchlearn.home.viewModel.HomeViewModel
import com.yyzh.launchlearn.widget.CustomLoadMoreView
import com.yyzh.mylibrary.utils.GlideImageLoader
import com.yyzh.mylibrary.utils.dp2px
import com.yyzh.mylibrary.utils.startKtxActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>() {
    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java
    private var page: Int = 0

    private val banner by lazy { com.youth.banner.Banner(activity) }
    private val mAdapter by lazy { HomeAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
        refresh.setOnRefreshListener {
            page = 0
            mViewModel.getHomeBanner()
            mViewModel.getHomeArticles(page)
        }
        homeRv.layoutManager = LinearLayoutManager(activity)
        homeRv.apply {
            adapter = mAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                        outRect.bottom=dp2px(10f )
                }
            })
        }
        initBanner()
        mAdapter.apply {
            addHeaderView(banner)
            onItemChildClickListener= itemChildClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                loadMore()
            },homeRv)
            setOnItemClickListener { _, _, position ->
                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to mAdapter.data[position].link)
            }
        }


    }
    //加载更多
    private fun loadMore() {
        mViewModel.getHomeArticles(++page)
    }

    private val itemChildClickListener= OnItemChildClickListener { _, view, position ->
        when(view.id){
            R.id.articleStar->//点击收藏
                mAdapter.run {
                    data[position]?.let {
                        it.collect=!it.collect
                        mViewModel.setCollect(it.id,it.collect)
                        notifyDataSetChanged()
                    }
                }

        }
    }


    private fun initBanner() {
        banner.apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(200f))
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to bannerUrls[position])
                }
            }
        }
    }

    override fun initData() {

        mViewModel.apply {
            getHomeBanner()//
            getHomeArticles(page)
            mBannerList.observe(this@HomeFragment, Observer {
                Log.e("banner", it.toString())
                bannerImages.clear()
                bannerUrls.clear()
                bannerUrls.clear()
                if (it.isNotEmpty()) {
                    for (banner in it) {
                        bannerImages.add(banner.imagePath)
                        bannerTitles.add(banner.title)
                        bannerUrls.add(banner.url)
                    }
                }
                setBanner()
            })
            mArticleList.observe(this@HomeFragment, Observer {
                Log.e("成功", it.toString())
                if (page == 0) {
                    if (refresh.isRefreshing) refresh.isRefreshing = false
                    it?.let { it1 ->
                        mAdapter.replaceData(it1.datas)
                    }
                } else {
                    it?.let { it1 ->
                        mAdapter.loadMoreComplete()
                        mAdapter.addData(it1.datas)
                    }
                }
            })
            errMsg.observe(this@HomeFragment, Observer {
                it?.let {
                    Log.e("错误信息", it)
                }
            })
        }
    }

    /**
     * 设置banner
     */
    private fun setBanner() {
        banner.apply {
            setImages(bannerImages)
            setBannerTitles(bannerTitles)
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setDelayTime(3000)
            start()
        }
    }

}