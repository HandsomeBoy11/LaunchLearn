package com.yyzh.launchlearn.navigation.model.bean

import com.yyzh.launchlearn.main.model.bean.Article

/**
 * Created by Lu
 * on 2018/3/28 21:22
 */
data class Navigation(val articles: List<Article>,
                      val cid: Int,
                      var nameIsSelect:Boolean=false,
                      val name: String)