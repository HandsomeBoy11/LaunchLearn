package com.yyzh.launchlearn.navigation.model.repository

import com.yyzh.launchlearn.navigation.model.bean.Navigation
import com.yyzh.myapplication.base.BaseRepository
import com.yyzh.myapplication.base.WanResponse
import com.yyzh.myapplication.base.WanRetrofitClient

class NavigationRepository:BaseRepository() {

    suspend fun getNavigation():WanResponse<List<Navigation>>{
        return apiCall {
            WanRetrofitClient.service.getNavigation()
        }
    }
}