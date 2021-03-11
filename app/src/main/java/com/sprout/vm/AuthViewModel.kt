package com.sprout.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.WxAccessToken
import com.sprout.model.WxUserInfo
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

open class AuthViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var accessToken:MutableLiveData<WxAccessToken> = MutableLiveData()

    var wxUserInfo:MutableLiveData<WxUserInfo> = MutableLiveData()

    /**
     * 微信授权
     */
    fun wxAccessToken(appid:String,secret:String,code:String){
        viewModelScope.launch {
            var map = HashMap<String,String>()
            map.put("appid",appid)
            map.put("secret",secret)
            map.put("code",code)
            map.put("grant_type","authorization_code")
            var result = repository.wxToken(map)
            accessToken.postValue(result)
        }
    }

    /**
     * 微信用户信息
     */
    fun wxUserInfo(accesstoken:String,openid:String){
        viewModelScope.launch {
            var result = repository.wxUserInfo(accesstoken,openid)
            wxUserInfo.postValue(result)
            /*wxUserInfo.postValue(
                WxUserInfo(result.city,result.country,result.headimgurl,result.nickname,result.openid,result.privilege,result.province,result.sex,result.unionid)
            )*/
        }
    }

}