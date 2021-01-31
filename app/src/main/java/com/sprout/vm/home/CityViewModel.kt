package com.sprout.vm.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.TrendsData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class CityViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var list:MutableLiveData<List<TrendsData>> = MutableLiveData()
    var page:Int = 1
    var size:Int = 2

    fun getTrends(command:Int){
        viewModelScope.launch {
            var result = repository.trendsList(command,0,page,size)
            if(result.errno == 0){
                list.postValue(result.data)
            }
        }
    }

}