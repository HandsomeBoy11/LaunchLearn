package com.yyzh.commenlibrary.base

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

open class BaseApplication:Application() {

    companion object{
        var CONTEXT: Context by Delegates.notNull()
    }
    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }
}