package com.yyzh.launchlearn.systemParent.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.main.model.bean.Article

class SystemTypeAdapter(layoutId:Int= R.layout.system_type_item) :BaseQuickAdapter<Article,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: Article) {

    }
}