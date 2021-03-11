package com.sprout.vm.pay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class PayViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var payType:Int = 0  //0 支付方式没选 1 微信支付  2支付宝支付
    var prepay_id:MutableLiveData<String> = MutableLiveData() //微信支付订单号

    /**
     * 获取微信订单信息
     */
    fun getWxOrder(){
        viewModelScope.launch {
            var result = repository.wxOrder()
            prepay_id.postValue(result.prepay_id)
        }
    }

}