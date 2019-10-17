package com.yyzh.launchlearn.home.viewModel

import androidx.lifecycle.MutableLiveData
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.home.model.repository.HomeRepository
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.launchlearn.home.model.bean.Banner
import com.yyzh.myapplication.base.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel:BaseViewModel() {
    private val repository by lazy { HomeRepository() }
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mBannerList: MutableLiveData<List<Banner>> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()

    //首页banner
    fun getHomeBanner(){
        launch {
            val response = withContext(Dispatchers.IO) { repository.getHomeBanner() }
            executeResponse(response,{
                mBannerList.value=response.data
            },{
                errMsg.value=response.errorMsg
            })
        }

    }
    //首页列表数据
    fun getHomeArticles(page:Int){
        launch {
            val response = withContext(Dispatchers.IO) { repository.getHomeArticles(page) }
            executeResponse(response,{
                mArticleList.value=response.data
            },{
                errMsg.value=response.errorMsg
            })
        }

    }

    //点击收藏
    fun setCollect(id:Int,collect:Boolean) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.setCollect(id,collect) }
//            executeResponse(response,{
//                mArticleList.value=response.data
//            },{
//                errMsg.value=response.errorMsg
//            })
        }
    }
}