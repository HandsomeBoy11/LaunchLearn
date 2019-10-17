package com.yyzh.launchlearn.login.view

import android.app.ProgressDialog
import android.view.View
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.yyzh.commenlibrary.base.BaseVMActivity
import com.yyzh.launchlearn.R
import com.yyzh.launchlearn.login.viewModel.LoginViewModel
import com.yyzh.launchlearn.main.view.NewMainActivity
import com.yyzh.launchlearn.utils.Preference
import com.yyzh.launchlearn.utils.toast
import com.yyzh.myapplication.base.App
import com.yyzh.mylibrary.utils.startKtxActivity
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.title_layout.*

class LoginActivity : BaseVMActivity<LoginViewModel>() {
    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    private lateinit var userName: String
    private lateinit var passWord: String
    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")


    override fun getLayoutResId(): Int = R.layout.login_activity

    override fun initView() {
        mToolbar.setTitle(R.string.login)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        login.setOnClickListener(onClickListener)
    }

    override fun startObserve() {
        mViewModel.apply {
            mLoginUser.observe(this@LoginActivity, Observer {
                isLogin = true
                App.CURRENT_USER = it
                userJson = Gson().toJson(it)
                dismissProgressDialog()
                startKtxActivity<NewMainActivity>()
                finish()
            })

            errMsg.observe(this@LoginActivity, Observer {
                dismissProgressDialog()
                it?.run { toast(it) }
            })
        }
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.login -> login()
        }
    }

    private fun login() {
        if (checkInput()) {
            showProgressDialog()
            mViewModel.login(userName, passWord)
        }
    }
    private fun checkInput(): Boolean {
        userName = userNameLayout.editText?.text.toString()
        passWord = pswLayout.editText?.text.toString()
        if (userName.isEmpty()) {
            userNameLayout.error = getString(R.string.please_input_username)
            return false
        }
        if (passWord.isEmpty()) {
            pswLayout.error = getString(R.string.please_input_psw)
            return false
        }
        return true
    }

    var progressDialog : ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog(){
        progressDialog?.dismiss()
    }
}