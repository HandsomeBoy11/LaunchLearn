package com.yyzh.launchlearn.navigation.viewModel

import androidx.lifecycle.MutableLiveData
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.navigation.model.repository.NavigationRepository
import com.yyzh.launchlearn.navigation.model.bean.Navigation
import com.yyzh.myapplication.base.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NavigationViewModel:BaseViewModel() {
    private val repository by lazy { NavigationRepository() }
    val mNavigationList: MutableLiveData<List<Navigation>> = MutableLiveData()

    fun getNavigation() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getNavigation() }
            executeResponse(result, { mNavigationList.value = result.data }, {})
        }
    }
}