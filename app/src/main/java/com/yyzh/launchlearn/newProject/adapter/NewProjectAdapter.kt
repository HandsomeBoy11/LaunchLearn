package com.yyzh.launchlearn.newProject.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.main.model.bean.Article
import com.yyzh.myapplication.base.App
import com.yyzh.myapplication.base.fromN

class NewProjectAdapter(layoutInt:Int= R.layout.new_project_item):BaseQuickAdapter<Article,BaseViewHolder>(layoutInt) {
    override fun convert(helper: BaseViewHolder, item: Article?) {
        item?.let {
            helper.setText(R.id.projectName,if (fromN()) Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(item.title))
                    .setText(R.id.projectDesc, item.desc)
                    .setText(R.id.projectAuthor, item.author)
                    .setText(R.id.projectTime, item.niceDate)
                    .setImageResource(R.id.articleStar, if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal)
                    .addOnClickListener(R.id.articleStar)
            Glide.with(App.CONTEXT).load(item.envelopePic).into(helper.getView(R.id.projectImg))
        }


    }
}