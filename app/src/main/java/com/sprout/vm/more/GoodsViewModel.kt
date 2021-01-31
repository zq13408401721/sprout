package com.sprout.vm.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.GoodData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class GoodsViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var gList: MutableLiveData<GoodData> = MutableLiveData()

    var bpage = 0
    var gpage = 0
    var size = 10


    fun getGood(){
        viewModelScope.launch {
            var result = repository.getGood(gpage,size)
            if(result.errno == 0){
                gList.postValue(result.data)
            }
        }
    }

}