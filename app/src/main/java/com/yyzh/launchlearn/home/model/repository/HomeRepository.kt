package com.yyzh.launchlearn.home.model.repository

import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.launchlearn.home.model.bean.Banner
import com.yyzh.myapplication.base.BaseRepository
import com.yyzh.myapplication.base.WanResponse
import com.yyzh.myapplication.base.WanRetrofitClient

class HomeRepository : BaseRepository() {
    suspend fun getHomeBanner(): WanResponse<List<Banner>> {
        return apiCall {
            WanRetrofitClient.service.getBanner()
        }
    }

    suspend fun getHomeArticles(page: Int): WanResponse<ArticleList> {
        return apiCall {
            WanRetrofitClient.service.getHomeArticles(page)
        }
    }

    suspend fun setCollect(id: Int, collect: Boolean): WanResponse<ArticleList> {
        return apiCall {
            if(collect){
                WanRetrofitClient.service.collectArticle(id)
            }else{
                WanRetrofitClient.service.cancelCollectArticle(id)
            }
        }
    }
}