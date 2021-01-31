package com.sprout.vm.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.TrendsData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class TrendsViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var list:MutableLiveData<List<TrendsData>> = MutableLiveData()
    var page:Int = 1
    var size:Int = 10

    /**
     *获取动态数据列表
     */
    fun getTrendsList(command:Int,channelid:Int){
        viewModelScope.launch {
            var result = repository.trendsList(command,channelid,page,size)
            if(result.errno == 0){
                list.postValue(result.data)
            }
        }
    }

}