package com.sprout.vm.login

import com.shop.base.BaseViewModel
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import com.sprout.vm.AuthViewModel
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI

class LoginViewModel:AuthViewModel() {



    /**
     * 获取微信的code
     */
    fun getWxLoginCode(api:IWXAPI){
        var req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "萌芽登录"
        api.sendReq(req)
    }

}