package com.yyzh.launchlearn.login.model.repository

import com.yyzh.launchlearn.login.model.bean.User
import com.yyzh.myapplication.base.BaseRepository
import com.yyzh.myapplication.base.WanResponse
import com.yyzh.myapplication.base.WanRetrofitClient

class LoginRepository :BaseRepository() {
    suspend fun login(userName:String, password:String):WanResponse<User>{
        return apiCall {
            WanRetrofitClient.service.login(userName,password) }
    }
}