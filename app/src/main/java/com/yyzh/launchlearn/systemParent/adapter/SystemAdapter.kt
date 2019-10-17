package com.yyzh.launchlearn.systemParent.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.systemParent.model.bean.SystemParent

class SystemAdapter(layoutId:Int= R.layout.system_list_item):BaseQuickAdapter<SystemParent,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: SystemParent) {
        helper.run {
            setText(R.id.systemParent, item.name)
            setText(R.id.systemChild, item.children.joinToString("     ", transform = { child -> child.name }))
        }
    }
}