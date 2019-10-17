package com.yyzh.launchlearn.newProject.model.repository

import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.myapplication.base.BaseRepository
import com.yyzh.myapplication.base.WanResponse
import com.yyzh.myapplication.base.WanRetrofitClient

class NewProjectRepository:BaseRepository() {
    suspend fun getNewProjectList(page:Int):WanResponse<ArticleList>{
        return apiCall{
            WanRetrofitClient.service.getLastedProject(page)
        }
    }
    suspend fun getProjectTypeDetailList(page: Int, cid: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getProjectTypeDetail(page, cid) }
    }
    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId) }
    }
}