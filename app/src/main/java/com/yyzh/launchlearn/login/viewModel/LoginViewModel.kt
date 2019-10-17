package com.yyzh.launchlearn.login.viewModel

import androidx.lifecycle.MutableLiveData
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.login.model.bean.User
import com.yyzh.launchlearn.login.model.repository.LoginRepository
import com.yyzh.myapplication.base.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel:BaseViewModel() {
    val mLoginUser: MutableLiveData<User> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    private val response by lazy { LoginRepository() }

    fun login(userName: String, password: String) {
        launch {
            val wanResponse = withContext(Dispatchers.IO) { response.login(userName, password) }
            executeResponse(wanResponse, {
                mLoginUser.postValue(wanResponse.data)
            }, {
                errMsg.postValue(wanResponse.errorMsg)
            })
        }
    }
}