package com.sprout.ui.pay

import android.content.Intent
import com.shop.base.BaseActivity
import com.sprout.R
import com.sprout.app.Global
import com.sprout.databinding.ActivityPayBinding
import com.sprout.utils.MyMD5
import com.sprout.utils.MyUtils
import com.sprout.vm.pay.PayViewModel
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PayActivity:BaseActivity<PayViewModel, ActivityPayBinding>(R.layout.activity_pay, PayViewModel::class.java),IWXAPIEventHandler {

    lateinit var wxapi:IWXAPI

    override fun initData() {

    }

    override fun initVM() {
        mViewModel.prepay_id.observe(this, {
            openWxPay(it)
        })
    }

    /**
     * 打开微信支付页面
     */
    fun openWxPay(prepayid: String){
        val req = PayReq()
        req.appId = Global.wx_app_id
        req.partnerId = Global.wx_partnerId
        req.prepayId = prepayid
        req.packageValue = "Sign=WXPay"
        val map: MutableMap<String, String> = HashMap()
        map["appid"] = Global.wx_app_id
        map["partnerid"] = Global.wx_partnerId
        map["prepayid"] = prepayid
        map["package"] = "Sign=WXPay"
        map.put("timestamp", ((System.currentTimeMillis() / 1000).toBigInteger()).toString())
        map["noncestr"] = MyUtils.getRandomString(16)
        val sign: String = getSign(map)
        req.nonceStr = map["noncestr"]
        req.timeStamp = map["timestamp"]
        req.sign = sign
        wxapi.sendReq(req)
    }

    fun getSign(map: Map<String, String>): String {
        val infoIds: List<Map.Entry<String, String>> = ArrayList(map.entries)
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, object : Comparator<Map.Entry<String, String>> {

            override fun compare(o1: Map.Entry<String, String>, o2: Map.Entry<String, String>): Int {
                return (o1.key).compareTo(o2.key!!)
            }
        })

        // 构造签名键值对的格式
        val sb = StringBuilder()
        for (item: Map.Entry<String, String> in infoIds) {
            if (item.key != null || item.key !== "") {
                if (!(item.value === "" || item.value == null)) {
                    sb.append("${item.key}=${item.value}&")
                }
            }
        }
        //商户号的密码
        sb.append("key=")
        sb.append("79CFE67D22319191E1E2FAE6C26D814E")
        return MyMD5.MD5Encode(sb.toString()).toUpperCase()
    }


    override fun initVariable() {
        mDataBinding.clickEvt = ClickEvt()
    }

    override fun initView() {
        mDataBinding.checkboxWeixin.onCheckedChange { buttonView, isChecked ->
            if(isChecked){
                mViewModel.payType = 1
                mDataBinding.checkboxAlipay.isChecked = false
            }
        }

        mDataBinding.checkboxAlipay.onCheckedChange { buttonView, isChecked ->
            if(isChecked){
                mViewModel.payType = 2
                mDataBinding.checkboxWeixin.isChecked = false
            }
        }
        wxapi = WXAPIFactory.createWXAPI(this, Global.wx_app_id)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    /**
     * 点击事件
     */
    inner class ClickEvt{

        fun checkChange(type: Int){

        }

        /**
         * 支付
         */
        fun pay(){
            mViewModel.getWxOrder()
        }

    }

    override fun onReq(p0: BaseReq?) {

    }

    override fun onResp(p0: BaseResp?) {

    }
}