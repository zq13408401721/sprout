package com.sprout.ui.login

import android.content.Intent
import com.shop.base.BaseActivity
import com.sprout.BR
import com.sprout.R
import com.sprout.app.Global
import com.sprout.databinding.ActivityLoginBinding
import com.sprout.vm.login.LoginViewModel
import com.sprout.vm.pay.WXViewModel
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class LoginActivity:BaseActivity<LoginViewModel,ActivityLoginBinding>(
        R.layout.activity_login,
        LoginViewModel::class.java
) {

    private lateinit var wxapi:IWXAPI
    private lateinit var viewModel:LoginViewModel

    override fun initData() {

    }

    override fun initVM() {
        viewModel = mViewModel as LoginViewModel
    }

    override fun initVariable() {
        mDataBinding.setVariable(BR.clickEvt,ClickEvt())
    }

    override fun initView() {
        initWX()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    fun initWX(){
        wxapi = WXAPIFactory.createWXAPI(this,Global.wx_app_id,true)
        wxapi.registerApp(Global.wx_app_id)
    }

    inner class ClickEvt{
        //微信登录
        fun wxLogin(){
            //调起微信授权页面
            var req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = Global.WX_STATE_AUTH
            var flag = wxapi.sendReq(req)
            print("flag:$flag")
            finish()
        }
    }


}

