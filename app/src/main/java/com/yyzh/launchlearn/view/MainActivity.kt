package com.yyzh.launchlearn.view

import android.util.Log
import androidx.lifecycle.Observer
import com.yyzh.commenlibrary.base.BaseVMActivity
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.login.viewModel.LoginViewModel

class MainActivity : BaseVMActivity<LoginViewModel>() {
    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java
    override fun getLayoutResId(): Int=R.layout.activity_main

    override fun initView() {
    }

    override fun initData() {
        mViewModel.apply {
            login("1234","1234")
            mLoginUser.observe(this@MainActivity, Observer {
                Log.e("chenggong","成功$it")
            })
            errMsg.observe(this@MainActivity, Observer {
                Log.e("失败","$it")
            })

        }
    }

}
