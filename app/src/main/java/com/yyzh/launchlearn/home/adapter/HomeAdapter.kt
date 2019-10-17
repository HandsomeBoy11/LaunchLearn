package com.yyzh.launchlearn.home.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.main.model.bean.Article
import com.yyzh.myapplication.base.fromN

class HomeAdapter(layoutResId:Int= R.layout.item_home):BaseQuickAdapter<Article, BaseViewHolder>(layoutResId){
    private var showStar = true

    fun showStar(showStar: Boolean) {
        this.showStar = showStar
    }

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.articleTitle, if (fromN()) Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(item.title))
                .setText(R.id.articleAuthor, item.author)
                .setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")
                .setText(R.id.articleTime, item.niceDate)
                .addOnClickListener(R.id.articleStar)

        if (showStar) helper.setImageResource(R.id.articleStar, if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal)
        else helper.setVisible(R.id.articleStar, false)
    }

}