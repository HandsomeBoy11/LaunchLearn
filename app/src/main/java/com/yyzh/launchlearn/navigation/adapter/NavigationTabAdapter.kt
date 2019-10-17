package com.yyzh.launchlearn.navigation.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.navigation.model.bean.Navigation

class NavigationTabAdapter(layoutId:Int= R.layout.navigation_tab_tiem):BaseQuickAdapter<Navigation,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.run {
            setText(R.id.tvName,item.name)
            setTextColor(R.id.tvName,if(item.nameIsSelect) Color.parseColor("#008577") else Color.parseColor("#2f2f2f"))
        }
    }
}