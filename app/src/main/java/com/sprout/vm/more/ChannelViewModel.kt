package com.sprout.vm.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.ChannelData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class ChannelViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var channels:MutableLiveData<List<ChannelData>> = MutableLiveData()

    /**
     * 获取频道数据
     */
    fun getChannel(){
        viewModelScope.launch {
            channels.postValue(repository.getChannel().data)
        }
    }

}