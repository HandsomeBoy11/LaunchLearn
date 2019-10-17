package com.yyzh.launchlearn.systemParent.model.repository

import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.launchlearn.systemParent.model.bean.SystemParent
import com.yyzh.myapplication.base.BaseRepository
import com.yyzh.myapplication.base.WanResponse
import com.yyzh.myapplication.base.WanRetrofitClient

class SystemRepository : BaseRepository() {
    suspend fun getSystemTypes(): WanResponse<List<SystemParent>> {
        return apiCall {
            WanRetrofitClient.service.getSystemType()
        }
    }

    suspend fun getSystemTypeDetail(id: Int, page: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getSystemTypeDetail(page, id) }
    }

    suspend fun getBlogArticle(id: Int, page: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getBlogArticle(id, page) }
    }

    suspend fun setCollect(id: Int, collect: Boolean): WanResponse<ArticleList> {
        return apiCall {
            if (collect) {
                WanRetrofitClient.service.collectArticle(id)
            } else {
                WanRetrofitClient.service.cancelCollectArticle(id)
            }
        }
    }
}