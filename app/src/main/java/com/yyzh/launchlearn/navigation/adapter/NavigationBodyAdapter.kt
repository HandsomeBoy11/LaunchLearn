package com.yyzh.launchlearn.navigation.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.main.model.bean.Article
import com.yyzh.launchlearn.navigation.model.bean.Navigation
import com.yyzh.launchlearn.main.view.BrowserNormalActivity
import com.yyzh.mylibrary.utils.startKtxActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class NavigationBodyAdapter(layoutId:Int= R.layout.navigation_body_tiem):BaseQuickAdapter<Navigation,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.run {
            setText(R.id.navigationName,item.name)
            val tagFlowLayout = getView<TagFlowLayout>(R.id.navigationTagLayout)
            tagFlowLayout.run {
                adapter = object : TagAdapter<Article>(item.articles) {
                    override fun getCount(): Int {
                        return item.articles.size
                    }

                    override fun getView(parent: FlowLayout, position: Int, t: Article): View {
                        val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                                parent, false) as TextView
                        tv.text = t.title
                        return tv
                    }
                }

                setOnTagClickListener { _, position, parent ->
                    parent.context.startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to item.articles[position].link)
                    true
                }
            }
        }
    }
}