package com.sprout.vm.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.ChannelData
import com.sprout.model.TrendsData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class RecommendViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var list:MutableLiveData<List<TrendsData>> = MutableLiveData()
    var channels:MutableLiveData<List<ChannelData>> = MutableLiveData()
    var commend:Int = 3
    var page:Int = 1
    var size:Int = 10

    /**
     * 获取频道数据
     */
    fun getChannel(){
        viewModelScope.launch {
            var result = repository.getChannel()
            if(result.errno == 0){
                channels.postValue(result.data)
            }
        }
    }

    /**
     * 获取推荐列表数据
     */
    fun recommendList(){
        viewModelScope.launch {


        }
    }

}