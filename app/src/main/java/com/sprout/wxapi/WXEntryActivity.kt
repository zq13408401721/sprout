package com.sprout.wxapi

import android.app.Activity
import android.app.AppComponentFactory
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shop.utils.MyMmkv
import com.sprout.app.Global
import com.sprout.vm.pay.WXViewModel
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.lang.Exception
import java.lang.ref.WeakReference

class WXEntryActivity:AppCompatActivity(),IWXAPIEventHandler {


    companion object{
        final val CODE_WX_LOGIN = 30001
    }

    private lateinit var api:IWXAPI
    private lateinit var vmModel:WXViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmModel = ViewModelProvider(this).get(WXViewModel::class.java)
        api = WXAPIFactory.createWXAPI(this,Global.wx_app_id,false)
        try {
            if(intent == null) intent = Intent()
            api.handleIntent(intent,this)
        }catch (e:Exception){
            e.printStackTrace()
        }

        vmModel.accessToken.observe(this,{
            MyMmkv.setValue("access_token",it.access_token)
            MyMmkv.setValue("openid",it.openid)
            MyMmkv.setValue("refresh_token",it.refresh_token)
            setResult(CODE_WX_LOGIN)
            finish()
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api.handleIntent(intent,this)
    }


    override fun onReq(req: BaseReq?) {
        print("onReq")
    }

    override fun onResp(resp: BaseResp?) {
        print("onResp")
        when(resp!!.errCode){
            0 -> {
                //授权返回
                if(resp is SendAuth.Resp){
                    var result = resp as SendAuth.Resp
                    var code = result.code
                    vmModel.wxAccessToken(Global.wx_app_id,Global.wx_serect,code)
                }
            }
        }
    }
}