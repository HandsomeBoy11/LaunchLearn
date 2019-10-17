package com.yyzh.launchlearn.newProject.viewModel

import androidx.lifecycle.MutableLiveData
import com.yyzh.commenlibrary.base.BaseViewModel
import com.yyzh.launchlearn.newProject.model.repository.NewProjectRepository
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.myapplication.base.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewProjectViewModel:BaseViewModel() {
    private val repository by lazy { NewProjectRepository() }
    val projectList:MutableLiveData<ArticleList> = MutableLiveData()
    var error=MutableLiveData<String>()

     fun getNewProjectList(page:Int){
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getNewProjectList(page)
            }
            executeResponse(response,{
                projectList.value=response.data
            },{
                error.value=response.errorMsg
            })
        }
    }
    fun getProjectTypeDetailList(page:Int,cid:Int){
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getProjectTypeDetailList(page,cid)
            }
            executeResponse(response,{
                projectList.value=response.data
            },{
                error.value=response.errorMsg
            })
        }
    }

    fun collectArticle(id: Int, collect: Boolean) {
        launch {
            val response = withContext(Dispatchers.IO) {
                if (collect) repository.collectArticle(id)
                else repository.unCollectArticle(id)
            }
//            executeResponse(response,{
//                projectList.value=response.data
//            },{
//                error.value=response.errorMsg
//            })
        }
    }
}