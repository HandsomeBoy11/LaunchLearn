package com.yyzh.launchlearn.systemParent.viewModel

import androidx.lifecycle.MutableLiveData
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.systemParent.model.repository.SystemRepository
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.myapplication.base.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SystemTypeViewModel:BaseViewModel() {

    val mArticleList=MutableLiveData<ArticleList>()
    val error=MutableLiveData<String>()
    private val repository by lazy { SystemRepository() }

    fun getSystemTypeDetail(id: Int, page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getSystemTypeDetail(id, page) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getBlogArticle(id: Int, page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getBlogArticle(id, page) }
            executeResponse(result, { mArticleList.value = result.data }, {
                error.value=result.errorMsg
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