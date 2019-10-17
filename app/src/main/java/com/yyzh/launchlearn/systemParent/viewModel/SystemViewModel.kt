package com.yyzh.launchlearn.systemParent.viewModel

import androidx.lifecycle.MutableLiveData
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.systemParent.model.repository.SystemRepository
import com.yyzh.launchlearn.systemParent.model.bean.SystemParent
import com.yyzh.myapplication.base.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SystemViewModel:BaseViewModel() {
    val systemList by lazy { MutableLiveData<List<SystemParent>>() }

    val error by lazy { MutableLiveData<String>() }
    private val repostory by lazy { SystemRepository() }

    fun getSystemTypes(){
        launch {
            val response = withContext(Dispatchers.IO) {
                repostory.getSystemTypes()
            }
            executeResponse(response,{
                systemList.value=response.data
            },{
                error.value=response.errorMsg
            })
        }
    }
}
