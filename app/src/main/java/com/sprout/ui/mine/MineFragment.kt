package com.sprout.ui.mine

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shop.base.BaseFragment
import com.shop.utils.MyMmkv
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.FragmentMineBinding
import com.sprout.model.WxUserInfo
import com.sprout.ui.login.LoginActivity
import com.sprout.ui.message.MessageFragment
import com.sprout.ui.pay.PayActivity
import com.sprout.vm.mine.MineViewModel
import com.sprout.wxapi.WXEntryActivity
import io.reactivex.internal.operators.completable.CompletableDoOnEvent

class MineFragment:BaseFragment<MineViewModel,FragmentMineBinding>(R.layout.fragment_mine,MineViewModel::class.java) {

    final var CODE_REQ_PAY = 20000 //打开充值页面的key值
    final var CODE_REQ_LOGIN = 20001   //登录页面

    lateinit var wxUser:WxUserInfo

    companion object{
        val instance: MineFragment by lazy { MineFragment() }
    }

    override fun initData() {
        var access_token = MyMmkv.getString("access_token")
        if(access_token != null && access_token.isNotEmpty()){
            getWxUserInfo()
        }
    }

    override fun initVM() {
        mViewModel.wxUserInfo.observe(this,{
            wxUser = it
            mDataBinding.setVariable(BR.wxUser,wxUser)
            Glide.with(mDataBinding.imgHeader).load(it.headimgurl).apply(RequestOptions.circleCropTransform()).into(mDataBinding.imgHeader)
        })
    }

    override fun initVariable() {

    }

    override fun initView() {
        mDataBinding.clickEvt = ClickEvt()
    }

    /**
     * 回传值
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE_REQ_LOGIN -> {
                //获取微信的用户的数据
                getWxUserInfo()
            }
        }
    }

    /**
     * 获取微信用户数据
     */
    fun getWxUserInfo(){
        var accesstoken = MyMmkv.getString("access_token")
        var openid = MyMmkv.getString("openid")
        if(accesstoken != null && accesstoken.isNotEmpty() && openid != null && openid.isNotEmpty()){
            mViewModel.wxUserInfo(accesstoken!!,openid!!)
        }
    }

    /**
     * 我的页面点击事件
     */
    inner class ClickEvt{

        //打开充值页面
        fun openPay(){
            var intent = Intent(activity,PayActivity::class.java)
            startActivityForResult(intent,CODE_REQ_PAY)
        }

        //打开登录页面
        fun openLogin(){
            var intent = Intent(activity,LoginActivity::class.java)
            startActivityForResult(intent,CODE_REQ_LOGIN)
        }

    }


}
